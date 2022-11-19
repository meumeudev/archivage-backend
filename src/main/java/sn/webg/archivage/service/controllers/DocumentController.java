package sn.webg.archivage.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sn.webg.archivage.service.models.DocumentDTO;
import sn.webg.archivage.service.models.DownloadFile;
import sn.webg.archivage.service.services.DocumentService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/documents")
@Tag(name = "document-controller", description = "Document controller")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create document", description = "this endpoint take input document and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the document was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentDTO createDocument(@RequestBody @Valid DocumentDTO document) {
        return documentService.createDocument(document);
    }

    @Operation(
            summary = "Add file to document",
            description = "this endpoint is used to upload a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping(value = "/{id}/_upload", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public DocumentDTO addFile(
            @Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") String id,
            @Parameter(description = "The  file to document", required = true) @RequestPart("file") MultipartFile file) {

        /* Uploading tariff versioned */
        return documentService.addFile(id, file);
    }

    @Operation(
            summary = "Download file document",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") String id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = documentService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }


    @Operation(summary = "Update the document", description = "this endpoint take input a document and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the document was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentDTO updateDocument(@Parameter(description = "Document identifier", name = "id", required = true) @PathVariable(value = "id") String id, @RequestBody @Valid DocumentDTO document) {
        document.setId(id);
        return documentService.updateDocument(document);
    }

    @Operation(summary = "delete the document", description = "Delete document , it take input name document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the document was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@Parameter(name = "id", description = "the document id deleted") @PathVariable String id) {
        documentService.deleteDocument(id);
    }

    @Operation(summary = "Read the document", description = "This endpoint is used to read document  it take input name document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentDTO readDocument(@Parameter(name = "id", description = "the document id to read") @PathVariable String id) {
        return documentService.readByIdFilters(id, null, null, false, null, null);
    }

    @Operation(summary = "Read all documents", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<DocumentDTO> readByFilters(
            Pageable pageable,
            @Parameter( description = "value of map metaData key/value used to filter list document ") @RequestParam(required = false) Map<String, Object> metaDataList,
            @Parameter( description = "value of name used to filter list document ") @RequestParam(value = "name", required = false) String name,
            @Parameter( description = "value of reference used to filter list document ") @RequestParam(value = "reference", required = false) String reference,
            @Parameter( description = "value of accessMetaData used to filter list document ") @RequestParam(value = "accessMetaData", required = false) Set<String> accessMetaData,
            @Parameter( description = "value of identifier documentTypes used to filter list document ") @RequestParam(value = "documentTypeIds", required = false) Set<String> idDocumentTypes,
            @Parameter( description = "value of identifier documents used to filter list document ") @RequestParam(value = "documentIds", required = false) Set<String> idDocuments,
            @Parameter( description = "value of identifier folders used to filter list document ") @RequestParam(value = "folderIds", required = false) Set<String> idFolders
        ) {
        return documentService.readByFilters(accessMetaData, name, reference, idDocumentTypes, false, idDocuments, idFolders, pageable, metaDataList);

    }
}
