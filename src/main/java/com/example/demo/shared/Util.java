package com.example.demo.shared;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;        // SHA-256 kommer från detta paket
import java.security.NoSuchAlgorithmException;  // skulle den inte hitta paketet behövs denna

@Component
public class Util {
    public String generatedHash(String uniqueProperty) {
        try {
            MessageDigest hashFunc = MessageDigest.getInstance("SHA3-256");
            byte[] digest = hashFunc.digest(uniqueProperty.getBytes());
            return digest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
