package ru.laboratory.blps.configuration.security.providers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class JaasAuthorityGranter implements AuthorityGranter {

    private final UserDetailsService userDetailsService;

    @Override
    public Set<String> grant(Principal principal) {
        return userDetailsService.loadUserByUsername(principal.getName()).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
