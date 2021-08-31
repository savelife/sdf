# sdf(Simplified development Framework)

> 1、支持无Controller类 实现简化API接口实现
>
> 目录结构

```yaml
|-- sdf
    |-- pom.xml
    |-- src
        |-- main
        |   |-- java
        |   |   |-- com
        |   |       |-- sdf
        								|-- normal #一般 常见 接口 写法 + 测试手动注入controller类方式
        |   |           		|-- AddUserReq.java 
        |   |           		|-- BaseControllerConfigurationDemo.java
        |   |               |-- AbstractBaseController.java
        |   |           		|-- IUserService.java
        |   |           		|-- QueryUserReq.java
        |   |           		|-- SDFApplication.java
        |   |           		|-- UpdateUserReq.java
        |   |           		|-- UserController.java
        |   |           		|-- UserServiceImpl.java
        |   |           |-- common #基础类 
        |   |           |   |-- BusinessEnumIfc.java
        |   |           |   |-- BusinessException.java
        |   |           |   |-- GlobalSystemMsg.java
        |   |           |   |-- IReq.java
        |   |           |   |-- Resp.java
        |   |           |   |-- SpringBeanFactory.java
        |   |           |-- framework
        |   |               |-- application #应用层
        |   |               |-- controller #接口层
        |   |               |   |-- BaseController.java #基础 controller
        |   |               |   |-- BaseControllerConfiguration.java #动态注入controller 
        |   |               |   |-- BaseService.java 
        |   |               |   |-- role # 演示role demo API
        |   |               |       |-- AddRoleReq.java
        |   |               |       |-- AddRoleResp.java
        |   |               |       |-- IRoleControllerService.java
        |   |               |       |-- RoleControllerServiceImpl.java
        |   |               |-- domain #领域层
        |   |               |   |-- Role.java #实体
        |   |               |-- infrastructure #基础设施层
        |   |-- resources
        |       |-- application.yml
        |-- test
            |-- java

