/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.util.Scanner;
import java.security.Security;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author alice
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
         //add at runtime the Bouncy Castle Provider
    	//the provider is available only for this application
    	Security.addProvider(new BouncyCastleProvider());
 
    	//BC is the ID for the Bouncy Castle provider;
        if (Security.getProvider("BC") == null){
            System.out.println("Bouncy Castle provider is NOT available");
        }
        else{
            System.out.println("Bouncy Castle provider is available");
        }   
        //Scanner
        Scanner input = new Scanner (System.in);
        
        //Ask for user for plaintext input
        System.out.println("Enter text that you wish to encrypt: ");
        String plaintext = input.nextLine();    //Stores plaintext from user
            
        //Input variable
        byte [] text = new String(plaintext).getBytes();
        String encryptedText;   //Stores encryptedText
        String decryptedText;   //Stores decryptedText
        
        //Creates a new random initializing vector(IV)
        SecureRandom rand =new SecureRandom();
        byte [] randBytes = new byte[8];
        rand.nextBytes(randBytes);
        IvParameterSpec iv = new IvParameterSpec(randBytes);
        
        //Creates an instance of DES Cypher in CBC set up with a PKCS5Padding through the BouncyCastle provider.
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding","BC");
        
        //Creates a KeyGenerator DES with the BouncyCastle provider
        KeyGenerator generator = KeyGenerator.getInstance("DES","BC");
        
        //Generate a random key of size 64 bits
        generator.init(64);
        Key key = generator.generateKey();
        
        //Cipher initialized to encrypt with a key and with IV algorithm
        cipher.init(Cipher.ENCRYPT_MODE,key,iv);
        
        //Creates an array of byte, cipherText, to hold the encrypted data
        byte [] cipherText = new byte[cipher.getOutputSize(text.length)];
        
        //Cipher Text encryption
        int ctLength = cipher.update(text,0,text.length,cipherText,0);
        
        //Updating the cyphertext length
        ctLength += cipher.doFinal (cipherText, ctLength);
        
        //cipherText from byte to String
        encryptedText = new String(cipherText);
        
        //Prints the Ciphertext
        System.out.println("Ciphertext: " + encryptedText);
        
        //Cipher initialized to decrypt with key and IV algorithm
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
         
        //Create an array of byte, plaintext, to hold the size of the plaintext
        byte []plainText = new byte [cipher.getOutputSize(ctLength)];
        
        //Continues the decryption operaton 
        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        
        //Finalizes the decryption operation
        ptLength = cipher.doFinal (plainText,ptLength);
        
        //Convert the text from byte into string 
        decryptedText = new String (plainText);
        
        //Prints decryptedText
        System.out.println("Decrypted Text: " + decryptedText);
        
        
    }
    
}
