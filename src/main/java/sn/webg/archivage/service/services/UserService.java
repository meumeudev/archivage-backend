package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.AgentDTO;
import sn.webg.archivage.service.models.UserDTO;
import sn.webg.archivage.service.models.UserWithPasswordDto;

public interface UserService {

    UserDTO createUser(UserWithPasswordDto userDTO);

    AgentDTO updateAgent(String userId, AgentDTO agentDTO);

    UserDTO readUser(String userId);

    UserDTO readUserByUsername(String userName);

    void deleteUser(String userId);

    Page<UserDTO> readAllUsers(Pageable pageable, String lastName, String firstName, String email, String address, Boolean activated);

    UserDTO updatePassword(String id,String oldPassword, String password);

}

