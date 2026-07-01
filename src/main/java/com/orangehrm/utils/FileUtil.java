package com.orangehrm.utils;

import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.exceptions.FrameworkException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Small helpers for the upload flows (profile image, resume). Guarantees the
 * sample asset exists so the suite is self-contained on a fresh checkout.
 */
public final class FileUtil {

    private FileUtil() { }

    public static String absoluteUploadPath(String fileName) {
        Path path = Paths.get(FrameworkConstants.UPLOADS_PATH + fileName).toAbsolutePath();
        if (!Files.exists(path)) {
            throw new FrameworkException("Upload asset missing: " + path);
        }
        return path.toString();
    }

    public static void ensureDir(String dir) {
        try {
            Files.createDirectories(Paths.get(dir));
        } catch (IOException e) {
            throw new FrameworkException("Cannot create dir: " + dir, e);
        }
    }
}
