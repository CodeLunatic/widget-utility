package com.cy.index;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OnApplicationStart implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().exec("cmd /c start http://localhost:9001");
    }
}
