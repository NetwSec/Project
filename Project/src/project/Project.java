/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.util.Scanner;
import java.security.Security;
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
        //add the Bouncy Castle Provider at runtime 
    	//the provider is available only for this application
    	Security.addProvider(new BouncyCastleProvider());
/*
        //BC is the ID for the Bouncy Castle provider;
        if (Security.getProvider("BC") == null){
            System.out.println("Bouncy Castle provider is NOT available");
        }
        else{
            System.out.println("Bouncy Castle provider is available");
        }
*/
        //Ask for user for plaintext input
        System.out.println("Enter text that you wish to encrypt: ");
        
//        //Create Scanner object to read user input
//        Scanner input = new Scanner (System.in);
//        
//        //Stores plaintext from user
//        String plaintext = input.nextLine();
//
//        //Creates a new object of type DES
//        DES des_test = new DES(plaintext);
//
//        //DES encryption
//        des_test.encryption();
//
//        //Prints Ciphertext after encryption
//        System.out.println("Ciphertext: " + des_test.getCipherText());
//
//        //DES decryption
//        des_test.decryption();
//
//        //Prints Plaintext after decryption
//        System.out.println("Plaintext: " + des_test.getPlainText());

        //ALICE'S TESTS
        String plaintext;
        Scanner s = new Scanner(System.in);
        
        // Get a line of plaintext
        System.out.print("Please enter a plaintext message: ");
        plaintext = s.nextLine();

        // Get a CryptoTests instance
        CryptoTests crypto = new CryptoTests();
        crypto.encryptdecrypt(plaintext, "AES");
        crypto.encryptdecrypt(plaintext, "DES");
    }

}
