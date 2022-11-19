package sn.webg.archivage.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sn.webg.archivage.service.models.Module;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.services.SecurityService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
@Tag(name = "security-controller", description = "Security controller")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    final SecurityService securityService;
    final UserRepository userRepository;

    @Operation(summary = "Read All permissions", description = "This endpoint is used to read all permissions  it take input name module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/permissions")
    @ResponseStatus(HttpStatus.OK)
    public Set<SecurityPermissions> readAllPermissionsByModules(@Parameter(name = "module", description = "the module name to read") @RequestParam(value = "module", required = false) Module module) {
        return securityService.readAllPermissionsByModules(module);
    }

    @Operation(summary = "Read All modules", description = "This endpoint is used to read all modules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/modules")
    @ResponseStatus(HttpStatus.OK)
    public List<Module> readAllModules() {
        return securityService.readAllModules();
    }
}
