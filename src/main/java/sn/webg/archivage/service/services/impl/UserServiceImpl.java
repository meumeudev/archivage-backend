package sn.webg.archivage.service.services.impl;

import io.jsonwebtoken.lang.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.exceptions.PasswordException;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.AgentMapper;
import sn.webg.archivage.service.mappers.UserMapper;
import sn.webg.archivage.service.models.AgentDTO;
import sn.webg.archivage.service.models.UserDTO;
import sn.webg.archivage.service.models.UserWithPasswordDto;
import sn.webg.archivage.service.repositories.RoleRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.services.UserService;

import java.text.MessageFormat;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    final AgentMapper agentMapper;

    final PasswordEncoder encoder;
    static final String USER_IDENTIFIER_NOT_FOUND_MESSAGE = "[User] Not found  {0}";

    final RoleRepository roleRepository;


    @Override
    public UserDTO createUser(UserWithPasswordDto userWithPasswordDto) {
        userWithPasswordDto.setUserId(null);

        UserEntity userEntity = userMapper.asEntity(userWithPasswordDto);
        userEntity.setPassword(encoder.encode(userWithPasswordDto.getPassword()));

        userEntity.getAgent().setActivated(true);

        UserEntity createUser = userRepository.save(userEntity);

        log.info("create account ok id {}", createUser.getUserId());
        log.trace("create user ok  {}", createUser);

        return userMapper.asDto(createUser);
    }

    @Override
    public AgentDTO updateAgent(String userId, AgentDTO agentDTO) {
        UserEntity userDb = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userId)));
        agentDTO.setActivated(userDb.getAgent().isActivated());
        userDb.setAgent(agentMapper.asEntity(agentDTO));

        if (Objects.nonNull(agentDTO.getRoleName())) {
            roleRepository.findByName(agentDTO.getRoleName()).ifPresent(userDb::setRole);
        }

        UserEntity userUpdate = userRepository.save(userDb);


        log.info("update user ok Id {}", userUpdate.getUserId());
        log.trace("update user ok Id {}", userUpdate);

        return agentMapper.asDto(userUpdate.getAgent());
    }

    @Override
    public UserDTO readUser(String userId) {

        var user = userRepository.findById(userId)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userId)));

        log.info("read user ok username {}", userId);
        log.trace("read user ok user {}", user);

        return user;
    }

    @Override
    public UserDTO readUserByUsername(String userName) {
        var user = userRepository.findOneByUsername(userName)
                .map(userMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userName)));

        log.info("read user ok username {}", userName);
        log.trace("read user ok userId {}", user);

        return user;
    }

    @Override
    public void deleteUser(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, userId));
        }

        userRepository.deleteById(userId);
        log.info("delete user ok userId {}", userId);
    }

    @Override
    public Page<UserDTO> readAllUsers(Pageable pageable, String lastName, String firstName, String email, String address, Boolean activated) {

        var users = userRepository.readAllByFilters(pageable, lastName, firstName, email, activated, address)
                .map(userMapper::asDto);

        log.trace("list users get ok {}", users);

        return users;
    }

    @Override
    public UserDTO updatePassword(String username,String oldPassword, String password) {
        var userEntityDb = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(USER_IDENTIFIER_NOT_FOUND_MESSAGE, username)));
        if(!encoder.encode(oldPassword).equals(userEntityDb.getPassword())){
            throw  new PasswordException("L'ancien mot de passe est incorrect");
        }
        userEntityDb.setPassword(encoder.encode(password));
        var userUpdate = userRepository.save(userEntityDb);

        return userMapper.asDto(userUpdate);
    }
}
