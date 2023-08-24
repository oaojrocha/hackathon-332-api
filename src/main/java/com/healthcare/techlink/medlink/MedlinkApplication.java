package com.healthcare.techlink.medlink;

import com.healthcare.techlink.medlink.core.init.Init;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedlinkApplication {

    public static void main(String[] args) {
        new Init().initEntities();

        SpringApplication.run(MedlinkApplication.class, args);
    }

}
