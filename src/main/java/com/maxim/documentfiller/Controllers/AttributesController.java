package com.maxim.documentfiller.Controllers;

import com.maxim.documentfiller.MongoDB.Exceptions.NoSuchFileAttributesException;
import com.maxim.documentfiller.MongoDB.templates.Properties.FileAttributes;
import com.maxim.documentfiller.MongoDB.templates.Properties.FilePropertiesRepository;
import com.maxim.documentfiller.DTOs.ConverterToDTO;
import com.maxim.documentfiller.DTOs.DTOs.FileAttributesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attributes")
public class AttributesController {

    @Autowired
    private FilePropertiesRepository repository;

    @Autowired
    private ConverterToDTO<FileAttributes, FileAttributesDTO> converterToDTO;


    @Operation(summary = "get all file attributes by filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfuly found such attributes",content = @Content(schema = @Schema(implementation = FileAttributesDTO.class))),
            @ApiResponse(responseCode = "404",description = "Not found File Attributes with such filename",content = @Content(schema = @Schema(implementation = FileAttributesDTO.class)))
    })

    @GetMapping("/{filename}/get")
    @ResponseStatus(HttpStatus.OK)
    public FileAttributesDTO attributes(@Parameter(description = "filename exactly") @PathVariable String filename) throws NoSuchFileAttributesException {
        return converterToDTO.toDTO(
                repository.findByFilename(filename).orElseThrow(
                        ()->new NoSuchFileAttributesException(filename)
                ));
    }

    @ExceptionHandler(NoSuchFileAttributesException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchFileAttributesException(NoSuchFileAttributesException ex) {
        return ex.getMessage();
    }

    //TODO write all roots maybe later
}
