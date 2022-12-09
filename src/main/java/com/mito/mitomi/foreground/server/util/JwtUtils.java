package com.mito.mitomi.foreground.server.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
/*
 * Jwt加密解密工具类
 */
@Slf4j
public class JwtUtils {
    /**
     * 签名密钥
     */
    private static final String SECRET_KEY = "fgS1sdfA3gsFSF5GS";

    /**
     * JWT过期时间：以分钟单位
     */
    private static final long EXPIRATION_I_MINUTE = 7 * 24 * 60;

    /**
     * 私有构造方法，避免外部随意创建对象
     */
    private JwtUtils() {}

    /**
     * 生成JWT
     *
     * @param claims
     * @return
     */
    public static String generate(Map<String,Object> claims){

        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_I_MINUTE*60*1000);

        String jwt = Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setHeaderParam("alg","HS256")
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
        log.debug("jwt: {}",jwt);
        return jwt;
    }
    /**
     * 解析JWT
     *
     * @param jwt
     * @return
     */
    public static Claims parse(String jwt){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }
}
