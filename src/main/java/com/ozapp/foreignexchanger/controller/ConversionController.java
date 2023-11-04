package com.ozapp.foreignexchanger.controller;

import com.ozapp.foreignexchanger.data.dto.ConversionDto;
import com.ozapp.foreignexchanger.data.exception.ValidationException;
import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListRequestModel;
import com.ozapp.foreignexchanger.service.ConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversion")
@CrossOrigin(origins = "*", allowedHeaders = {"*"})
@Tag(name = "Conversion API", description = "Conversion Management APIs")
public class ConversionController {
    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Operation(summary = "Conversion API.",
               description = "Conversion is done between two currencies. Total amount in target currency and 'transactionId' is returned.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Conversion list successfully listed",
                                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConversionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied.", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConversion(@RequestParam("sourceAmount") Double sourceAmount, @RequestParam("sourceCurrency") String sourceCurrency,
                                           @RequestParam("targetCurrency") String targetCurrency) {
        try {
            ConversionDto conversionDto = conversionService.convertCurrency(sourceAmount, sourceCurrency, targetCurrency);
            return ResponseEntity.ok(conversionDto);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Conversion List API.",
               description = "According to the date or transaction id search criteria, conversion archive is listed.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Conversion list successfully listed",
                                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied.", content = @Content)})
    @PostMapping(value = "/list/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConversionListArchive(@RequestBody ConversionListRequestModel conversionListRequestModel,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        try {
            Page<ConversionDto> conversionListArchivePage = conversionService.getConvertedCurrencyList(conversionListRequestModel, page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("conversionList", conversionListArchivePage.getContent());
            response.put("currentPage", conversionListArchivePage.getNumber());
            response.put("totalItems", conversionListArchivePage.getTotalElements());
            response.put("totalPages", conversionListArchivePage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
