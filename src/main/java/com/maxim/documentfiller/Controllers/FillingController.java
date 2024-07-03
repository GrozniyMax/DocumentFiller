package com.maxim.documentfiller;

import com.maxim.documentfiller.DB.templates.Properties.FilePropertiesRepository;
import com.maxim.documentfiller.DocumentFilling.Document;
import com.maxim.documentfiller.DocumentFilling.Exceptions.IncorrectFilePropertiesException;
import com.maxim.documentfiller.DocumentFilling.Exceptions.NotFilledDataException;
import com.maxim.documentfiller.DocumentFilling.FileAttributesDataSource;
import com.maxim.documentfiller.DocumentFilling.TemplateDataSource;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController()
@RequestMapping("/document")
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    /**
     * <strong>For future use</strong>
     */
    @Autowired
    private FileAttributesDataSource fileAttributesDataSource;

    @Autowired
    private TemplateDataSource dataSource;

    @PostMapping("/{filename}")
    public  void putNewFile(@PathVariable("filename")String fileName, @RequestBody Map<String,String> properties,
                            HttpServletResponse response,@RequestParam(name = "output-filename") String outputFilename) throws IOException {
        var doc = Document.builder().filename(fileName).outputName(outputFilename).outputType("pdf").build();
        doc.setData(properties);
        doc.buildDocument(response.getOutputStream(), dataSource);
    }

    @ExceptionHandler(IncorrectFilePropertiesException.class)
    public ResponseEntity<String> handleIncorrectFileProperties(IncorrectFilePropertiesException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity.internalServerError().body(String.format("Incorrect template of %s",ex.getFilename()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(NotFilledDataException.class)
    public ResponseEntity<String> handleNotFilledDataException(NotFilledDataException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity.badRequest().body("No data to fill template was found");
    }



}
