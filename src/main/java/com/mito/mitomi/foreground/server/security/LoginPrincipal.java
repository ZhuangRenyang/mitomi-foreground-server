package com.mito.mitomi.foreground.server.security;

import lombok.Data;

import java.io.Serializable;
/**
 * 当前登录的当事人
 * 验证类
 */
@Data
public class LoginPrincipal implements Serializable {
    /**
     * 当前登录的用户id
     */
    private Long id;

    /**
     * 当前登录的用户名
     */
    private String username;
}
