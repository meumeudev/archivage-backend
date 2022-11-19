package sn.webg.archivage.service.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.security.rules.SecurityRule;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSecurityManager {

    // Collects all SecurityRule beans declared under package "security.rules"
    final List<SecurityRule> securityRules;

    /**
     * Collect all {@link SecurityRule} beans declared under package "security.rules"
     * to be configure as new security rule into the HttpSecurity passed through parameters
     * */
    public void manageSecurityRules(HttpSecurity http) throws Exception {
        // Configure HttpSecurity with all beans security rules
        for(SecurityRule securityRule : securityRules) {
            securityRule.configure(http);
        }
    }

}
