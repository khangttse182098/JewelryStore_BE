package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.WarrantyDTO;
import com.swp.jewelrystore.service.PDFGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warranty")
public class WarrantyAPI {
     private final PDFGeneratorService pdfGeneratorService;

     @PostMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response, @RequestBody WarrantyDTO warrantyDTO) {
         response.setContentType("application/pdf");
         DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         String currentDateTime = dateFormatter.format(new Date());

         String headerKey = "Content-Disposition";
         String headerValue = "attachment; filename=pdf_" + warrantyDTO.getCustomerName() + "_" +currentDateTime + ".pdf";
         response.setHeader(headerKey, headerValue);
         try {
             pdfGeneratorService.exportPDFFile(response, warrantyDTO);
         } catch (Exception e){
             e.printStackTrace();
         }
     }
}
