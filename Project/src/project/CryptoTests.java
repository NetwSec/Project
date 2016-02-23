/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author alice
 */
public class CryptoTests {

    private final Cipher AESCipher;
    private final Cipher DESCipher;
    private final Key DESKey;
    private final Key AESKey;
    final private  IvParameterSpec AESiv;  //holds the initialization vector(IV)
    final private  IvParameterSpec DESiv;  //holds the initialization vector(IV)

    public CryptoTests() throws Exception
    {
        //AES
        // Create cipher object for AES with CBC
        AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");    
        AESiv = new IvParameterSpec(generateIV());
        // Generate a key
        AESKey = generateKey();
        
        //DES
        // Create cipher object for DES with CBC
        DESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");    
        DESiv = new IvParameterSpec(generateIV());
        // Generate a key
        DESKey = generateKey();
    }

    public void encryptdecrypt (String originalPlaintext, String algorithm) throws Exception
    {
        
        byte[] cipherBytes;
        String decryptedPlaintext;
        
        switch (algorithm.toUpperCase()) {
            case "AES":
                cipherBytes = encryptAES(originalPlaintext);
                System.out.println("The enciphered bytes are: " + cipherBytes);
                decryptedPlaintext = decryptAES(cipherBytes);
                System.out.println("Your plaintext was: " + decryptedPlaintext);
                break;
            case "DES":
                // Call DES functions
                cipherBytes = encryptDES(originalPlaintext);
                System.out.println("The enciphered bytes are: " + cipherBytes);
                decryptedPlaintext = decryptDES(cipherBytes);
                System.out.println("Your plaintext was: " + decryptedPlaintext);
                break;
            default:
                System.out.println("Please use a valid algorithm ID");
                break;
        }
    }

    private byte[] encryptAES(String plaintext) throws Exception
    {
        System.out.println("Encrypting using AES...");

        // Convert plaintext string to byte array
        byte[] plainBytes = plaintext.getBytes();

        // Initialize the Cipher object to encrypt
        AESCipher.init(Cipher.ENCRYPT_MODE, AESKey,AESiv);
        
        // Allocate a byte array for the ciphertext
        byte[] cipherBytes = new byte[AESCipher.getOutputSize(plainBytes.length)];

        // Use update to encrypt
        // input: plainBytes, input length: plainBytes.length, input offset: 0
        // output to: cipherBytes, output offset: 0
        int cipherLength = AESCipher.update(plainBytes, 0 ,plainBytes.length ,cipherBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: cipherBytes, output offset: cipherLength
        cipherLength += AESCipher.doFinal(cipherBytes, cipherLength);

        // Return the ciphertext bytes
        return cipherBytes;
    }

    private String decryptAES(byte[] ciphertext) throws Exception
    {
        System.out.println("Decrypting using AES...");

        // Initialize the Cipher object to decrypt
        AESCipher.init(Cipher.DECRYPT_MODE, AESKey,AESiv);
        
        // Allocate a byte array for the plaintext
        byte[] plainBytes = new byte[AESCipher.getOutputSize(ciphertext.length)];

        // Use update to decrypt
        // input: ciphertext, input length: ciphertext.length, input offset: 0
        // output to: plainBytes, output offset: 0
        int plainLength = AESCipher.update(ciphertext,0, ciphertext.length, plainBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: plainBytes, output offset: plainLength
        plainLength += AESCipher.doFinal(plainBytes, plainLength);

        // Return plaintext string from plaintext bytes
        return new String(plainBytes);
    }
    
    private byte[] encryptDES(String plaintext) throws Exception
    {
        System.out.println("Encrypting using DES...");

        // Convert plaintext string to byte array
        byte[] plainBytes = plaintext.getBytes();

        // Initialize the Cipher object to encrypt
        DESCipher.init(Cipher.ENCRYPT_MODE, DESKey,DESiv);
        
        // Allocate a byte array for the ciphertext
        byte[] cipherBytes = new byte[DESCipher.getOutputSize(plainBytes.length)];

        // Use update to encrypt
        // input: plainBytes, input length: plainBytes.length, input offset: 0
        // output to: cipherBytes, output offset: 0
        int cipherLength = DESCipher.update(plainBytes, 0 ,plainBytes.length ,cipherBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: cipherBytes, output offset: cipherLength
        cipherLength += DESCipher.doFinal(cipherBytes, cipherLength);

        // Return the ciphertext bytes
        return cipherBytes;
    }

    private String decryptDES(byte[] ciphertext) throws Exception
    {
        System.out.println("Decrypting using DES...");

        // Initialize the Cipher object to decrypt
        DESCipher.init(Cipher.DECRYPT_MODE, DESKey,DESiv);
        
        // Allocate a byte array for the plaintext
        byte[] plainBytes = new byte[DESCipher.getOutputSize(ciphertext.length)];

        // Use update to decrypt
        // input: ciphertext, input length: ciphertext.length, input offset: 0
        // output to: plainBytes, output offset: 0
        int plainLength = DESCipher.update(ciphertext,0, ciphertext.length, plainBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: plainBytes, output offset: plainLength
        plainLength += DESCipher.doFinal(plainBytes, plainLength);

        // Return plaintext string from plaintext bytes
        return new String(plainBytes);
    }
    
    //*************************************************************************
    //Generates a random key of size 128-bits
    private Key generateKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES","BC");
        generator.init(128);
        
        return generator.generateKey();
    }
    
    //Creates a new random initializing vector(IV) 
    private byte[] generateIV(){
        SecureRandom rand =new SecureRandom();
        byte [] randBytes = new byte[16];
        rand.nextBytes(randBytes);
        
        return randBytes;
    }
}
