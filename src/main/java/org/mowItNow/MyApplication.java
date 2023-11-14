package org.mowItNow;

import org.mowItNow.mower.presentation.MowerScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication  implements CommandLineRunner {

    @Autowired
    private MowerScreen mowerScreen;

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mowerScreen.screen();
    }
}
