package sn.webg.archivage.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.webg.archivage.service.models.request.AuthUsername;
import sn.webg.archivage.service.models.response.SignInAuthentication;
import sn.webg.archivage.service.services.AuthenticationService;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1")
@Tag(name = "authentication-controller", description = "Authentication controller")
@RequiredArgsConstructor
public class AuthenticationController {

    final AuthenticationService authenticationService;

    @Operation(summary = "Authentication by account email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(value = "/auth/authenticate", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public SignInAuthentication authenticate(
            @Parameter(description = "Username and password user", required = true) @Valid @RequestBody AuthUsername authUsername) {
        return authenticationService.authenticate(authUsername);
    }
}
