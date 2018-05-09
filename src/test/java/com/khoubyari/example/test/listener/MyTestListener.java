package com.khoubyari.example.test.listener;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

public class MyTestListener extends RunListener {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private long getTimestamp() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }

    @Override
    public void testRunStarted(Description description) {
        log.info("Class" + description.getClassName() + " start test.\n");
    }

    @Override
    public void testRunFinished(Result result) {
        System.out.println("Test ends");
    }

    @Override
    public void testStarted(Description description) {
        log.info("timestamp="+getTimestamp()+";testname="+description.getMethodName());
    }

    @Override
    public void testFinished(Description description) {
        log.info("timestamp="+getTimestamp()+";testname="+description.getMethodName());
    }

    @Override
    public void testFailure(Failure failure) {
        log.error(failure.getDescription().getMethodName() + " test FAILED!!!");
    }
}
