package com.tbtk.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Configuration for Swagger UI and API documentation.
 * <p>
 * Documentation is available on http://localhost:8080/swagger-ui/#
 * <p>
 * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tbtk.map.controller"))
                .paths(regex(".*?"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Documentation")
                .version("1.0")
                .description("TbtkMap is a simple web application that " +
                        "allows users to save locations on the world map.")
                .build();
    }
}
