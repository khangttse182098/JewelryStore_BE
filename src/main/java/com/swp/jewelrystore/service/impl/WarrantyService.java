package com.swp.jewelrystore.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.swp.jewelrystore.service.PDFGeneratorService;

import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import org.springframework.stereotype.Service;


@Service
public class WarrantyService implements PDFGeneratorService {

    @Override
    public void exportPDFFile(HttpServletResponse response) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            // choose font for the pdf
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(18);
            // paragraph
            Paragraph paragraph = new Paragraph("Warranty for Product", fontTitle);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
            fontParagraph.setSize(14);

            Paragraph paragraph1 = new Paragraph("This is a paragraph", fontParagraph);
            paragraph1.setAlignment(Element.ALIGN_LEFT);

            document.add(paragraph);
            document.add(paragraph1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // Close the document
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
