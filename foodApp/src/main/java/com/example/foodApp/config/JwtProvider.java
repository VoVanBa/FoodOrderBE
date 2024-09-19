package com.example.foodApp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.util.*;

@Service
public class JwtProvider {
    private SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRECT_KEY.getBytes());

    public String gennerateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorites= auth.getAuthorities();
        String roles= popularAuthorities(authorites);

        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration((new Date(new Date().getTime()+3600000)))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration((new Date(new Date().getTime()+3600000))).signWith(key)
                .compact();
    }
    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email =String.valueOf(claims.get("email"));

        return email;
    }

    public String popularAuthorities(Collection <? extends GrantedAuthority> authorities){
        Set<String> auths= new HashSet<>();

        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }

        return String.join(",",auths);
    }
}
