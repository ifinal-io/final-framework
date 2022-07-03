/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.security.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ifinalframework.json.Json;

import io.jsonwebtoken.*;

/**
 * JwtTokenUtil.
 *
 * @author ilikly
 * @version 1.3.3
 * @since 1.3.3
 */
public class JwtTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "Authorization";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    // 过期时间
    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    // 应用密钥
    public static final String SECRET = "finalframework/jwt/secret/JwtTokenUtil/Default";
    // 角色权限声明
    private static final String ROLE_CLAIMS = "roles";

    /**
     * 生成Token
     */
    public static String createToken(String username, Collection<String> roles) {
        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, roles);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        return token;
    }

    public static String createToken(String payload) {
        return Jwts.builder()
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

    }

    public static String getPayload(String token){
        return Json.toJson(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody());
    }


    /**
     * 校验Token
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从Token中获取username
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 从Token中获取用户角色
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.get("roles").toString();
    }

    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}


