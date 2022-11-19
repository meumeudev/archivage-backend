package sn.webg.archivage.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.webg.archivage.service.models.AgentDTO;
import sn.webg.archivage.service.models.UserDTO;
import sn.webg.archivage.service.models.UserWithPasswordDto;
import sn.webg.archivage.service.services.UserService;


@RequestMapping("/v1/agents")
@Tag(name = "agent controller", description = "Set of API to manage agents")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create user", description = "this endpoint take input user and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserWithPasswordDto user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Update the user", description = "this endpoint take input a user and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AgentDTO updateAgent(@Parameter(name = "userId", description = "the user id updated") @PathVariable String userId, @RequestBody AgentDTO agentDTO) {
        return userService.updateAgent(userId, agentDTO);
    }

    @Operation(summary = "Read the user", description = "This endpoint is used to read user  it take input id user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO readUser(@Parameter(name = "userId", description = "the user id to read") @PathVariable String userId) {
        return userService.readUser(userId);
    }

    @Operation(summary = "Read the user", description = "This endpoint is used to read user  it take input id user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/username/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO readUserByUsername(@Parameter(name = "userName", description = "the user id to read") @PathVariable String userName) {
        return userService.readUserByUsername(userName);
    }

    @Operation(summary = "delete the user", description = "Delete user , it take input   id user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Parameter(name = "userId", description = "the user id deleted") @PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @Operation(summary = "Read all agent", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<UserDTO> readAllUsers(
            Pageable pageable,
            @Parameter(name = "lastName", description = "value of lastName used to filter list agent ") @RequestParam(value = "lastName", required = false) String lastName,
            @Parameter(name = "firstName", description = "value of firstName used to filter list agent ") @RequestParam(value = "firstName", required = false) String firstName,
            @Parameter(name = "email", description = "value of email used to filter list agent ") @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "address", description = "value of address used to filter list agent ") @RequestParam(value = "address", required = false) String address,
            @Parameter(description = "Agent activated") @RequestParam(value = "activated", required = false) Boolean activate
    ) {

        return userService.readAllUsers(pageable, lastName, firstName, email, address, activate);

    }

    @Operation(summary = "Update the password user", description = "this endpoint take input a user and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/password/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updatePassword(@Parameter(name = "userName", description = "the userNam of user to updated") @PathVariable String userName, @Parameter(name = "oldpassword", description = "the old password of User") @RequestParam(name = "oldpassword") String oldpassword, @Parameter(name = "password", description = "the new password of User") @RequestParam(name = "password") String password) {
        return userService.updatePassword(userName, oldpassword, password);
    }


}
