package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import sn.webg.archivage.service.models.SecurityPermissions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Allow configuration of API access through {@link HttpSecurity}
 * when a bean of this type is initialised.<br/>
 * {@code httpMethod} defines API's Http method (GET, POST, PUT, DELETE).<br/>
 * {@code apiPatterns} lists all URI access through {@code httpMethod} which need to be secure.<br/>
 * {@code conditions} conditions needed to be applied for access to the API : role + control method.
 * Conditions are concatenated with "or" preposition and "role" or "control method" can be not specified.<br/>
 * By default, all user need to be authenticated before API call, but it can be turn off
 * when {@code withoutAuthentication} is turned to {@code true}.<br/>
 * WARNING : {@code withoutAuthentication} is only for API called by external
 * application (example : SIPS) with other method of verification (example : seal).
 *
 * @see HttpSecurity#antMatcher(String)
 */
@Slf4j
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityRule {

    /**
     * Http method GET, POST, PUT, PATCH or DELETE
     */
    @NotNull
    HttpMethod httpMethod;

    /**
     * List of APIs patterns
     */
    @NotEmpty
    @Singular
    List<String> apiPatterns;

    /**
     * Defines if user should be authenticated
     * true by default
     */
    @Builder.Default
    boolean authenticated = true;

    /**
     * list of conditions to authorize the access to the listed APIs
     */
    @Builder.Default
    List<Condition> conditions = new ArrayList<>();

    /**
     * Applies new security configuration into the HttpSecurity parameter
     * @param httpSecurity HttpSecurity
     * @throws Exception
     */
    public void configure(HttpSecurity httpSecurity) throws Exception {
        String authenticationAccess = authenticated ? "isAuthenticated()" : null;

        //allConditions = "(condition1) or (condition2) or ..."
        String allConditions = conditions.stream()
                .map(Condition::toFormat)
                .filter(s -> s != null && !s.isEmpty())
                .collect(() -> new StringJoiner(" or ", "(", ")").setEmptyValue(""), StringJoiner::add, StringJoiner::merge)
                .toString();

        //access = "(isAuthenticated()|isAnonymous()) and (allConditions)"
        String access = Arrays.stream(new String[]{authenticationAccess, allConditions})
                .filter(s -> s != null && !s.isEmpty())
                .collect(() -> new StringJoiner(" and ", "(", ")").setEmptyValue(""), StringJoiner::add, StringJoiner::merge)
                .toString();


        if (access.isEmpty()) {
            log.trace("Configuring access for URIs {} \"{}\" with no condition", httpMethod, apiPatterns);
            httpSecurity.authorizeRequests()
                    .antMatchers(httpMethod, apiPatterns.toArray(new String[]{}))
                    .permitAll();
        } else {
            log.trace("Configuring access for URIs {} \"{}\" with this condition : {}", httpMethod, apiPatterns, access);
            httpSecurity.authorizeRequests()
                    .antMatchers(httpMethod, apiPatterns.toArray(new String[]{}))
                    .access(access);
        }
    }

    /**
     * Define new condition that should be close with {@link Condition#end()}
     * @return new condition
     */
    public Condition condition() {
        Condition condition = new Condition(this);
        this.conditions.add(condition);
        return condition;
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Condition {

        /**
         * List of authorized permissions
         */
        Set<SecurityPermissions> permissions = new HashSet<>();

        /**
         * Refers to a method that will be executed for more precise control.
         */
        String controlMethod;

        /**
         * Parent security rule
         */
        final SecurityRule securityRule;

        /**
         * Adds a new permission to the condition permissions
         * @param permission
         * @return
         */
        public Condition hasPermission(SecurityPermissions permission) {
            this.permissions.add(permission);
            return this;
        }

        /**
         * Refers to a method that will be executed for more precise control.
         * @param controlMethod
         * @return
         */
        public Condition controlMethod(String controlMethod) {
            this.controlMethod = controlMethod;
            return this;
        }

        /**
         * Adds a new condition
         * @return
         */
        public Condition or() {
            return securityRule.condition();
        }

        /**
         * Closes the conditions
         * @return
         */
        public SecurityRule end() {
            return securityRule;
        }

        /**
         * Return condition in E-L spring expression.<br/>
         * If one is not specified, the "and" is not provided.
         *
         * @return "(hasAnyAuthority(permissions...) and controlMethod)"
         */
        private String toFormat() {
            StringJoiner result = new StringJoiner(" and ", "(", ")").setEmptyValue("");
            if (!this.permissions.isEmpty()) {
                String anyAuthorities = this.permissions.stream()
                        .map(r -> "T(" + SecurityPermissions.class.getName() + ")." + r.name())
                        .collect(Collectors.joining(","));
                result.add("hasAnyAuthority(" + anyAuthorities + ")");
            }
            if (!StringUtils.isEmpty(controlMethod)) {
                result.add(controlMethod);
            }
            return result.toString();
        }

    }
}

