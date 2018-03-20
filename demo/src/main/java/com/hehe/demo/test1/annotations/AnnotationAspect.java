package com.hehe.demo.test1.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注解切面，用来注解功能的实现
 * @author xieqinghe .
 * @date 2017/11/23 下午1:42
 * @email qinghe101@qq.com
 */
//声明一个注解
@Aspect
@Slf4j
public class AnnotationAspect {

    @Pointcut(value = "@annotation(com.hehe.demo.test1.annotations.Annotation)")
    public void logRequiredPointCut() {
    }

    @Around(value = "logRequiredPointCut()")
    public Object  aspect(ProceedingJoinPoint point) throws Throwable {
       try {
           log.info("begin");

           Method sourceMethod = ((MethodSignature) point.getSignature()).getMethod();
           Annotation annotation=sourceMethod.getDeclaredAnnotation(Annotation.class);
           Class processorClass=annotation.processor();
           String processorMethod=annotation.method();

           Class outputType = sourceMethod.getReturnType(); //需要捕获日志方法的返回类型
           Class[] inputTypes = sourceMethod.getParameterTypes(); //需要捕获日志方法的参数类型
           Object[] inputValues = point.getArgs(); //需要捕获日志方法的参数值

           RequestAttributes attributes = RequestContextHolder.getRequestAttributes(); //HttpServlet 请求参数相关

           Object outputValue= point.proceed();

           Method[] methods=processorClass.getMethods();

           Method selectedMethod = null;
           for (Method method : methods) {
              if (method.getName().equals(processorMethod)){
                  selectedMethod=method;
                  break;
              }
           }

           if (selectedMethod==null){
               throw new Exception("未找到方法");
           }

           Object[] invokeParameters=ioeToInvokeParameters(inputValues,outputValue,outputType);

           ExecutorService executor = Executors.newFixedThreadPool( 1);
           Method finalSelectedMethod = selectedMethod;
           executor.submit(new Runnable() {
               @Override
               public void run() {
                   try {
                       finalSelectedMethod.invoke(processorClass.newInstance(),invokeParameters);
                   }catch (Exception e){

                   }finally {
                       executor.shutdown();
                   }
               }
           });

//           selectedMethod.invoke(processorClass.newInstance(),invokeParameters);

           log.info("end");

           return outputValue;
       }catch (Exception e){
           log.info("fail");
           throw e;
       }



    }

    Object[] ioeToInvokeParameters(Object[] inputValues,Object outputValue,Class outputType) {
        int inputValuesLen = null == inputValues ? 0 : inputValues.length;
        Object[] invokeParameters = new Object[inputValuesLen + 1];
        int index = 0;
        if (inputValuesLen > 0) {
            for (int i = 0; i < inputValuesLen; i++) {
                invokeParameters[index++] = inputValues[i];
            }
        }
        if (outputType.equals(void.class) || outputType.equals(Void.class)) {
            invokeParameters[index++] = null;
        } else {
            invokeParameters[index++] = outputValue;
        }
        //invokeParameters[index] = ioe.getException();
        return invokeParameters;
    }
}
