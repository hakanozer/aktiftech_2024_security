package com.works.services;

import com.google.crypto.tink.subtle.AesGcmJce;
import com.google.crypto.tink.subtle.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Service
public class TinkEncDec {

    @Value("${tink.key128Bit}")
    private String key128Bit;

    @Value("${tink.extraKey}")
    private String extraKey;

    public String generateKey() {
        try {
            // n -> 128, 192, 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        }catch (Exception ex) {
            return "";
        }
    }

    public String encrypt( String plainText ) {
        String cipherText = "";
        try {
            AesGcmJce aesGcmJce = new AesGcmJce(key128Bit.getBytes());
            byte[] byteEncrypt = aesGcmJce.encrypt(plainText.getBytes(), extraKey.getBytes());
            cipherText = new String(byteEncrypt, "ISO-8859-1");
        } catch (Exception e) {
            System.err.println("Encrypt Error :" + e);
        }
        cipherText = Base64.encode(cipherText.getBytes());
        return cipherText;
    }

    public String decrypt( String cipherText ) {
        String planText = "";
        try {
            cipherText = new String( Base64.decode(cipherText) );
            byte[] convertEncode = cipherText.getBytes("ISO-8859-1");
            AesGcmJce aesGcmJce = new AesGcmJce(key128Bit.getBytes());
            byte[] descBytes = aesGcmJce.decrypt(convertEncode, extraKey.getBytes());
            planText = new String(descBytes);
        } catch (Exception e) {
            System.err.println("decrypt Error : " + e);
        }
        return planText;
    }


}