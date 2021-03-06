package cn.com.xuxiaowei.security.servlet.login;

import cn.com.xuxiaowei.security.setting.SecuritySettings;
import cn.com.xuxiaowei.security.util.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.weixin4j.Weixin;
import org.weixin4j.component.SnsComponent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 第三方登录（微信网页（微信内部））URL
 *
 * @author xuxiaowei
 */
public class WeChatWebPageHttpServlet extends HttpServlet {

    @Autowired
    private SecuritySettings securitySettings;

    /**
     * 域名
     */
    private String domain = "http://127.0.0.1";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        // 微信平台基础支持对象
        Weixin weixin = new Weixin();

        // 网页授权基础支持
        SnsComponent snsComponent = new SnsComponent(weixin);

        // 状态码
        String state = UUID.randomUUID().toString().replace("-", "");

        session.setAttribute("wechat_webpage_connect_state", state);

        // 网页安全授权获取用户信息（获取 Code 页面）

        // 获取 Authorization Code URL
        // 使用的 接口URL：https://open.weixin.qq.com/connect/oauth2/authorize
        // 官方文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
        // 第一步：用户同意授权，获取code
        String oAuth2CodeUserInfoUrl = snsComponent.getOAuth2CodeUrl(domain + securitySettings.weChatWebPageUrl, "snsapi_userinfo", state);

        // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
        // 由于 第三方登录（微信网页（微信内部））授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
        // 第三方登录（微信网页（微信内部））授权时，必须进入此方法
        WebAuthenticationDetails details = SecurityUtils.getDetails();

        // 在此方法中，将 getDetails() 放入 Session
        // 授权时，从 Session 中获取
        session.setAttribute("detailsWeChatWebPage", details);

        // 用户同意授权，获取 code ，重定向到微信 URL
        resp.sendRedirect(oAuth2CodeUserInfoUrl);

    }

}
