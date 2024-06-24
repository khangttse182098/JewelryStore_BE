package com.swp.jewelrystore.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PDFGeneratorService {
    void exportPDFFile(HttpServletResponse response) throws IOException;
}
