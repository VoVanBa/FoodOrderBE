package com.example.foodApp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        if (isbyPassToken(request)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if(jwt != null){
            jwt=jwt.substring(7);


            try {
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRECT_KEY.getBytes());

                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email =String.valueOf(claims.get("email"));

                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication= new UsernamePasswordAuthenticationToken(email,null,auth);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("invalid token.....");
            }
        }

        filterChain.doFilter(request,response);
    }


//    private boolean isbyPassToken(@NonNull HttpServletRequest request) {
//        final List<Pair<String, String>> byPassTokens = Arrays.asList(
//
//                Pair.of("/products", "GET"),
//                //   Pair.of("/orders", "GET"),
////                Pair.of("/products", "POST"),
//                Pair.of("/users/login", "POST"),
//                Pair.of("/users/register", "POST")
//        );
//        String requestPath = request.getServletPath();
//        String requestMethod = request.getMethod();
//
//        if (requestPath.equals("/orders")
//                && requestMethod.equals("GET")) {
//            // Allow access to %s/orders
//            return true;
//        }
//
//        for (Pair<String, String> bypassToken : byPassTokens) {
//            if (requestPath.contains(bypassToken.getFirst())
//                    && requestMethod.equals(bypassToken.getSecond())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}
