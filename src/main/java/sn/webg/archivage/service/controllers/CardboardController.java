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
import sn.webg.archivage.service.models.CardboardDTO;
import sn.webg.archivage.service.services.CardboardService;

import java.util.List;

@RestController
@RequestMapping("/v1/cardboards")
@Tag(name = "carton-controller", description = "Carton controller")
@RequiredArgsConstructor
public class CardboardController
{
    private final CardboardService cardboardService;

    @Operation(summary = "Create cardboard", description = "this endpoint take input cardboard and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the cardboard was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardboardDTO createCardboard(@RequestBody CardboardDTO cardboardDTO) {
        return cardboardService.createCardboard(cardboardDTO);
    }

    @Operation(summary = "Update the Cardboard", description = "this endpoint take input a cardboard and updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the cardboard was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @PutMapping("/{cardboardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardboardDTO updateCardboard(@Parameter(name = "cardboardId", description = "the cardboard id updated") @PathVariable String cardboardId, @RequestBody CardboardDTO cardboardDTO) {
        cardboardDTO.setId(cardboardId);
        return cardboardService.updateCardboard(cardboardDTO);
    }

    @Operation(summary = "Read the cardboard", description = "This endpoint is used to read cardboard  it take input id rayon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{cardboardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardboardDTO readCardboard(@Parameter(name = "cardboardId", description = "the cardboard id to read") @PathVariable String  cardboardId) {
        return cardboardService.readCardboard(cardboardId);
    }

    @Operation(summary = "delete the cardboard", description = "Delete cardboard , it take input   id cardboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the cardboard was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{cardboardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCardboard(@Parameter(name = "cardboardId", description = "the cardboard id deleted") @PathVariable String cardboardId) {
        cardboardService.deleteCardboard(cardboardId);
    }

    @Operation(summary = "Read all rayon", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CardboardDTO> readAllCardboards(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list cardboard ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "label", description = "value of label used to filter list cardboard ") @RequestParam(value = "label", required = false) String label,
            @Parameter(name = "labelshelf", description = "value of label used to filter list cardboard ") @RequestParam(value = "labelshelf", required = false) List<String> labelShelf

            ) {

        return cardboardService.readAllCardboards(pageable, code, label, labelShelf);

    }

}
