package com.wonya.demo.utils;

import com.wonya.demo.vo.Users;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Security {

    public String sha256(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(msg.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b: md.digest()) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "false";
        }
    }

    private final String secretKey = "demo";

    public String generateJwtToken(Users user) {
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user.getId());
        builder.setHeader(createHeader());
        builder.setClaims(createClaims(user));
        builder.setExpiration(createExpireDateForOneYear());
        builder.signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);

            System.out.printf("expireTime = " + claims.getExpiration());
            System.out.printf("id = " + claims.get("id"));
            System.out.printf("name = " + claims.get("name"));
            return true;

        } catch (ExpiredJwtException exception) {
            System.out.printf("Expired!");
            return false;
        } catch (JwtException exception) {
            System.out.printf("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.printf("Token Null");
            return false;
        }
    }

    public String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createClaims(Users user) {
        // 비공개 클레임으로 사용자의 이름과 이메일을 설정, 세션 처럼 정보를 넣고 빼서 쓸 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("name", user.getName());

        return claims;
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    private String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("id");
    }
}
