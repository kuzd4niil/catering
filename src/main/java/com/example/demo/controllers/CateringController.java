package com.example.demo.controllers;

import com.example.demo.models.Catering;
import com.example.demo.services.CateringService;
import com.example.demo.services.FileUploaderService;
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

    @GetMapping("all")
    public ResponseEntity<List<Catering>> getAll() {
        List<Catering> cateringList = cateringService.getAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = new ResponseEntity(cateringList, responseHeaders, HttpStatus.OK);
        return responseEntity;
    }

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

    @GetMapping("{id}")
    public Catering getCatering(@PathVariable(value = "id") Long id) {
        Catering catering = cateringService.getCatering(id);
        return catering;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_CATERING')")
    public void addCatering(@RequestBody Catering catering, Principal principal) {
        cateringService.addCatering(catering, principal);
    }

    @PostMapping("addexcel")
    public void addFile(@RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        fileUploaderService.uploadFile(file, principal);
    }

    @PostMapping("{id}/make_an_order")
    public void makeAnOrder(/*здесь что-то должно быть*/) {

    }

    @PutMapping("{id}")
    public Catering updateCatering(@PathVariable(value = "id") Long cateringId, @RequestBody Catering catering, Principal principal) {
        return cateringService.updateCatering(cateringId, catering);
    }

    @DeleteMapping("{id}")
    public void removeCatering(@PathVariable(value = "id") Long id, Principal principal) {
        cateringService.removeCatering(id);
    }
}
