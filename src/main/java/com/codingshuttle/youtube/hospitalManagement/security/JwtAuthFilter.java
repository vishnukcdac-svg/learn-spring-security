package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.User;
import com.codingshuttle.youtube.hospitalManagement.repository.UserRepository;
import error.GlobalExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

    private final  UserRepository userRepository;
    private final AuthUtil authUtil;
  private  final HandlerExceptionResolver handlerExceptionResolver;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("incoming request {}", request.getRequestURI());
            // extract the token from the header
            final String requestTockenHeader = request.getHeader("Authorization");

            // check if the token is present and starts with Bearer
            if (requestTockenHeader == null || !requestTockenHeader.startsWith("Bearer")) {
                // if token is not present or not starts with Bearer
                filterChain.doFilter(request, response);
                return;
            }
            // extract the token from the header
            String token = requestTockenHeader.split("Bearer")[1].trim();

            String userName = authUtil.getUserNameFromToken(token);
            //check if the token is valid
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(userName).orElseThrow();
                //create authentication object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                //set authentication object in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
            // continue the filter chain
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
