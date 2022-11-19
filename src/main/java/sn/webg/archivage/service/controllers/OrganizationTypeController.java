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
import sn.webg.archivage.service.models.OrganizationTypeDTO;
import sn.webg.archivage.service.services.OrganizationTypeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/organizationTypes")
@Tag(name = "organizationType-controller", description = "organizationType controller")
@RequiredArgsConstructor
public class OrganizationTypeController {

    final OrganizationTypeService organizationTypeService;

    @Operation(summary = "Create organizationType", description = "this endpoint take input organizationType and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organizationType was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationTypeDTO createOrganizationType(@RequestBody @Valid OrganizationTypeDTO organizationTypeDTO) {

        return organizationTypeService.createOrganizationType(organizationTypeDTO);
    }


    @Operation(summary = "Update the organizationType", description = "this endpoint take input organizationType and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organizationType was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationTypeDTO updateOrganizationType(@Parameter(name = "id", description = "the organizationType id to read") @PathVariable String id, @RequestBody @Valid OrganizationTypeDTO organizationTypeDTO) {

        organizationTypeDTO.setId(id);

        return organizationTypeService.updateOrganizationType(organizationTypeDTO);
    }


    @Operation(summary = "delete the organizationType", description = "Delete organizationType , it take input id organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the organizationType was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizationType(@Parameter(name = "id", description = "the organizationType id deleted") @PathVariable String id) {

        organizationTypeService.deleteOrganizationType(id);
    }

    @Operation(summary = "Read the organizationType", description = "This endpoint is used to read organizationType  it take input id organizationType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationTypeDTO readOrganizationType(@Parameter(name = "id", description = "the organizationType id to read") @PathVariable String id) {

        return organizationTypeService.readOrganizationType(id);
    }


    @Operation(summary = "Read page organizationType", description = "This endpoint is used to read all organizationType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrganizationTypeDTO> readAllOrganizationTypes(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list organizationType ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "label", description = "value of label used to filter list organisationType ") @RequestParam(value = "label", required = false) String label
    ) {
        return organizationTypeService.readAllOrganizationTypes(pageable, code, label);
    }
}
