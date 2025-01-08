package com.mdd.ela.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author dungmd
 * @created 1/8/2025 2:48 下午
 * @project e-learning-app
 */
@Configuration
public class OpenApiConfig {
//    @Bean
//    public GroupedOpenApi publicApi(@Value("${openapi.service.api-docs}") String apiDocs) {
//        return GroupedOpenApi.builder()
//                .group(apiDocs)
//                .packagesToScan("com.mdd.ela.controller")
//                .build();
//    }
    @Bean
    public OpenAPI openAPI(@Value("${openapi.service.title}")String title,
                           @Value("${openapi.service.version}")String version,
                           @Value("${openapi.service.server}")String server
    ){
        return new OpenAPI().info(new Info().title(title)
                .version(version))
                .servers(List.of(new Server().url(server)))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
