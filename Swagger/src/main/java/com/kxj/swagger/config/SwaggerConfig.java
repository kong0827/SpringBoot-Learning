package com.kxj.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @ClassName SwaggerConfig
 * @Description Swagger配置类
 * @Author kongxiangjin
 * @Date 2019/12/18 17:25
 * @Version 1.0
 **/


/**
 * 启动swagger自动配置
 * @author kongxiangjin
 */
@EnableSwagger2
@Component
public class SwaggerConfig {
    /**
     * 配置docket以配置Swagger具体参数
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发小组名称")
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                ;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("孔祥劲", "https://github.com/kong0827", "1351882069@qq.com");
        return new ApiInfo("孔祥劲Swagger接口文档",
                "每天学习一丢丢，每天进步一点点",
                "1.0",
                "kongxiangjin",
                contact, "Apache 2.0",
                "ttps://github.com/kong0827",
                new ArrayList()
        );
    }

}
