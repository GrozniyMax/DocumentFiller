package com.maxim.documentfiller.Controllers;


import com.maxim.documentfiller.MongoDB.Exceptions.SuchFileExistsExpection;
import com.maxim.documentfiller.DocumentFilling.TemplateDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.maxim.documentfiller.MongoDB.Exceptions.NoSuchFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/templates-file")
public class TemplatesController {

    @Autowired
    private TemplateDataSource fileDataSource;

    @Operation(summary = "Get template file", description = "Load template  file by filename as a Stream")
    @ApiResponses()
    @GetMapping("/{filename}/get")
    @ResponseStatus(HttpStatus.OK)
    public void getTemplateFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        fileDataSource.loadFile(filename).transferTo(response.getOutputStream());
    }

    @Operation(summary = "Put template file into database", description = "Put template and automatically replace template with the same name if there is any")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "File successfully added to database"),
            @ApiResponse(responseCode = "500", description = "Error occurs with I/O"),

    })
    @PutMapping("/{filename}/replace")
    @ResponseStatus(HttpStatus.OK)
    public void putTemplateFile(@PathVariable String filename, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        fileDataSource.putFile(filename, file.getInputStream(), getMetadataFromRequestHeaders(request));
    }

    @Operation(summary = "Add new template file into database", description = "Put file if there is no template with this name. File is transferred like Multipart")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "File successfully added to database"),
            @ApiResponse(responseCode = "500", description = "Error occurs with I/O"),
            @ApiResponse(responseCode = "400",description = "Such file already exists")

    })
    @PostMapping("/{filename}/add")
    @ResponseStatus(HttpStatus.OK)
    public void addTemplateFile(@PathVariable String filename, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        fileDataSource.addFile(filename, file.getInputStream(), getMetadataFromRequestHeaders(request));
    }

    private Map<String, Object> getMetadataFromRequestHeaders(HttpServletRequest request) {
        Map<String, Object> metadata = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining((headerName)->{
            if (headerName.startsWith("_metadata_")){
                metadata.put(
                        headerName.replaceFirst("_metadata_",""),
                        request.getHeader(headerName)
                );

            }
        });
        return metadata;
    }

    @ExceptionHandler(NoSuchFileException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFileNotFound(NoSuchFileException ex){
        return String.format("File %s not found",ex.getMessage());
    }

    @ExceptionHandler(SuchFileExistsExpection.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleSuchFileExistsException(SuchFileExistsExpection ex){
        return ResponseEntity.badRequest().body("Such file already exists"+ex.getMessage());
    }

}
