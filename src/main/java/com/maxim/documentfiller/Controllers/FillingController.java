package com.maxim.documentfiller.Controllers;

import com.maxim.documentfiller.DocumentFilling.Document;
import com.maxim.documentfiller.DocumentFilling.Exceptions.IncorrectFilePropertiesException;
import com.maxim.documentfiller.DocumentFilling.Exceptions.NotFilledDataException;
import com.maxim.documentfiller.DocumentFilling.FileAttributesDataSource;
import com.maxim.documentfiller.DocumentFilling.TemplateDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController()
@RequestMapping("/document-filling")
public class FillingController {

    /**
     * <strong>For future use</strong>
     */
    @Autowired
    private FileAttributesDataSource fileAttributesDataSource;

    @Autowired
    private TemplateDataSource dataSource;

    @Operation(summary = "fill template with data and get it", description = "Fill template with data with given properties and get it filled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully filled file", content = @Content(schema = @Schema(implementation = Document.class))),
            @ApiResponse(responseCode = "400", description = "Error due to incorrect filling")
    })
    @PostMapping("/{filename}")
    @ResponseStatus(HttpStatus.OK)
    public  void fillTemplate(
            @Parameter(name = "filename", description = "template filename") @PathVariable("filename")String fileName,
            @Schema(name = "File properties", description = "Filled file properties (e.g Main.name:Max") @RequestBody Map<String,String> properties,
            HttpServletResponse response, @Parameter(name = "Output filename") @RequestParam(name = "output-filename") String outputFilename) throws IOException {
        var doc = Document.builder().filename(fileName).outputName(outputFilename).outputType("pdf").build();
        doc.setData(properties);
        doc.buildDocument(response.getOutputStream(), dataSource);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectFilePropertiesException.class)
    public ResponseEntity<String> handleIncorrectFileProperties(IncorrectFilePropertiesException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity.internalServerError().body(String.format("Incorrect template of %s",ex.getFilename()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFilledDataException.class)
    public ResponseEntity<String> handleNotFilledDataException(NotFilledDataException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity.badRequest().body("No data to fill template was found: "+ex.getMessage());
    }



}
