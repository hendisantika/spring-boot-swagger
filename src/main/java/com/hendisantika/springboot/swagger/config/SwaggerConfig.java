package com.hendisantika.springboot.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hendisantika on 4/24/17.
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI notifOpenAPI(@Value("${application-description}") String appDescription, @Value("${application" +
            "-version}") String appVersion) {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new io.swagger.v3.oas.models.info.Info()
                .title("Sample Service API")
                .description(appDescription)
                .version(appVersion)
                .contact(new io.swagger.v3.oas.models.info.Contact()
                        .name("Hendi Santika")
                        .url("https://s.id/hendisantika")
                        .email("hendisantika@yahoo.co.id"))
                .termsOfService("TOC")
                .license(new io.swagger.v3.oas.models.info.License().name("License").url("https://s.id/hendisantika")));
        return openAPI;
    }
}
