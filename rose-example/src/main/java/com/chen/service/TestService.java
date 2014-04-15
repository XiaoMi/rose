/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-6-27 下午08:39:04
 */
package com.chen.service;

import com.chen.dao.TestDAO;
import com.chen.model.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestDAO testDAO;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public Test getTest() {
        return testDAO.getTest();
    }

    public void insertTest(Test test) {
        testDAO.insertTest(test);
    }

    public void test(){
        taskExecutor.createThread(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        });
    }
}
