
package com.yy.ebp.gateway.sso.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yy.ebp.gateway.sso.dto.SessionConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class SecurityFilter extends ZuulFilter {
	
    private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private SessionConfig sessionConfig;

    Base64 base64 = new Base64();

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

//        String userAgent = request.getHeader("User-Agent");
//        if (userAgent.contains(APP_TYPE_DISCIPLINE)) {
//            return disciplineFilter();
//        } else {
//        }
        return sighCheck();
    }

    /**
     * 设置是否执行该过滤的条件
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //追加请求头
        ctx.addZuulRequestHeader("gateway", "ebp");

        /** 需要排除（不拦截）的URL */
        List<String> except = sessionConfig.getExcept();
        String servletPath = request.getServletPath();
        if (except != null) {
            for (String exUrl : except) {
                // 放行
                if (servletPath.startsWith(exUrl)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 有多个过滤器时，过滤器执行的顺序，数字越小越优先执行
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * filterType：pre，routing，post，error
     * 前，中，后和错误时候的过滤
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 签名检查
     */
	private Object sighCheck() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String userId = request.getHeader("userId");
        String timestamp = request.getHeader("timestamp");
        String signature = request.getHeader("signature");

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(signature)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        if (!checkSignature(userId, timestamp, signature)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        return null;
    }

    /**
     * 校验签名
     *
     * @param parentId
     * @param timestamp
     * @param signature
     * @return
     */
    private Boolean checkSignature(String parentId, String timestamp, String signature) {
        String id = base64.encodeToString(parentId.getBytes());
        String temp = id + timestamp;
        String md5 = DigestUtils.md5Hex(temp);
        return signature.equals(md5);
    }

    public static void main(String[] args) {
        // 阳光守护
        Base64 base64 = new Base64();
        String parentId = "1";
        String timestamp = "1556582400000";
        String id = base64.encodeToString(parentId.getBytes());
        String temp = id + timestamp;
        String md5 = DigestUtils.md5Hex(temp);
        System.out.println(md5);

    }
}
