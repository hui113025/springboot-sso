# springboot-sso
spring-boot + spring-session + redis 集成SSO单点登录
- sso-core 过滤器 各系统都需引入 需要放入本地仓库（..\.m2\repository\com\zheng\sso-core\1.0-SNAPSHOT\sso-core-1.0-SNAPSHOT.jar）
- sso-client_user 客服端用户 http://127.0.0.1:82
- sso-client_other 客服端用户 http://127.0.0.1:81
- spring-session 支持ip和子域名 各系统都需配置