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
import sn.webg.archivage.service.models.DocumentTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.services.DocumentTypeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/documentTypes")
@Tag(name = "documentType-controller", description = "DocumentType controller")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @Operation(summary = "Create documentType", description = "this endpoint take input documentType and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the documentType was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentTypeDTO createDocumentType(@RequestBody @Valid DocumentTypeDTO documentType) {
        return documentTypeService.createDocumentType(documentType);
    }


    @Operation(summary = "Update the documentType", description = "this endpoint take input a documentType and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the documentType was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentTypeDTO updateDocumentType(@Parameter(description = "DocumentType identifier", name = "id", required = true) @PathVariable(value = "id") String id, @RequestBody @Valid DocumentTypeDTO documentType) {
        documentType.setId(id);
        return documentTypeService.updateDocumentType(documentType);
    }

    @Operation(summary = "delete the documentType", description = "Delete documentType , it take input name documentType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the documentType was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocumentType(@Parameter(name = "id", description = "the documentType id deleted") @PathVariable String id) {
        documentTypeService.deleteDocumentType(id);
    }

    @Operation(summary = "Read the documentType", description = "This endpoint is used to read documentType  it take input name documentType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentTypeDTO readDocumentType(@Parameter(name = "id", description = "the documentType id to read") @PathVariable String id) {
        return documentTypeService.readDocumentType(id);
    }

    @Operation(summary = "Read all documentTypes", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<DocumentTypeDTO> readByFilters(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list documentType ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "label", description = "value of label used to filter list documentType ") @RequestParam(value = "label", required = false) String label,
            @Parameter(name = "metaDataTypeList", description = "value of list metaDataType used to filter list documentType ") @RequestParam(value = "metaDataTypeList", required = false) List<MetaDataType> metaDataTypeList,
            @Parameter(name = "metaDataLabelList", description = "value of list metaDataLabel used to filter list documentType ") @RequestParam(value = "metaDataLabelList", required = false)  List<String> metaDataLabelList
    ) {
        return documentTypeService.readByFilters(code, label, metaDataTypeList, metaDataLabelList, pageable);

    }
}
