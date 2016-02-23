/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.*;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author alice
 */
public class CryptoTests {
    
    private Cipher AESCipher;
    private Key AESKey;
    
    public CryptoTests() throws NoSuchAlgorithmException, NoSuchProviderException, 
            NoSuchPaddingException
    {
        // Create cipher object for AES with CBC
        AESCipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        // Generate 128-bit AES key
        // Get a KeyGenerator instance for AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        // Initialize the KeyGenerator for 128bit key size
        keyGen.init(128);
        // Generate a key
        AESKey = keyGen.generateKey();
    }
    
    public void encryptdecrypt (String originalPlaintext, String algorithm) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        switch (algorithm.toUpperCase()) {
            case "AES":
                byte[] cipherBytes = encryptAES(originalPlaintext);
                System.out.println("The enciphered bytes are: " + new String(cipherBytes, "ISO-8859-1"));
//                String decryptedPlaintext = decryptAES(cipherBytes);
//                System.out.println("Your plaintext was: " + decryptedPlaintext);
                break;
            case "DES":
                // Call DES functions
                break;
            default:
                System.out.println("Please use a valid algorithm ID");
                break;
        }
    }
    
    private byte[] encryptAES(String plaintext) throws InvalidKeyException, 
            ShortBufferException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        System.out.println("Encrypting using AES...");
 
        // Convert plaintext string to byte array
        byte[] plainBytes = plaintext.getBytes("ISO-8859-1");
        
        // Initialize the Cipher object to encrypt
        AESCipher.init(Cipher.ENCRYPT_MODE, AESKey);
        // Allocate a byte array for the ciphertext
        byte[] cipherBytes = new byte[AESCipher.getOutputSize(plainBytes.length)];
        
        // Use update to encrypt
        // input: plainBytes, input length: plainBytes.length, input offset: 0 
        // output to: cipherBytes, output offset: 0
        int cipherLength = AESCipher.update(plainBytes, plainBytes.length, 0, cipherBytes, 0);
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: cipherBytes, output offset: cipherLength
        cipherLength += AESCipher.doFinal(cipherBytes, cipherLength);
        
        // Return the ciphertext bytes
        return cipherBytes;
    }
    
    private String decryptAES(byte[] ciphertext) throws InvalidKeyException, 
            ShortBufferException, IllegalBlockSizeException, BadPaddingException
    {
        System.out.println("Decrypting using AES...");
        
        // Initialize the Cipher object to decrypt
        AESCipher.init(Cipher.DECRYPT_MODE, AESKey);
        // Allocate a byte array for the plaintext
        byte[] plainBytes = new byte[AESCipher.getOutputSize(ciphertext.length)];
        
        // Use update to decrypt
        // input: ciphertext, input length: ciphertext.length, input offset: 0
        // output to: plainBytes, output offset: 0
        int plainLength = AESCipher.update(ciphertext, ciphertext.length, 0, plainBytes, 0);
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: plainBytes, output offset: plainLength
        plainLength += AESCipher.doFinal(plainBytes, plainLength);
        
        // Return plaintext string from plaintext bytes
        return new String(plainBytes);
    }
    
}
