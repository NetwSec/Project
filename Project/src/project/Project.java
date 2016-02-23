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
        
        //    	//BC is the ID for the Bouncy Castle provider;
//        if (Security.getProvider("BC") == null){
//            System.out.println("Bouncy Castle provider is NOT available");
//        }
//        else{
//            System.out.println("Bouncy Castle provider is available");
//        } 
  
        //Scanner
        Scanner input = new Scanner (System.in);
        
        //Ask for user for plaintext input
        System.out.println("Enter text that you wish to encrypt: ");
        String plaintext = input.nextLine();    //Stores plaintext from user

        //Creates a new object of type DES
        DES test = new DES(plaintext);
        
        //DES encryption
        test.encryption();
        
        //Prints Ciphertext after encryption
        System.out.println("Ciphertext: " + test.getCipherText());
        
        //DES decryption
        test.decryption();
        
        //Prints Plaintext after decryption
        System.out.println("Plaintext: " + test.getPlainText());
        
        // ALICE'S TESTS
        //        String plaintext;
        //        Scanner s = new Scanner(System.in);
//        // Get a line of plaintext
//        System.out.print("Please enter a plaintext message: ");
//        plaintext = s.nextLine();
//        
//        // Get a CryptoTests instance
//        CryptoTests crypto = new CryptoTests();
//        crypto.encryptdecrypt(plaintext, "AES");

//        byte[] plainBytes = plaintext.getBytes();
//        System.out.println("Your message was: " + new String(plainBytes));
    }
    
}
