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
            
            if (plaintext.equals("exit"))
            {
                break;
            }
            else if (plaintext.equals("extra")) 
            {
                extra();
            }
            else
            {
                encrypt_decrypt(plaintext);
            }
        }
    }
    
    public static void encrypt_decrypt (String plaintext) throws Exception
    {
        // Get a CryptoTests instance
        CryptoTests crypto = new CryptoTests();

        byte[] cipherBytes;         // To hold enciphered bytes
        String decryptedPlaintext;  // To hold deciphered text
        int pm_size = 0;            // To hold predicted message size

        // DES encryption/decryption

        // Get enciphered bytes
        System.out.println("Encrypting using DES...");
        cipherBytes = crypto.encryptDES(plaintext.getBytes());

        /*
        // Print the size of data
        System.out.println("The plaintext size:" + plaintext.getBytes().length);
        System.out.print("Predicted encrypted byte size using PKCS5Padding :");
        pm_size = plaintext.getBytes().length;
        pm_size += 8 - (pm_size % 8); //8 == aes_block_size
        System.out.println(pm_size);

        System.out.println("The encrypted byte size:" + cipherBytes.length);
        */

        // Print enciphered bytes
        System.out.print("The enciphered bytes are: ");
        for (int j=0; j<cipherBytes.length; j++) {
            System.out.format("%02X ", cipherBytes[j]);
        }
        System.out.println();

        // Get deciphered text
        System.out.println("Decrypting using DES...");
        decryptedPlaintext = crypto.decryptDES(cipherBytes);

        // Print deciphered text
        System.out.println("Your plaintext was: " + decryptedPlaintext);

        // AES encryption/decryption
        // Get enciphered bytes
        System.out.println("Encrypting using AES...");
        cipherBytes = crypto.encryptAES(plaintext.getBytes());

        /*
        // Print the size of data
        System.out.println("The plaintext size:" + plaintext.getBytes().length);
        System.out.print("Predicted encrypted byte size using PKCS5Padding :");
        pm_size = plaintext.getBytes().length;
        pm_size += 16 - (pm_size % 16); //16 == aes_block_size
        System.out.println(pm_size);

        System.out.println("The encrypted byte size:" + cipherBytes.length);
        */

        // Print enciphered bytes
        System.out.print("The enciphered bytes are: ");
        for (int j=0; j<cipherBytes.length; j++) {
            System.out.format("%02X ", cipherBytes[j]);
        }
        System.out.println();

        // Get deciphered text
        System.out.println("Decrypting using AES...");
        decryptedPlaintext = crypto.decryptAES(cipherBytes);

        // Print deciphered text
        System.out.println("Your plaintext was: " + decryptedPlaintext);

        // Destroy the object
        crypto = null;
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
