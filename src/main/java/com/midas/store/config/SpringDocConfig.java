package com.midas.store.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI SpringDocConfig() {
        return new OpenAPI()
                .info(new Info().title("Store API Midas")
                        .description("Test Midas App")
                        .version("v0.0.1")
                        .license(new License().name("Test java 17").url("https://www.abelacevedo.com.ar")))
                .externalDocs(new ExternalDocumentation()
                        .description("Portfolio Abel Acevedo v1")
                        .url("https://www.abelacevedo.com.ar"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer").bearerFormat("JWT")));
    }

}
