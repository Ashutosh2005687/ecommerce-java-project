package com.ecommerce.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    // Simple SHA-256 hash helper for demo purposes. In production use a proper password hashing algorithm (bcrypt/argon2).
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
