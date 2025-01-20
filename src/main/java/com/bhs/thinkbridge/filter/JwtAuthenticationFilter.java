package com.bhs.thinkbridge.filter;

import com.bhs.thinkbridge.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final RequestMatcher[] matchers = new RequestMatcher[]{
            new AntPathRequestMatcher("api/v1/auth/signup", HttpMethod.POST.name()),
            new AntPathRequestMatcher("api/v1/auth/signin", HttpMethod.POST.name())
    };

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        for(RequestMatcher matcher : matchers){
            if(matcher.matches(request)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getCookies() == null){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            Cookie[] cookies = request.getCookies();
            String jwtToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JwtToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
            if (jwtToken != null) {
                String email = jwtService.extractUsername(jwtToken);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (email != null && authentication == null) {
                    UserDetails user = userDetailsService.loadUserByUsername(email);
                    if (jwtService.isTokenValid(jwtToken, user)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                user.getUsername(), null,user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }

}
