package com.khoubyari.example.test;

import com.khoubyari.example.Application;
import com.khoubyari.example.test.listener.MyTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;


@RunWith(MyTestRunner.class)
@ActiveProfiles("test")
public class FiboTest {

    @Test
    public void should_test_suite_fibonacci_courte() {
        assertEquals(Long.parseLong("7810785687120836007"), Application.fibonacci(130));
    }

    @Test
    public void should_test_suite_fibonacci_use_puissance() {
        assertEquals(9227465 , Application.fibonaciRecursif(35));
    }
}
