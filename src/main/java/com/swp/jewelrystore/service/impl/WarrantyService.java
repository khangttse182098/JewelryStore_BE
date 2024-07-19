package com.swp.jewelrystore.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.WarrantyEntity;
import com.swp.jewelrystore.model.dto.WarrantyDTO;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.repository.WarrantyRepository;
import com.swp.jewelrystore.service.PDFGeneratorService;

import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.io.FileOutputStream;



@Service
@RequiredArgsConstructor
public class WarrantyService implements PDFGeneratorService {

   private final ProductRepository productRepository;
   private final WarrantyRepository warrantyRepository;

    @Override
    public void exportPDFFile(HttpServletResponse response, WarrantyDTO warrantyDTO) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            InputStream fontStream = getClass().getResourceAsStream("/font/RobotoSlab-Light.ttf");
            if (fontStream == null) {
                throw new RuntimeException("Font file not found");
            }
            // Create a temporary file to store the font
            File tempFontFile = File.createTempFile("RobotoSlab-Light", ".ttf");
            try (FileOutputStream fos = new FileOutputStream(tempFontFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fontStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            // Create the base font using the temporary file
            BaseFont baseFont = BaseFont.createFont(tempFontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTitle = new Font(baseFont, 26, Font.BOLD);
            Font fontParagraph = new Font(baseFont, 15);
            Font fontBold = new Font(baseFont, 17, Font.BOLD);

            // image

            // Load and add the image from the resources folder
            InputStream imageStream = getClass().getResourceAsStream("/image/logo-mahika.jpg");
            if (imageStream == null) {
                throw new RuntimeException("Image file not found");
            }
            byte[] imageBytes = toByteArray(imageStream);
            Image image = Image.getInstance(imageBytes);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            // title
            Paragraph title = new Paragraph("PHIẾU BẢO HÀNH", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);

            // customer Name
            Paragraph customerName = new Paragraph("Họ tên khách hàng:     " + warrantyDTO.getCustomerName(), fontParagraph);
            customerName.setAlignment(Element.ALIGN_LEFT);

            // phoneNumber
            Paragraph phoneNumber = new Paragraph("Số điện thoại:      " + warrantyDTO.getPhoneNumber(), fontParagraph);
            phoneNumber.setAlignment(Element.ALIGN_LEFT);


            // buy date
            ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            ZonedDateTime vietnamTime = ZonedDateTime.now(vietnamZoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = vietnamTime.format(formatter);
            Paragraph buyDate = new Paragraph("Ngày mua:           " + formattedDateTime, fontParagraph);
            buyDate.setAlignment(Element.ALIGN_LEFT);

            // out date
            ZonedDateTime sixMonthsLater = vietnamTime.plusMonths(6);
            String formattedSixMonthsLater = sixMonthsLater.format(formatter);
            Paragraph outDate = new Paragraph("Ngày hết hạn:       " + formattedSixMonthsLater, fontParagraph);
            outDate.setAlignment(Element.ALIGN_LEFT);

            // Product information
            Paragraph productTitle = new Paragraph("Thông tin sản phẩm bảo hành", fontBold);
            productTitle.setAlignment(Element.ALIGN_CENTER);
            productTitle.setSpacingBefore(10);

            // Table
            float[] columnWidth = {100f,100f,200f,500f};
            PdfPTable table = new PdfPTable(columnWidth);
            table.setSpacingBefore(30);
            List<ProductEntity> listProduct = productRepository.findAllByIdIn(warrantyDTO.getListProductId());
            int index = 1;
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("STT", fontParagraph));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Mã bảo hành", fontParagraph));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Tên sản phẩm", fontParagraph));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Mô tả", fontParagraph));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            for (ProductEntity item: listProduct){
                cell = new PdfPCell(new Phrase(String.valueOf(index), fontParagraph));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                WarrantyEntity warranty = warrantyRepository.findByProduct(item);
                cell = new PdfPCell(new Phrase(warranty.getWarrantyNumber(), fontParagraph));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(item.getProductName(), fontParagraph));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(warranty.getDescription(), fontParagraph));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
                index++;
            }
            table.setSpacingAfter(30);
            // note
            Paragraph note = new Paragraph("TRƯỜNG HỢP KHÔNG ĐƯỢC BẢO HÀNH: ", fontBold);
            note.setAlignment(10);

            // note-content
            Paragraph note_content = new Paragraph("  * Quá thời gian bảo hành, sản phẩm có bị tác động từ bên ngoài (tác động của con người: làm vỡ, hỏng,...)", fontParagraph);
            note.setAlignment(30);

            // signature
            Paragraph signature = new Paragraph("Kí tên quầy", fontParagraph);
            signature.setAlignment(Element.ALIGN_RIGHT);

            document.add(title);
            document.add(customerName);
            document.add(phoneNumber);
            document.add(buyDate);
            document.add(outDate);
            document.add(productTitle);
            document.add(table);
            document.add(note);
            document.add(note_content);
            document.add(signature);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // Close the document
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
