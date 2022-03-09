package com.example.demo.controllers;

import com.example.demo.models.Catering;
import com.example.demo.models.Reserve;
import com.example.demo.services.CateringService;
import com.example.demo.services.FileUploaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/catering")
public class CateringController {

    private CateringService cateringService;
    private FileUploaderService fileUploaderService;

    CateringController(CateringService cateringService, FileUploaderService fileUploaderService) {
        this.cateringService = cateringService;
        this.fileUploaderService = fileUploaderService;
    }

    @Operation(summary = "Get all caterings without sorting and paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all users",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))})
    })
    @GetMapping("all")
    public ResponseEntity<List<Catering>> getAll() {
        List<Catering> cateringList = cateringService.getAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(cateringList, responseHeaders, HttpStatus.OK);
        return responseEntity;
    }

    @Operation(summary = "get several caterings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get several caterings",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Catering.class))})
    })
    @GetMapping("page/{numberPage}")
    public List<Catering> getCaterings(@PathVariable(name = "numberPage") Integer numberPage,
                                             @RequestParam(name = "pageSize", required = true) Short pageSize,
                                             @RequestParam(name = "sortDirection", required = true) Short sortDirection,
                                             @RequestParam(name = "sortBy", required = true) String sortBy
    ) {
        return cateringService.getCaterings(
            numberPage,
            pageSize,
            sortDirection,
            sortBy
        );
    }

    @Operation(summary = "Get catering by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get one catering",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))}),
            @ApiResponse(responseCode = "404", description = "Catering not found",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<Catering> getCatering(@PathVariable(value = "id") Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(cateringService.getCatering(id), responseHeaders, HttpStatus.OK);
        return responseEntity;
    }

    @Operation(summary = "Add catering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add catering",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))}),
            @ApiResponse(responseCode = "400", description = "Catering already exist",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))})
    })
    @PostMapping
    public ResponseEntity<Catering> addCatering(@RequestBody Catering catering, Principal principal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(cateringService.addCatering(catering, principal), responseHeaders, HttpStatus.OK);
        return responseEntity;
    }

    @Operation(summary = "Add caterings by Excel file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add Excel file with list of caterings",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))})
    })
    @PostMapping("addexcel")
    public ResponseEntity<List<Catering>> addFile(@RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<Catering>> responseEntity = new ResponseEntity(fileUploaderService.uploadFile(file, principal), responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }

    @Operation(summary = "Do reserve in a catering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Make order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reserve.class))})
    })
    @PostMapping("{id}/reserve")
    public ResponseEntity<Reserve> reserve(@PathVariable(value = "id") Long cateringId, Principal principal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Reserve> responseEntity = new ResponseEntity(cateringService.reserve(cateringId, principal), responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }

    @Operation(summary = "Update catering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Editing catering",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Catering.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<Catering> updateCatering(@PathVariable(value = "id") Long cateringId, @RequestBody Catering catering, Principal principal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Catering> responseEntity = new ResponseEntity(cateringService.updateCatering(cateringId, catering), responseHeaders, HttpStatus.ACCEPTED);
        return responseEntity;
    }

    @Operation(summary = "Delete catering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Remove catering")
    })
    @DeleteMapping("{id}")
    public ResponseEntity removeCatering(@PathVariable(value = "id") Long id, Principal principal) {
        cateringService.removeCatering(id);
        return ResponseEntity.accepted().build();
    }
}
