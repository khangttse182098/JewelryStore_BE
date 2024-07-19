package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.WarrantyDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PDFGeneratorService {
    void exportPDFFile(HttpServletResponse response, WarrantyDTO warrantyDTO) throws IOException;
}
