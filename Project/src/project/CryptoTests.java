package project;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 * Class CryptoTests
 * Encrypts and decrypts using AES and DES.
 * @author alice
 */
public class CryptoTests {

    final private Cipher AESCipher;        // Cipher object to be used with AES
    final private Cipher DESCipher;        // Cipher object to be used with DES
    final private Key AESKey;              // Key to be used with AES
    final private Key DESKey;              // Key to be used with DES
    final private IvParameterSpec AESiv;   // Initialization vector to be used with AES in CBC mode
    final private IvParameterSpec DESiv;   // Initialization vector to be used with DES in CBC mode

    /**
     * Constructor
     * @throws Exception
     */
    public CryptoTests() throws Exception
    {
        //AES
        // Initialize Cipher object for AES with CBC
        AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC"); 
        // Get 16-bit initialization vector for AES
        AESiv = new IvParameterSpec(generateIV(16));
        // Get 128-bit AES key
        AESKey = generateAESKey();
        
        //DES
        // Create cipher object for DES with CBC
        DESCipher = Cipher.getInstance("DES/CBC/PKCS5Padding", "BC"); 
        // Get 8-bit initialization vector for DES
        DESiv = new IvParameterSpec(generateIV(8));
        // Get 64-bit DES key
        DESKey = generateDESKey();
    }
    
    /**
     * Generates a random 128-bit AES key
     * @return random 128-bit DES key
     * @throws Exception 
     */
    private Key generateAESKey() throws Exception
    {
        // Get key generator for AES 
        KeyGenerator generator = KeyGenerator.getInstance("AES","BC");
        // Initialize the generator for 128-bit key size
        generator.init(128);
        
        // Return generated key
        return generator.generateKey();
    }
    
    /**
     * Generates a random 64-bit DES key
     * @return random 64-bit DES key
     * @throws Exception 
     */
    private Key generateDESKey() throws Exception
    {
        // Get key generator for DES 
        KeyGenerator generator = KeyGenerator.getInstance("DES","BC");
        // Initialize the generator for 64-bit key size
        generator.init(64);
        
        // Return generated key
        return generator.generateKey();
    }
    
    /**
     * Generates a random initialization vector of size n
     * @param n
     * @return array of n random bytes
     */
    private byte[] generateIV(int n)
    {
        SecureRandom rand =new SecureRandom();
        byte [] randBytes = new byte[n];
        rand.nextBytes(randBytes);
        
        return randBytes;
    }

    /**
     * Calls private functions to perform encryption and decryption on 
     * originalPlaintext using the specified algorithm.
     * @param originalPlaintext
     * @param algorithm
     * @throws Exception 
     */
    public void encryptdecrypt (String originalPlaintext, String algorithm) throws Exception
    {
        byte[] cipherBytes;         // To hold enciphered bytes
        String decryptedPlaintext;  // To hold deciphered text
        
        int pm_size = 0;
        
        switch (algorithm.toUpperCase()) {
            case "AES":
                // Get enciphered bytes
                cipherBytes = encryptAES(originalPlaintext.getBytes());
                // Print the size of data
                System.out.println("The plaintext size:" + originalPlaintext.getBytes().length);
                System.out.print("Predicted encrypted byte size using PKCS5Padding :");
                pm_size = originalPlaintext.getBytes().length;
                pm_size += 16 - (pm_size % 16);
                System.out.println(pm_size);
                System.out.println("The encrypted byte size:" + cipherBytes.length);
                // Print enciphered bytes
                System.out.print("The enciphered bytes are: ");
                for (int j=0; j<cipherBytes.length; j++) {
                    System.out.format("%02X ", cipherBytes[j]);
                }
                System.out.println();
                // Get deciphered text
                decryptedPlaintext = decryptAES(cipherBytes);
                // Print deciphered text
                System.out.println("Your plaintext was: " + decryptedPlaintext);
                break;
            case "DES":
                // Get enciphered bytes
                cipherBytes = encryptDES(originalPlaintext.getBytes());
                // Print the size of data
                System.out.println("The plaintext size:" + originalPlaintext.getBytes().length);
                System.out.print("Predicted encrypted byte size using PKCS5Padding :");
                pm_size = originalPlaintext.getBytes().length;
                pm_size += 8 - (pm_size % 8);
                System.out.println(pm_size);
                System.out.println("The encrypted byte size:" + cipherBytes.length);
                // Print enciphered bytes
                System.out.print("The enciphered bytes are: ");
                for (int j=0; j<cipherBytes.length; j++) {
                    System.out.format("%02X ", cipherBytes[j]);
                }
                System.out.println();
                // Get deciphered text
                decryptedPlaintext = decryptDES(cipherBytes);
                // Print deciphered text
                System.out.println("Your plaintext was: " + decryptedPlaintext);
                break;
            default:
                // No matching algorithm available
                System.out.println("Please use a valid algorithm ID");
                break;
        }
    }

    /**
     * Performs encryption on bytes using AES.
     * @param plainBytes
     * @return array of enciphered bytes
     * @throws Exception 
     */
    private byte[] encryptAES(byte[] plainBytes) throws Exception
    {
        System.out.println("Encrypting using AES...");

        // Initialize the Cipher object to encrypt
        AESCipher.init(Cipher.ENCRYPT_MODE, AESKey, AESiv);
        
        // Allocate a byte array for the ciphertext bytes
        byte[] cipherBytes = new byte[AESCipher.getOutputSize(plainBytes.length)];

        // Use update to encrypt
        // input: plainBytes, input offset: 0, input length: plainBytes.length, 
        // output to: cipherBytes, output offset: 0
        int cipherLength = AESCipher.update(plainBytes, 0, plainBytes.length, cipherBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: cipherBytes, output offset: cipherLength
        cipherLength += AESCipher.doFinal(cipherBytes, cipherLength);

        // Return the ciphertext bytes
        return cipherBytes;
    }

    /**
     * Performs decryption on ciphertext using AES.
     * @param ciphertext
     * @return deciphered plaintext string
     * @throws Exception 
     */
    private String decryptAES(byte[] ciphertext) throws Exception
    {
        System.out.println("Decrypting using AES...");

        // Initialize the Cipher object to decrypt
        AESCipher.init(Cipher.DECRYPT_MODE, AESKey, AESiv);
        
        // Allocate a byte array for the plaintext bytes
        byte[] plainBytes = new byte[AESCipher.getOutputSize(ciphertext.length)];

        // Use update to decrypt
        // input: ciphertext, input offset: 0, input length: ciphertext.length, 
        // output to: plainBytes, output offset: 0
        int plainLength = AESCipher.update(ciphertext,0, ciphertext.length, plainBytes, 0);
        
        // Finish decryption (if any input is left in buffer of the Cipher object)
        // output to: plainBytes, output offset: plainLength
        plainLength += AESCipher.doFinal(plainBytes, plainLength);

        // Return plaintext string from plaintext bytes
        return new String(plainBytes);
    }
    
    /**
     * Performs encryption on bytes using DES.
     * @param plainBytes
     * @return array of enciphered bytes
     * @throws Exception 
     */
    private byte[] encryptDES(byte[] plainBytes) throws Exception
    {
        System.out.println("Encrypting using DES...");

        // Initialize the Cipher object to encrypt
        DESCipher.init(Cipher.ENCRYPT_MODE, DESKey, DESiv);
        
        // Allocate a byte array for the ciphertext bytes
        byte[] cipherBytes = new byte[DESCipher.getOutputSize(plainBytes.length)];

        // Use update to encrypt
        // input: plainBytes, input offset: 0, input length: plainBytes.length, 
        // output to: cipherBytes, output offset: 0
        int cipherLength = DESCipher.update(plainBytes, 0, plainBytes.length, cipherBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: cipherBytes, output offset: cipherLength
        cipherLength += DESCipher.doFinal(cipherBytes, cipherLength);

        // Return the ciphertext bytes
        return cipherBytes;
    }

    /**
     * Performs decryption on ciphertext using AES.
     * @param ciphertext
     * @return deciphered plaintext string
     * @throws Exception 
     */
    private String decryptDES(byte[] ciphertext) throws Exception
    {
        System.out.println("Decrypting using DES...");

        // Initialize the Cipher object to decrypt
        DESCipher.init(Cipher.DECRYPT_MODE, DESKey, DESiv);
        
        // Allocate a byte array for the plaintext bytes
        byte[] plainBytes = new byte[DESCipher.getOutputSize(ciphertext.length)];

        // Use update to decrypt
        // input: ciphertext, input offset: 0, input length: ciphertext.length, 
        // output to: plainBytes, output offset: 0
        int plainLength = DESCipher.update(ciphertext,0, ciphertext.length, plainBytes, 0);
        
        // Finish encryption (if any input is left in buffer of the Cipher object)
        // output to: plainBytes, output offset: plainLength
        plainLength += DESCipher.doFinal(plainBytes, plainLength);

        // Return plaintext string from plaintext bytes
        return new String(plainBytes);
    }
}
