package com.maxim.documentfiller;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.haulmont.yarg.loaders.impl.GroovyDataLoader;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.structure.BandOrientation;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;
import com.haulmont.yarg.structure.xml.impl.DefaultXmlReader;
import com.haulmont.yarg.util.groovy.DefaultScriptingImpl;

import com.maxim.documentfiller.DocumentFilling.Document;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;

@RestController
public class DocumentController {

    @RequestMapping(path = "/generate/doc", method = RequestMethod.GET)
    public void generateDocument() throws IOException {
        ReportBuilder reportBuilder = new ReportBuilder();
        reportBuilder.template(new ReportTemplateBuilder().documentContent(new FileInputStream(new File("/home/maxim/IdeaProjects/FuckingDocumentForming/TestTest/test.docx")))
                .outputType(ReportOutputType.pdf)
                .documentPath("dfghjk")
                .documentName("test.docx").build());


        reportBuilder.band(new BandBuilder().name("Main").query("Main", "parameter=param1 $.main", "json").orientation(BandOrientation.HORIZONTAL).build());


        Report report = reportBuilder.build();
        Reporting reporting = new Reporting();
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory();
        reporting.setFormatterFactory(formatterFactory);
        reporting.setLoaderFactory(new DefaultLoaderFactory()
                .setGroovyDataLoader(new GroovyDataLoader(new DefaultScriptingImpl()))
                .setJsonDataLoader(new JsonDataLoader()));

        String json = FileUtils.readFileToString(new File("/home/maxim/IdeaProjects/FuckingDocumentForming/TestTest/tets.json"));
        ReportOutputDocument reportOutputDocument = reporting.runReport(new RunParams(report).param("param1", json),
                new FileOutputStream("/home/maxim/IdeaProjects/DocumentFiller/result.pdf"));
    }

}