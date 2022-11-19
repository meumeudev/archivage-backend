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
import sn.webg.archivage.service.models.ShelfDTO;
import sn.webg.archivage.service.services.ShelfService;



@RestController
@RequestMapping("/v1/shelfs")
@Tag(name = "rayon-controller", description = "Rayon controller")
@RequiredArgsConstructor
public class ShelfController {
    private final ShelfService shelfService;

    @Operation(summary = "Create shel", description = "this endpoint take input shelf and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShelfDTO createShelf(@RequestBody ShelfDTO shelf) {
        return shelfService.createShelf(shelf);
    }

    @Operation(summary = "Update the shelf", description = "this endpoint take input a shelf and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{shelfId}")
    @ResponseStatus(HttpStatus.OK)
    public ShelfDTO updateShelf(@Parameter(name = "shelfId", description = "the shelf id updated") @PathVariable String shelfId, @RequestBody ShelfDTO shelf) {
        shelf.setId(shelfId);
        return shelfService.updateShelf(shelf);
    }

    @Operation(summary = "Read the shelf", description = "This endpoint is used to read shelf  it take input id rayon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{shelfId}")
    @ResponseStatus(HttpStatus.OK)
    public ShelfDTO readShelf(@Parameter(name = "shelfId", description = "the shelf id to read") @PathVariable String shelfId) {
        return shelfService.readShelf(shelfId);
    }

    @Operation(summary = "delete the shelf", description = "Delete shelf , it take input   id shelf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{shelfId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShelf(@Parameter(name = "shelfId", description = "the shelf id deleted") @PathVariable String shelfId) {
        shelfService.deleteShelf(shelfId);
    }

    @Operation(summary = "Read all rayon", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ShelfDTO> readAllShelfs(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list shelf ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "label", description = "value of label used to filter list shelf ") @RequestParam(value = "label", required = false) String label,
            @Parameter(description = "shelf activated") @RequestParam(value = "activate", required = false) Boolean activate
    ) {

        return shelfService.readAllShelfs(pageable, code, label, activate);

    }

}
