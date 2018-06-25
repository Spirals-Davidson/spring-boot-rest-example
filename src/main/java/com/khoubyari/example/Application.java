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
/*
    public static long fibonacci(int k) {
            if(n == 0 || n == 1)
                return n;

            return fibonaciRecursif(n - 1) + fibonaciRecursif(n - 2);
    }
*/
    public static long fibonaciRecursif(int n){
        long c = 1,p = 0, n;
        for(int i=1; i<k; i++){
            n = p + c;
            p = c;
            c = n;
        }
        return c;

    }


}
