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
import sn.webg.archivage.service.models.FolderTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.services.FolderTypeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/folderTypes")
@Tag(name = "folderType-controller", description = "FolderType controller")
@RequiredArgsConstructor
public class FolderTypeController {

    private final FolderTypeService folderTypeService;

    @Operation(summary = "Create folderType", description = "this endpoint take input folderType and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folderType was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FolderTypeDTO createFolderType(@RequestBody @Valid FolderTypeDTO folderType) {
        return folderTypeService.createFolderType(folderType);
    }


    @Operation(summary = "Update the folderType", description = "this endpoint take input a folderType and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folderType was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FolderTypeDTO updateFolderType(@Parameter(description = "FolderType identifier", name = "id", required = true) @PathVariable(value = "id") String id, @RequestBody @Valid FolderTypeDTO folderType) {
        folderType.setId(id);
        return folderTypeService.updateFolderType(folderType);
    }

    @Operation(summary = "delete the folderType", description = "Delete folderType , it take input name folderType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folderType was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolderType(@Parameter(name = "id", description = "the folderType id deleted") @PathVariable String id) {
        folderTypeService.deleteFolderType(id);
    }

    @Operation(summary = "Read the folderType", description = "This endpoint is used to read folderType  it take input name folderType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FolderTypeDTO readFolderType(@Parameter(name = "id", description = "the folderType id to read") @PathVariable String id) {
        return folderTypeService.readFolderType(id);
    }

    @Operation(summary = "Read all folderTypes", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<FolderTypeDTO> readByFilters(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list folderType ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "label", description = "value of label used to filter list folderType ") @RequestParam(value = "label", required = false) String label,
            @Parameter(name = "metaDataList", description = "value of list metaDataId used to filter list folderType ") @RequestParam(value = "metaDataList", required = false) List<String> metaDataList
    ) {
        return folderTypeService.readByFilters(code, label, metaDataList, pageable);

    }
}
