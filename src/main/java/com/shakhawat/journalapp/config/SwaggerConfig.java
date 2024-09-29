package com.shakhawat.journalapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Journal Application API").description(
                        "This is a sample Spring Boot-3 RESTful service using SpringDoc-openapi and OpenAPI 3."));
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String[] packagesToScan = {"com.shakhawat.journalapp.controller"};
        return GroupedOpenApi
                .builder()
                .group("REST API")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/admin/**", "/user/**", "/public/**", "/journal/**")
                .addOpenApiCustomizer(bearerAuthCustomizer())
                .addOpenApiCustomizer(tagsSorterCustomizer())
                .addOpenApiCustomizer(userApiCustomizer())
                .build();
    }

    private OpenApiCustomizer userApiCustomizer() {
        return openAPI -> openAPI
                .info(new Info()
                        .title("SpringBoot & OpenAPI")
                        .description("This is a sample Spring Boot RESTful service using OpenAPI")
                        .version("3.0.0")
                        .contact(new Contact()
                                .name("Shakhawat Mollah")
                                .url("https://github.com/shakhawatmollah")
                                .email("shskylark@gmail.com")))
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("Local"),
                        new Server().url("http://localhost:8090").description("Live")));
    }

    private OpenApiCustomizer bearerAuthCustomizer() {
        return openAPI -> openAPI
                .schemaRequirement("Bearer", new SecurityScheme()
                        .description("JWT Authorization header using the Bearer scheme. Example: \\\\\\\"Authorization: Bearer {token}\\\\\\\"")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("GWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                )
                .security(Collections.singletonList(new SecurityRequirement().addList("Bearer")));
    }

    private OpenApiCustomizer tagsSorterCustomizer() {
        return openAPI -> {
            List<Tag> tags = openAPI.getTags();
            tags.sort(Comparator.comparing(Tag::getName));
            openAPI.tags(tags);
        };
    }

}
