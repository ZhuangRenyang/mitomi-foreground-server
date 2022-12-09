package com.mito.mitomi.foreground.server.filter;

import com.alibaba.fastjson.JSON;
import com.mito.mitomi.foreground.server.security.LoginPrincipal;
import com.mito.mitomi.foreground.server.util.JwtUtils;
import com.mito.mitomi.foreground.server.web.JsonResult;
import com.mito.mitomi.foreground.server.web.ServiceCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 *过滤器类
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {//

    // 最终，过滤器可以选择“阻止”或“放行”
    // 如果选择“阻止”，则后续的所有组件都不会被执行
    // 如果选择“放行”，会执行“过滤器链”中剩余的部分，甚至继续向后执行到控制器等组件
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 此方法是任何请求都会执行的方法
        log.debug("执行到JwtAuthorizationFilter过滤器");

        // 清除Security的上下文
        // 如果不清除，只要此前存入过信息，即使后续不携带JWT，上下文中的登录信息依然存在
        SecurityContextHolder.getContext();

        // 从请求头中获取JWT
        String jwt = request.getHeader("Authorization");
        log.debug("从请求头中获取的JWT数据:{}", jwt);

        //先判断是否获取到了有效的jwt数据，无jwt数据-放行
        if (!StringUtils.hasText(jwt)) {//不为null && 不为Empty() && 包含文本containsText();
            log.debug("请求头中的jwt数据是无效的,直接放行");
            filterChain.doFilter(request, response);
            return;
        }


        //解析
        Claims claims = null;
        try {
            claims = JwtUtils.parse(jwt);
        } catch (
                ExpiredJwtException e) {
            log.debug("解析JWT失败,JWT过期:{},{}", e.getClass().getName(), e.getMessage());
            String errorMessage = "登录信息过期,请重新登录!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (
                SignatureException e) {
            log.debug("解析JWT失败,签名错误:{},{}", e.getClass().getName(), e.getMessage());
            String errorMessage = "登录信息过期,请重新登录!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (
                MalformedJwtException e) {
            log.debug("解析JWT失败,数据错误:{},{}", e.getClass().getName(), e.getMessage());
            String errorMessage = "登录信息过期,请重新登录!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        } catch (
                Throwable e) {
            log.debug("解析JWT失败,错误详情:{},{}", e.getClass().getName(), e.getMessage());
            String errorMessage = "获取登录信息失败,请重新登录!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID, errorMessage);
            String jsonResultString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(jsonResultString);
            return;
        }

        Object id = claims.get("id");
        log.debug("从JWT中解析得到管理员id", id);
        Object username = claims.get("username");
        log.debug("从JWT中解析得到用户名:{}", username);

        LoginPrincipal loginPrincipal = new LoginPrincipal();
        loginPrincipal.setId(Long.parseLong(id.toString()));
        loginPrincipal.setUsername(username.toString());

        //准备权限
        Object authoritiesString = claims.get("authorities");
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesString.toString(), SimpleGrantedAuthority.class);

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(loginPrincipal, null, authorities);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //"放行"
        filterChain.doFilter(request, response);
    }
}
