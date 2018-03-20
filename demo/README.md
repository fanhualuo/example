## demo

### 1 com.hehe.demo.test1.annotations包
使用注解实现方法指定方法调用，spring aop实现<br>
用途:方法级别生成log日志<br>
spring aop使用注解创建切面<br>
- @After 通知方法会在目标方法返回或抛出异常后调用
- @AfterRetruening 通常方法会在目标方法返回后调用
- @AfterThrowing 通知方法会在目标方法抛出异常后调用
- @Around 在目标方法执行前后执行，通过ProceedingJoinPoint.proceed();方法隔开
- @Pointcut 声明一个通用的切点
- @EnableAspectJAutoProxy 启动自动代理功能


### 2 com.hehe.demo.test1.redis.lock包
基于redisson的分布式锁


### 3 com.hehe.demo.test1.excel.fastexcel包
excel快速导入导出, 

来自 https://github.com/dhatim/fastexcel