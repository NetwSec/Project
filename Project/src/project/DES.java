/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Carlos
 */
public class DES {

    //Variables
    private byte [] cipherText;     //holds the ciphertext in bytes
    private byte [] plainText;      //holds the plaintext after decryption in bytes
    private final byte [] text;           //holds the plaintext from constructor/user input in bytes
    final private Cipher cipher;    //Cipher variable used to create an instance of DES
    final private Key key;          //Key variables used to hold the 64-bit key
    final private IvParameterSpec iv;  //holds the initialization vector(IV)
    private int ctLength;           //holds the length of the ciphertext
    private int ptLength;           //holds the length of the plaintext

    //Constructor with String Parameter
    public DES (String input) throws Exception{
        text = new String(input).getBytes();
        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding","BC");
        key = generateKey();
        iv = new IvParameterSpec(generateIV());
    }

    //Generates a random key of size 64-bits
    private Key generateKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("DES","BC");
        generator.init(64);

        return generator.generateKey();
    }

    //Creates a new random initializing vector(IV)
    private byte[] generateIV(){
        SecureRandom rand =new SecureRandom();
        byte [] randBytes = new byte[8];
        rand.nextBytes(randBytes);

        return randBytes;
    }

    //Encryption
    public void encryption() throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE,key,iv);
        cipherText = new byte[cipher.getOutputSize(text.length)];
        ctLength = cipher.update(text,0,text.length,cipherText,0);
        ctLength += cipher.doFinal(cipherText,ctLength);
    }

    //Decryption
    public void decryption() throws Exception{
        cipher.init(Cipher.DECRYPT_MODE,key,iv);
        plainText = new byte [cipher.getOutputSize(ctLength)];
        ptLength = cipher.update(cipherText,0,ctLength,plainText,0);
        ptLength = cipher.doFinal(plainText,ptLength);
    }

    //Accessors/Getters
    //getCipherText
    public String getCipherText(){
        String temp = new String (cipherText);
        return temp;    //Returns cipherText in String
    }

    //getPlainText
    public String getPlainText(){
        String temp = new String (plainText);
        return temp;   //Returns plainText in String
    }

}
