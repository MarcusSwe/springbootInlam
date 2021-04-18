package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.text.NumberFormat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    //@Qualifier("germanCurrencyFormat")
    private NumberFormat nf;

	@Test
	void contextLoads() {
	    int count = ctx.getBeanDefinitionCount();
        System.out.println("Det finns " + count + " bönor i IoC Containern");
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
	}

}
