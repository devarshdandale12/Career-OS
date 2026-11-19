package com.devarsh.careeros.util;

public class TextUtil {

    private TextUtil() {
    }

    // Trims spaces, and turns empty text into null
    public static String cleanOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}