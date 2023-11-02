package com.ozapp.foreignexchanger.controller;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/conversion")
@CrossOrigin(origins = "*", allowedHeaders = {"*"})
public class ConversionController {
    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping(value = "/list/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetectionArchive(@RequestBody ConversionListRequestModel conversionListRequestModel,
                                                 @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            // validate(conversionListRequestModel.getDetectionCriteria());
            Page<ConversionDto> detectionPage = conversionService.getConversionList(conversionListRequestModel, page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("conversionList", detectionPage.getContent());
            response.put("currentPage", detectionPage.getNumber());
            response.put("totalItems", detectionPage.getTotalElements());
            response.put("totalPages", detectionPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } /*catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } */ catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
