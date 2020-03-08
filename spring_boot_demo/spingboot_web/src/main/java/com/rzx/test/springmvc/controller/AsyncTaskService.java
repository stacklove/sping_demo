package com.rzx.test.springmvc.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTaskService {

    /**
     * @Description 异步任务处理2
     * @Author xieshiyao@1218.com.cn
     * @Date 2020/3/7 15:31
     * @Param
     * @Return
     * @Exception
     */
    @Async
    public Future<Boolean> syncTask2() throws InterruptedException {
        /**发10封邮件*****/
        Thread.sleep(1000);
        System.out.println("10封邮件发送成功");
        return new AsyncResult<>(true);
    }

    /**
     * @Description 异步任务处理1
     * @Author xieshiyao@1218.com.cn
     * @Date 2020/3/7 15:28
     * @Param
     * @Return
     * @Exception
     */
    @Async
    public Future<Boolean> syncTask1() throws InterruptedException {
        /**发1000个通知消息****/
        Thread.sleep(3000);
        System.out.println("1000个消息通知发送成功");

        return new AsyncResult<>(true);
    }
}
