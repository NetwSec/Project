package project;
import java.util.Scanner;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Project {

    public static void main(String[] args) throws Exception
    {   
        // Add the Bouncy Castle Provider at runtime (only available for this application)
    	Security.addProvider(new BouncyCastleProvider());

        Scanner scanner = new Scanner(System.in);
        
        while (true)
        {
            // Get a line of plaintext
            System.out.print("Please enter the text you want to encrypt, or exit to quit the program: ");
            String plaintext = scanner.nextLine();
            
            if (plaintext.equals("exit")) break;

            // Get a CryptoTests instance
            CryptoTests crypto = new CryptoTests();
            // AES encryption/decryption
            crypto.encryptdecrypt(plaintext, "AES");
            // DES encryption/decryption
            crypto.encryptdecrypt(plaintext, "DES");
            // Destroy the object
            crypto = null;
        }
    }

}
