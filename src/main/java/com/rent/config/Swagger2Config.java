package com.rent.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Exrickx
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        log.info("开始加载Swagger2");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Documentation")
                .description("API接口文档")
                .termsOfServiceUrl("http://exrick.cn/")
                .contact(new Contact("Exrick", "http://blog.exrick.cn", "1012139570@qq.com"))
                .version("1.0.0")
                .build();
    }
}
