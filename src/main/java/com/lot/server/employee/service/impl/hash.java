package com.lot.server.employee.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class hash {
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }
    }
}
