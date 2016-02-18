/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
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
    public static void main(String[] args) {
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
    }
    
}
