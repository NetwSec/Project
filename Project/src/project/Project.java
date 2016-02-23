package project;
import java.util.Scanner;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Project {

    public static void main(String[] args) throws Exception
    {   
        // Add the Bouncy Castle Provider at runtime (only available for this application)
    	Security.addProvider(new BouncyCastleProvider());

        String plaintext;
        Scanner scanner = new Scanner(System.in);
        
        // Get a line of plaintext
        System.out.print("Please enter text you want to encrypt: ");
        plaintext = scanner.nextLine();

        // Get a CryptoTests instance
        CryptoTests crypto = new CryptoTests();
        // AES encryption/decryption
        crypto.encryptdecrypt(plaintext, "AES");
        // DES encryption/decryption
        crypto.encryptdecrypt(plaintext, "DES");
    }

}
