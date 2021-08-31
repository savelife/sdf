# sdf(Simplified development Framework)

> 动态注入controller API 接口 实现无Controller类编写
>
> 使用说明:
>
> 定义API 接口 ,  接口层添加API注册 ,与传统一致,可以支持swagger文档生成
>
> 接口返回无限制
>
> 接口参数目前可以支持 
>
> ​	1、实现IReq接口的类作为单一参数(Json 格式接受前端参数)
>
> ​	2、无参数
>
>  有其他想法的小伙伴可以一起完善这个框架,有时间会考虑完善整个框架,以期望支持DDD模式开发. 
>
> 



### 目录结构

```yaml
|-- sdf
    |-- pom.xml
    |-- src
        |-- main
        |   |-- java
        |   |   |-- com
        |   |       |-- sdf
        |	|		    |-- normal #一般 常见 接口 写法 + 测试手动注入controller类方式
        |   |           |   |-- AddUserReq.java 
        |   |           |	|-- BaseControllerConfigurationDemo.java
        |   |           |   |-- AbstractBaseController.java
        |   |           |	|-- IUserService.java
        |   |           |   |-- QueryUserReq.java
        |   |      	    |   |-- SDFApplication.java
        |   |           |   |-- UpdateUserReq.java
        |   |           |   |-- UserController.java
        |   |           |   |-- UserServiceImpl.java
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

