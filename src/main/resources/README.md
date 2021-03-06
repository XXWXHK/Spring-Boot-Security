# 第三方登录

## QQ 登录

- QQ组件配置文件

    - qqconnectconfig.properties
    
        - 获取配置文件 qqconnectconfig.properties 中需要修改信息的网址：
        
            - [QQ互联](https://connect.qq.com/manage.html)
    
        - 需要修改的信息
            
            - app_ID
            
            - app_KEY
            
            - redirect_URI
            
                - redirect_URI 去掉域名时，要与 application.yml 中的 security.qq-url 相同
        
## 微信登录

### 微信内部登录

- weixin4j.properties

    - 填写配置（开发者第三方用户唯一凭证）：weixin4j.oauth.appid
    
    - 填写配置（开发者第三方用户唯一凭证密钥）：weixin4j.oauth.secret
    
    - 以上配置获取网址：[微信公众平台接口测试帐号申请](http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)

- WeChatWebPageHttpServlet

    - 网页安全授权获取用户信息（获取 Code 页面）的域名设置位置：
    
        - WeChatWebPageHttpServlet 中的 域名（类变量 domain） 与 [微信公众平台接口测试帐号申请](http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) 
            中的 网页帐号（网页授权获取用户基本信息） 不同时，会出现错误：redirect_uri 参数错误
    
        - 在 [微信公众平台接口测试帐号申请](http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) 
        
            - 体验接口权限表
            
                - 网页服务
                
                    - 网页帐号（网页授权获取用户基本信息）
                    
                        - 开发测试时，可设置为 127.0.0.1

### 微信扫码登录