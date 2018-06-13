package com.khoubyari.example.test;

import com.khoubyari.example.Application;
import com.khoubyari.example.test.listener.MyTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;

import static org.junit.Assert.assertEquals;


@RunWith(MyTestRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class FiboTest {

    @Before
    public void setUp() throws Exception {
        TestContextManager testContextManager;
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }


    @Test
    public void should_test_suite_fibonacci_courte() {
        assertEquals(Long.parseLong("7810785687120836007"), Application.fibonacci(130));
    }
/*
    @Test
    public void should_test_suite_fibonacci_use_puissance() {
        assertEquals(9227465 , Application.fibonaciRecursif(35));
    }
*/
}
