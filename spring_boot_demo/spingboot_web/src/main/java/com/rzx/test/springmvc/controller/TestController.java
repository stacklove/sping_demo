package com.rzx.test.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AsyncTaskService asyncTaskService;
    @Autowired
    private TestEhcacheServer testEhcacheServer;
    @GetMapping("/get/{id}")
    public Map<String, Object> getMethod(@PathVariable(required = true) String id) throws InterruptedException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Future<Boolean> task1 = asyncTaskService.syncTask1();
        Future<Boolean> task2 = asyncTaskService.syncTask2();
        while (!task1.isDone()||!task2.isDone()){
            if(task1.isDone()&&task2.isDone())break;
        }

        testEhcacheServer.testEhcache();

        return map;
    }





}

