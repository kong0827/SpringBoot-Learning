package com.kxj.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @ClassName SwaggerConfig
 * @description Swagger配置类
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
                .enable(true)
                .select()
                // 配置要扫描接口的方式
                .apis(RequestHandlerSelectors.any())
                // 过滤什么路径 过滤只含有kong下面的请求
                .paths(PathSelectors.ant("/kong/**"))
                .build();
    }

//    @Bean
//    public Docket docket(Environment environment) {
//
//        // 设置要显示的Swagger环境
//        Profiles profiles = Profiles.of("dev", "test");
//        // 通过Environment.acceptsProfiles 判断是否处在自己设定的环境中
//        boolean enable = environment.acceptsProfiles(profiles);
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .groupName("开发小组名称")
//                .enable(enable)
//                .select()
//                // 配置要扫描接口的方式
//                .apis(RequestHandlerSelectors.any())
//                // 过滤什么路径 过滤只含有kong下面的请求
//                .paths(PathSelectors.ant("/kong/**"))
//                .build();
//    }

    // 配置Swagger的默认信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("小K", "https://github.com/kong0827", "1351882069@qq.com");
        return new ApiInfo("小K的Swagger接口文档",
                "每天学习一丢丢，每天进步一点点",  // 描述
                "1.0",
                "https://github.com/kong0827",
                contact,                                   // 作者信息
                "Apache 2.0",
                "ttps://github.com/kong0827",
                new ArrayList()
        );
    }
}
