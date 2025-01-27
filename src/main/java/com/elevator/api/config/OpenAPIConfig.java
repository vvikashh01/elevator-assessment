package com.elevator.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Elevator Test API")
                        .version("1.0")
                        .description("This API exposes endpoints to manage insurance claims for Elevator.")
                        .termsOfService("Terms of service")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Elevator")
                                .url("https://elevator.com/")
                                .email("admin@elevator.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("License of API")
                                .url("API license URL")))
                .servers(getServers());
    }

    private List<Server> getServers() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:9090"));
        return servers;
    }

}