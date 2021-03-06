package com.maxel.cursomc.config;

import com.maxel.cursomc.service.DBService;
import com.maxel.cursomc.service.EmailService;
import com.maxel.cursomc.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Autowired
    DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public  boolean instantiateDatabase() throws Exception {
        if(!"create".equals(strategy)) {
            return  false;
        }

        dbService.instantiateTestDatabase();
        return  true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
