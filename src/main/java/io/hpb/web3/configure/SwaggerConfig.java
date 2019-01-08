package io.hpb.web3.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;

@Configuration //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
@ComponentScan(basePackages = {"io.hpb.web3.controller"}) //必须存在 扫描的API Controller package name 也可以直接扫描class (basePackageClasses)
public class SwaggerConfig{
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("gene", "http://www.hpb.io/", "gene@hpb.io");
        return new ApiInfoBuilder()
                .title("HPB区块链中间件API接口")
                .description("HPB区块链中间件API的统一接口服务")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}