package com.maxim.documentfiller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application that provides API to create document from template with filled by user data
 */
@SpringBootApplication
public class DocumentFillerApplication {

    @Value("${test}")
    private static String test;



    public static void main(String[] args) {

        SpringApplication.run(DocumentFillerApplication.class, args);
        System.out.println("!!!!!!!!!! "+test);

    }

}
