package sn.webg.archivage.service.security.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.exceptions.UserNotActivatedException;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.repositories.RoleRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.security.SecurityCompositeRole;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {

        log.debug("Authenticating {}", username);

        if (new EmailValidator().isValid(username, null)) {
            return userRepository
                .findOneByAgentEmailIgnoreCase(username)
                .map(user -> createSpringSecurityUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with email {0} was not found in the database", username)));
        }

        return userRepository
            .findOneByUsername(username)
            .map(user -> createSpringSecurityUser(username, user))
            .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User {0}  was not found in the database", username)));
    }

    private User createSpringSecurityUser(String username, UserEntity user) {
        if (!user.getAgent().isActivated()) {
            throw new UserNotActivatedException(MessageFormat.format("User was not activated", username));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (Objects.nonNull(user.getRole())) {

            /* Setting all permissions when role is ADMIN */
            if (user.getRole().getName().equals(SecurityCompositeRole.ADMIN)) {
                grantedAuthorities.addAll(Arrays.stream(SecurityPermissions.values())
                        .map(securityPermissions ->  new SimpleGrantedAuthority(securityPermissions.name())).collect(Collectors.toList()));
            } else {

                /* Reading all role from user*/
                grantedAuthorities.addAll(roleRepository
                        .findByName(user.getRole().getName())
                        .map(RoleEntity::getPermissions)
                        .map(securityPermissions -> securityPermissions.stream().map(sp -> new SimpleGrantedAuthority(sp.name())).collect(Collectors.toList()))
                        .orElse(new ArrayList<>()));
            }

            /* Adding role to permissions */
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
