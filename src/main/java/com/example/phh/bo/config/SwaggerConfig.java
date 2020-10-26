package com.example.phh.bo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @SuppressWarnings("desprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        .title("PHH TEST BO API")
        .description("Spring Boot - PHH TEST BO API")
        .version("V1")
        .build();
    }

    @Bean
    public Docket api() {
        String version = "V1";
        return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .groupName(version)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/bo/**"))
        .build();
    }
}