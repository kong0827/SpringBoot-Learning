## Spring事件发布及监听
### 1、简介

    目前spring boot中支持的事件类型如下
    
    ApplicationFailedEvent：该事件为spring boot启动失败时的操作
    
    ApplicationPreparedEvent：上下文context准备时触发
    
    ApplicationReadyEvent：上下文已经准备完毕的时候触发
    
    ApplicationStartedEvent：spring boot 启动监听类
    
    SpringApplicationEvent：获取SpringApplication
    
    ApplicationEnvironmentPreparedEvent：环境事先准备

#### ApplicationListener
#### ApplicationRunner
#### CommandLineRunner
#### ApplicationEvent 