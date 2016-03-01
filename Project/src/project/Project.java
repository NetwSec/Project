package project;
import java.util.Scanner;
import java.security.Security;
import java.security.SecureRandom;
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
            System.out.print("Please enter the text you want to encrypt, extra for time test, or exit to quit the program: ");
            String plaintext = scanner.nextLine();
            
            if (plaintext.equals("exit")) break;
            if (plaintext.equals("extra")) 
            {
                extra();
                break;
            }

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
    
    public static void extra () throws Exception
    {
        System.out.println("Generating random strings");
        
        // declares an array of byte strings
        byte[][] ByteStrings;

        // allocates memory for 100 strings
        ByteStrings = new byte[100][];
           
        // create random byte generator
        SecureRandom random = new SecureRandom();
        
        // generate 100 random string
        for (int i = 0; i < 100; i++)
        {
            // allocates memory for string
            ByteStrings[i] = new byte[50];
            
            // populate the string
            random.nextBytes(ByteStrings[i]);
        }
        
        // Get a CryptoTests instance
        CryptoTests crypto = new CryptoTests();
        
        System.out.println("Encrypting with DES");
        long time1 = System.nanoTime();
        for (int i = 0; i < 100; i++) crypto.encryptDES(ByteStrings[i]);
        long time2 = System.nanoTime();
        long timeDES = time2 - time1;
        
        System.out.println("Encrypting with AES");
        long time3 = System.nanoTime();
        for (int i = 0; i < 100; i++) crypto.encryptAES(ByteStrings[i]);
        long time4 = System.nanoTime();
        long timeAES = time4 - time3;
        
        System.out.format("DES encryption time: %d ns, AES encryption time: %d ns\n", timeDES, timeAES);
    }
}
