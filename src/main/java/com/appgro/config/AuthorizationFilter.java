package com.appgro.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthorizationFilter extends OncePerRequestFilter {

    public AuthorizationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if (headerIsValid(header)) {
            try {
                Claims claims = getClaims(getToken(header));
                Optional.ofNullable(claims.getSubject())
                        .ifPresent(username -> setUserContext(claims, username));
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        goToNextFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    private boolean headerIsValid(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    private String getToken(String header) {
        return header.replace("Bearer ", "");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConfig.JWT_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private void setUserContext(Claims claims, String username) {
        User userDetails = new User(username, "", getGrantedAuthorities(claims));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                getGrantedAuthorities(claims)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @SuppressWarnings("unchecked")
    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        return ((List<String>) claims.get("authorities")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private void goToNextFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
