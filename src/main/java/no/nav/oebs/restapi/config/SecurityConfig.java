package no.nav.oebs.restapi.config;

import org.springframework.context.annotation.Configuration;
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableJwtTokenValidation(ignore = { "org.springframework", "org.springdoc" })
public class SecurityConfig {

}