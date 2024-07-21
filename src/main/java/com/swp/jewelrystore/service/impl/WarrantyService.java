package com.swp.jewelrystore.service.impl;


import ch.qos.logback.classic.pattern.DateConverter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import com.swp.jewelrystore.converter.DateTimeConverter;
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


import java.awt.*;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;



@Service
@RequiredArgsConstructor
public class WarrantyService implements PDFGeneratorService {

   private final ProductRepository productRepository;
   private final WarrantyRepository warrantyRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public void exportPDFFile(HttpServletResponse response, WarrantyDTO warrantyDTO) {

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
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

            image.setAbsolutePosition(0,100);
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            PdfContentByte canvas = writer.getDirectContentUnder();
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.2f); // Set the opacity here (0.0f to 1.0f)
            canvas.setGState(gState);
            canvas.addImage(image);


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
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date startDate = inputFormat.parse(formattedDateTime);

                    // out date
            ZonedDateTime sixMonthsLater = vietnamTime.plusMonths(6);
            String formattedSixMonthsLater = sixMonthsLater.format(formatter);
            Paragraph outDate = new Paragraph("Ngày hết hạn:       " + formattedSixMonthsLater, fontParagraph);
            outDate.setAlignment(Element.ALIGN_LEFT);
            Date endDate = inputFormat.parse(formattedSixMonthsLater);

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
                WarrantyEntity warranty =new WarrantyEntity();
                warranty.setWarrantyNumber(generateRandomCode(7));
                warranty.setStartDate(startDate);
                warranty.setEndDate(endDate);
                warranty.setProduct(item);
                warranty.setDescription("Phiếu bảo hành 6 tháng cho " + item.getProductName() + ", cam kết sửa chữa miễn phí lỗi kỹ thuật và cung cấp dịch vụ vệ sinh, bảo dưỡng định kỳ");
                if(new Date().after(endDate)){
                    warranty.setStatus("Hết hạn");
                }else{
                    warranty.setStatus("Đang hiệu lực");
                }
                warranty.setCreatedDate(new Date());
                warrantyRepository.save(warranty);
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
            Paragraph signature = new Paragraph("Xác nhận bởi", fontParagraph);
            signature.setSpacingBefore(25);
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

            PdfContentByte canvasUnder = writer.getDirectContentUnder();
            canvasUnder.setRGBColorFill(0, 0, 0);
            canvasUnder.rectangle(0, 0, PageSize.A4.getWidth(), 100);
            canvasUnder.fill();


            PdfContentByte canvasOver = writer.getDirectContent();
            canvasOver.setRGBColorFill(255, 255, 255); // Set color fill to white for text
            Font whiteFont = new Font(baseFont, 14, Font.NORMAL, Color.WHITE);
            Phrase company = new Phrase("Công ty TNHH Mahika Store", whiteFont);
            Phrase contact = new Phrase("Liên hệ:  68686868", whiteFont);
            Phrase address = new Phrase("Địa chỉ:  Xã Tự Cường, Huyện Tự Lực, Tỉnh Kiên Giang", whiteFont);
            ColumnText.showTextAligned(canvasOver, Element.ALIGN_CENTER, company, PageSize.A4.getWidth() / 2, 70, 0);
            ColumnText.showTextAligned(canvasOver, Element.ALIGN_CENTER, contact, PageSize.A4.getWidth() / 2, 45, 0);
            ColumnText.showTextAligned(canvasOver, Element.ALIGN_CENTER, address, PageSize.A4.getWidth() / 2, 20, 0);


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
    public static String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
