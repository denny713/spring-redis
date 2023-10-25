package com.redis.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DocConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:7070");

        Server testServer = new Server();
        testServer.setDescription("test");
        testServer.setUrl("https://test.org");

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Spring Boot with Redis CRUD Test")
                .description("Documenting Spring Boot REST API with SpringDoc and OpenAPI 3 spec")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Denny Afrizal")
                        .email("denny.afrizal713@gmail.com"))
                .termsOfService("TOC"));
        openAPI.setServers(Arrays.asList(localServer, testServer));

        return openAPI;
    }
}
