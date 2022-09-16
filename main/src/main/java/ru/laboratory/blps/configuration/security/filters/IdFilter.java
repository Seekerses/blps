package ru.laboratory.blps.configuration.security.filters;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Component
public class IdFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().noneMatch(v -> v.getAuthority().equals("ROLE_ANONYMOUS"))){
            httpServletRequest.setAttribute("UserId", ((User) userService.loadUserByUsername((String) authentication.getPrincipal())).getId());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
