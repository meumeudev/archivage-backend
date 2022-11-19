package sn.webg.archivage.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.webg.archivage.service.models.OrganizationDTO;
import sn.webg.archivage.service.services.OrganizationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/organizations")
@Tag(name = "organization-controller", description = "organization controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationController {

    final OrganizationService organizationService;

    @Operation(summary = "Create organization", description = "this endpoint take input organization and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organization was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    public OrganizationDTO createOrganization(@RequestBody @Valid OrganizationDTO organizationDTO) {

        return organizationService.createOrganization(organizationDTO);
    }


    @Operation(summary = "Update the organization", description = "this endpoint take input organization and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organization was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDTO updateOrganization(@Parameter(name = "id", description = "the organization id to read")
                                              @PathVariable String id, @RequestBody @Valid OrganizationDTO organizationDTO) {

        organizationDTO.setId(id);

        return organizationService.updateOrganization(organizationDTO);
    }

    @Operation(summary = "delete the organization", description = "Delete organization , it take input id organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organization was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@Parameter(name = "id", description = "the organization id deleted") @PathVariable String id) {
        organizationService.deleteOrganization(id);
    }

    @Operation(summary = "Read the organization", description = "This endpoint is used to read organization  it take input id organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDTO readOrganization(@Parameter(name = "id", description = "the organization id to read") @PathVariable String id) {

        return organizationService.readOrganization(id);
    }


    @Operation(summary = "Read page organization", description = "This endpoint is used to read all organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrganizationDTO> readAllOrganizations(
            Pageable pageable,
            @Parameter(name = "name", description = "value of name used to filter list organization ") @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "phone", description = "value of phone used to filter list organisation ") @RequestParam(value = "phone", required = false) String phone,
            @Parameter(name = "responsible", description = "value of responsible used to filter list organisation ") @RequestParam(value = "responsible", required = false) String responsible,
            @Parameter(name = "organizationTypeId", description = "value of organizationTypeId used to filter list organisation ") @RequestParam(value = "organizationTypeId", required = false) String organizationTypeId
    ) {
        return organizationService.readAllOrganizations(pageable, name, phone, responsible, organizationTypeId);
    }
}
