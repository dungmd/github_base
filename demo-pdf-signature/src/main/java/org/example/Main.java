package org.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.C5.C5_01_SignatureIntegrity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.security.Security;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
        String imgScr = "image\\world.jpg";
        ImageData data = ImageDataFactory.create(imgScr);
        Image image1 = new Image(data);
        String path = "D:\\firstPDF.pdf";
        String paraText ="Helo World";
        Paragraph paragraph1 = new Paragraph(paraText);
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument);
        document.add(paragraph1);
        document.add(image1);

        document.close();
        System.out.println("PDF CREATED");
    }
}