package ru.laboratory.blps.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.laboratory.blps.configuration.security.providers.BLPSLoginModule;
import ru.laboratory.blps.configuration.security.providers.JaasAuthorityGranter;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JaasConfiguration {
    @Bean
    public javax.security.auth.login.Configuration configuration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        Map<String, Object> options = new HashMap<>();
        options.put("userDetailsService", userDetailsService);
        options.put("passwordEncoder", passwordEncoder);
        AppConfigurationEntry configurationEntry = new AppConfigurationEntry(
                BLPSLoginModule.class.getCanonicalName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                options);
        Map<String, AppConfigurationEntry[]> configurationEntryMap = new HashMap<>();
        configurationEntryMap.put("SPRINGSECURITY", new AppConfigurationEntry[]{configurationEntry});
        return new InMemoryConfiguration(configurationEntryMap);
    }

    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider(
            javax.security.auth.login.Configuration configuration,
            UserDetailsService userDetailsService) {
        DefaultJaasAuthenticationProvider defaultJaasAuthenticationProvider = new DefaultJaasAuthenticationProvider();
        defaultJaasAuthenticationProvider.setConfiguration(configuration);
        defaultJaasAuthenticationProvider.setAuthorityGranters(new AuthorityGranter[]
                {new JaasAuthorityGranter(userDetailsService)});
        return defaultJaasAuthenticationProvider;
    }
}
