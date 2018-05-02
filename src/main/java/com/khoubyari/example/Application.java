package com.khoubyari.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static long fibonacci(int k) {
        long c = 1,p = 0, n;
        for(int i=1; i<k; i++){
            n = p + c;
            p = c;
            c = n;
        }
        return c;
    }

    public static long fibonnaciRecursif(int n){
            if(n == 0 || n == 1)
                return n;

            return fibonnaciRecursif(n - 1) + fibonnaciRecursif(n - 2);
    }


}
