package com.maxim.documentfiller.DocumentFilling;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.GroovyDataLoader;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.BandOrientation;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.ReportTemplate;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;
import com.haulmont.yarg.util.groovy.DefaultScriptingImpl;
import com.maxim.documentfiller.DocumentFilling.Exceptions.IncorrectFilePropertiesException;
import com.maxim.documentfiller.DocumentFilling.Exceptions.NotFilledDataException;
import lombok.Builder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Document {

    @Nullable
    private String outputName;

    /**
     * <strong>Must be written in lower case</strong>
     */
    @Nullable
    private String outputType;

    /**
     * <strong>Must include file extension</strong>
     */
    @NonNull
    private String filename;

    private String pathSeparator=".+";

    private StringTree jsonTree = new StringTree();

    private Set<String> bandsName = new HashSet<>();

    private boolean isFilledWithData=false;

    @Builder
    public Document(String filename, String outputType, String outputName, String pathSeparator) {
        this.filename = filename;
        this.outputName = outputName==null?filename:outputName;
        this.outputType = outputType==null?getFileTypeFromName(outputName):outputType;
        this.pathSeparator = pathSeparator==null?"\\.+":pathSeparator;
        jsonTree.setPathSeparator(this.pathSeparator);
    }

    private String getFileTypeFromName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }


    /**
     * Method that inserts data into document
     * <br>
     * <b>Must be invoken</b>
     * @param data
     */
    public void setData(Map<String, String> data) throws IncorrectFilePropertiesException {
        try {
            if (data==null || data.isEmpty()) {
                return;
            }
            isFilledWithData=true;
            for (var entry: data.entrySet()){
                jsonTree.put(entry.getKey().toLowerCase(),entry.getValue());
                bandsName.add(entry.getKey().split(pathSeparator)[0].toLowerCase());
            }
        } catch (IncorrectFilePropertiesException e) {
            e.setFilename(filename);
            throw e;
        }
    }

    /**
     * Builds document and writes it to given stream
     * <br>Terminal operation somehow :)
     * @param writeTo stream to write into
     */
    public void buildDocument(OutputStream writeTo, TemplateDataSource dataSource) throws IOException {
        if (!isFilledWithData) throw new NotFilledDataException();
        ReportBuilder reportBuilder = new ReportBuilder();
        reportBuilder.template(preBuildTemplate(dataSource));

        //Building Bands for data
        String capitalLetterName;
        for (String bandName: bandsName){
            capitalLetterName = new StringBuilder().append(bandName.substring(0,1).toUpperCase()).append(bandName.substring(1)).toString();
            reportBuilder.band(new BandBuilder()
                    .name(capitalLetterName).query(capitalLetterName, "parameter=param1 $.main", "json").orientation(BandOrientation.HORIZONTAL).build());
        }

        Report report = reportBuilder.build();
        //Load json data
        String json = jsonTree.toString();

        System.out.println(json);

        Reporting reporting = preBuildReporting();
        ReportOutputDocument reportOutputDocument = reporting.runReport(new RunParams(report).param("param1",json), writeTo);

    }

    private ReportTemplate preBuildTemplate(TemplateDataSource dataSoruce) throws IOException {
        return new ReportTemplateBuilder()
                .documentName(filename)
                .outputType(ReportOutputType.getOutputTypeById(outputType))
                .documentContent(new FileInputStream(new File("/home/maxim/IdeaProjects/DocumentFiller/MongoDB/templates/test.docx")))
                .documentPath("/home/maxim/IdeaProjects/DocumentFiller/MongoDB/templates/test.docx")
                .build();

    }

    private Reporting preBuildReporting(){
        Reporting reporting = new Reporting();
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory();
        reporting.setFormatterFactory(formatterFactory);
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl()))
                .setJsonDataLoader(new JsonDataLoader()));
        return reporting;
    }

    /**
     * Adds styles for current report
     * @param reportBuilder
     */
    private void fillWithStyles(ReportBuilder reportBuilder){
        // Not done yet :(
    }




}
