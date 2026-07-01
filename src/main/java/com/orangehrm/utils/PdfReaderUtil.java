package com.orangehrm.utils;

import com.orangehrm.exceptions.FrameworkException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

/**
 * Extracts text from a downloaded PDF for the (bonus) PDF-validation flow -
 * e.g. validating a generated performance/leave report PDF contains expected
 * employee names or totals.
 */
public final class PdfReaderUtil {

    private PdfReaderUtil() { }

    public static String extractText(String absolutePath) {
        try (PDDocument document = Loader.loadPDF(new File(absolutePath))) {
            return new PDFTextStripper().getText(document);
        } catch (Exception e) {
            throw new FrameworkException("Could not read PDF: " + absolutePath, e);
        }
    }

    public static boolean containsText(String absolutePath, String expected) {
        return extractText(absolutePath).contains(expected);
    }
}
