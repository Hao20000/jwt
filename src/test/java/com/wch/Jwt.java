package com.wch;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class Jwt {

    private static long tokenExpiration = 1000 * 60 * 60 * 24;
    private static String tokenSignKey = "wuchenghao";


    @Test
    public void testJWT() {
        JwtBuilder jwtBuilder = Jwts.builder();
        //头 载荷 签名哈希
        String jwtToken = jwtBuilder
                //头
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                //载荷 ： 自定义信息
                .claim("nickname", "Helen")
                .claim("avatar", "1.jpg")
                .claim("role", "admin")

                //载荷：默认信息
                .setSubject("wch_user") //令牌主题
                .setIssuer("wuchenghao") //签发者
                .setAudience("wuchenghao") //接收方
                .setIssuedAt(new Date()) //令牌签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //令牌过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20)) //令牌生效时间
                .setId(UUID.randomUUID().toString())

                //签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)

                //组装jwt字符串
                .compact();

        System.out.println(jwtToken);
    }

    @Test
    public void testGetJwtInfo() {
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrbmFtZSI6IkhlbGVuIiwiYXZhdGFyIjoiMS5qcGciLCJyb2xlIjoiYWRtaW4iLCJzdWIiOiJ3Y2hfdXNlciIsImlzcyI6Ind1Y2hlbmdoYW8iLCJhdWQiOiJ3dWNoZW5naGFvIiwiaWF0IjoxNjUxODE4Njk1LCJleHAiOjE2NTE5MDUwOTUsIm5iZiI6MTY1MTgxODcxNSwianRpIjoiMWFjNTIxMzgtZTFmMi00N2U4LThjODktMDM5YjA1YzQ3MmMwIn0.4WdeSkO-IUT2BaPzM4oXvUh41v-mdgdJwmHH0xc9Fns";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(tokenSignKey).parseClaimsJws(jwtToken);

        Claims claims = claimsJws.getBody();
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String role = (String) claims.get("role");

        System.out.println(nickname + " " + avatar + " " + role);

        String id = claims.getId();
        System.out.println(id);
    }
}
