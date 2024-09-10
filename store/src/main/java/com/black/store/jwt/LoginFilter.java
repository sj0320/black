package com.black.store.jwt;

import com.black.store.config.auth.CustomUserDetails;
import com.black.store.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password,null);
//        return authenticationManager.authenticate(token);
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // 내부에서 ObjectMapper 인스턴스 생성

            // JSON 본문에서 사용자 이름과 비밀번호 추출
            UserDTO.SaveRequest userDTO= objectMapper.readValue(request.getInputStream(), UserDTO.SaveRequest.class);
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);
            return authenticationManager.authenticate(token);

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        String username = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        System.out.println(role);

        String token = jwtUtil.createJwt(username,role,60*60*1000L);
        response.addHeader("Authorization","Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,HttpServletResponse response,AuthenticationException failed){
        response.setStatus(401);
    }

}
