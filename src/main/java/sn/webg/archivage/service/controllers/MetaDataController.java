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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sn.webg.archivage.service.models.MetaDataDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.services.MetaDataService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/metaDatas")
@Tag(name = "metaData-controller", description = "MetaData controller")
@RequiredArgsConstructor
public class MetaDataController {

    private final MetaDataService metaDataService;

    @Operation(summary = "Create metaData", description = "this endpoint take input metaData and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the metaData was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetaDataDTO createMetaData(@RequestBody @Valid MetaDataDTO metaData) {
        return metaDataService.createMetaData(metaData);
    }


    @Operation(summary = "Update the metaData", description = "this endpoint take input a metaData and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the metaData was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetaDataDTO updateMetaData(@Parameter(description = "MetaData identifier", name = "id", required = true) @PathVariable(value = "id") String id, @RequestBody @Valid MetaDataDTO metaData) {
        metaData.setId(id);
        return metaDataService.updateMetaData(metaData);
    }

    @Operation(summary = "delete the metaData", description = "Delete metaData , it take input name metaData")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the metaData was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMetaData(@Parameter(name = "id", description = "the metaData id deleted") @PathVariable String id) {
        metaDataService.deleteMetaData(id);
    }

    @Operation(summary = "Read the metaData", description = "This endpoint is used to read metaData  it take input name metaData")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetaDataDTO readMetaData(@Parameter(name = "id", description = "the metaData id to read") @PathVariable String id) {
        return metaDataService.readMetaData(id);
    }

    @Operation(summary = "Read all metaDatas", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MetaDataDTO> readByFilters(
            Pageable pageable,
            @Parameter(name = "label", description = "value of label used to filter list metaData ") @RequestParam(value = "label", required = false) String label,
            @Parameter(name = "metaDataType", description = "value of metaDataType used to filter list metaData ") @RequestParam(value = "metaDataType", required = false) MetaDataType metaDataType
    ) {
        return metaDataService.readByFilters(label, metaDataType, pageable);

    }
}
