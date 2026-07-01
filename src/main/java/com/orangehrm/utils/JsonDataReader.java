package com.orangehrm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.exceptions.FrameworkException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Thin Jackson wrapper for the data-driven layer. Test data lives as JSON under
 * /testdata and is deserialised either into a Map or a typed POJO list.
 */
public final class JsonDataReader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonDataReader() { }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> readAsMap(String fileName) {
        try {
            return MAPPER.readValue(file(fileName), Map.class);
        } catch (IOException e) {
            throw new FrameworkException("Failed reading JSON: " + fileName, e);
        }
    }

    public static <T> List<T> readAsList(String fileName, Class<T> type) {
        try {
            return MAPPER.readValue(file(fileName),
                    MAPPER.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new FrameworkException("Failed reading JSON list: " + fileName, e);
        }
    }

    private static File file(String fileName) {
        return new File(FrameworkConstants.TEST_DATA_PATH + fileName);
    }
}
