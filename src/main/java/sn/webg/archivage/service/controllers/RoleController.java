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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sn.webg.archivage.service.models.RoleDTO;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.services.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@Tag(name = "role-controller", description = "Role controller")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create role", description = "this endpoint take input role and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the role was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO createRole(@RequestBody @Valid RoleDTO role) {
        return roleService.createRole(role);
    }


    @Operation(summary = "Update the role", description = "this endpoint take input a role and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the role was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO updateRole(@RequestBody @Valid RoleDTO role) {
        return roleService.updateRole(role);
    }

    @Operation(summary = "delete the role", description = "Delete role , it take input name role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the role was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{roleName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleDTO(@Parameter(name = "roleName", description = "the role name deleted") @PathVariable String roleName) {
        roleService.deleteRole(roleName);
    }

    @Operation(summary = "Read the role", description = "This endpoint is used to read role  it take input name role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO readRole(@Parameter(name = "roleName", description = "the role name to read") @PathVariable String roleName) {
        return roleService.readRole(roleName);
    }

    @Operation(summary = "Read all roles", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RoleDTO> readAllRoles(
            Pageable pageable,
            @Parameter(name = "roleName", description = "value of name used to filter list role ") @RequestParam(value = "roleName", required = false) String roleName,
            @Parameter(name = "List permissions", description = "value of list permissions used to filter list role ") @RequestParam(value = "permissions", required = false) List<SecurityPermissions> permissions
    ) {
        return roleService.readByFilters(pageable, roleName, permissions);

    }
}
