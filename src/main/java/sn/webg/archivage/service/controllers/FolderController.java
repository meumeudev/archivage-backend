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
import sn.webg.archivage.service.models.FolderDTO;
import sn.webg.archivage.service.services.FolderService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/folders")
@Tag(name = "folder-controller", description = "Folder controller")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "Create folder", description = "this endpoint take input folder and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folder was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FolderDTO createFolder(@RequestBody @Valid FolderDTO folder) {
        return folderService.createFolder(folder);
    }


    @Operation(summary = "Update the folder", description = "this endpoint take input a folder and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folder was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FolderDTO updateFolder(@Parameter(description = "Folder identifier", name = "id", required = true) @PathVariable(value = "id") String id, @RequestBody @Valid FolderDTO folder) {
        folder.setId(id);
        return folderService.updateFolder(folder);
    }

    @Operation(summary = "delete the folder", description = "Delete folder , it take input name folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the folder was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder(@Parameter(name = "id", description = "the folder id deleted") @PathVariable String id) {
        folderService.deleteFolder(id);
    }

    @Operation(summary = "Read the folder", description = "This endpoint is used to read folder  it take input name folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FolderDTO readFolder(@Parameter(name = "id", description = "the folder id to read") @PathVariable String id) {
        return folderService.readByIdFilters(id, null, null, false, null);
    }

    @Operation(summary = "Last reference at Folder", description = "This endpoint is used to read last reference at folder  it take input name folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/reference/_last")
    @ResponseStatus(HttpStatus.OK)
    public String lastReference(){
        return folderService.lastReference();
    }

    @Operation(summary = "Read all folders", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<FolderDTO> readByFilters(
            Pageable pageable,
            @Parameter( description = "value of map metaData key/value used to filter list folder ") @RequestParam(required = false) Map<String, Object> metaDataList,
            @Parameter( description = "value of name used to filter list folder ") @RequestParam(value = "name", required = false) String name,
            @Parameter( description = "value of reference used to filter list folder ") @RequestParam(value = "reference", required = false) String reference,
            @Parameter( description = "value of year used to filter list folder ") @RequestParam(value = "year", required = false) Integer year,
            @Parameter( description = "value of accessMetaData used to filter list folder ") @RequestParam(value = "accessMetaData", required = false) Set<String> accessMetaData,
            @Parameter( description = "value of identifier folderTypes used to filter list folder ") @RequestParam(value = "folderTypeIds", required = false) Set<String> idFolderTypes,
            @Parameter( description = "value of identifier folders used to filter list folder ") @RequestParam(value = "folderIds", required = false) Set<String> idFolders
        ) {
        return folderService.readByFilters(metaDataList, accessMetaData, name, reference, idFolderTypes, false, idFolders, year, pageable);

    }
}
