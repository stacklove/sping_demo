package com.rzx.test.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
@Slf4j
public class SchedulerTaskService {


    @Scheduled(cron = "*/5 * * * * ?")
    public void task1(){
        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-DD hh:mm:ss");
        log.info("当前执行的时间为:"+format.format(new Date()));
    }
}
