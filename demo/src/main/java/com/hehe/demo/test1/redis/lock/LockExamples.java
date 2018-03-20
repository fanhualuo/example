package com.hehe.demo.test1.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;


/**
 * 基于redisson的分布式锁
 *
 * @author xieqinghe .
 * @date 2018/1/22 下午1:48
 * @email qinghe101@qq.com
 */
public class LockExamples {

    public static void main(String[] args) {

        lock1();
        lock1();

    }

    public static void lock1() {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        //以名称返回锁实例。redis对应的key->lock
        RLock lock = redisson.getLock("lock");

        /**
         * 获得锁,如果锁不可用，则当前线程变为挂起,默认锁定看门狗超时是30秒,
         * 可以修改锁时间 lock.lock(2, TimeUnit.SECONDS)
         */
        lock.lock();
        System.out.println("---------------------lock" + Thread.currentThread());

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //释放锁。
        lock.unlock();

        //关闭Redisson实例
        redisson.shutdown();
    }


}
