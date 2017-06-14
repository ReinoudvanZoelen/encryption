package assignments;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * De eerste applicatie genereert een publieke sleutel van 1024 bits volgens het RSA-algoritme en de bijbehorende
 * private sleutel en schrijft beide sleutels in afzonderlijke files weg.
 */
public class Assignment_1 {

    private String Public_Key_File = "Public.key";
    private String Private_Key_File = "Private.key";
    private String Encryption_Type = "RSA";
    private String data = "ThisIsMyMostSecurePassword1234321";

    public Assignment_1(String public_Key_File, String private_Key_File, String encryption_Type) throws Exception {
        System.out.println("\n\n\n===== Starting assignment 1");

        this.Public_Key_File = public_Key_File;
        this.Private_Key_File = private_Key_File;
        this.Encryption_Type = encryption_Type;

        //KeyService.generateKeypair();
        byte[] encrypted = this.encryptData(data);
        byte[] decrypted = this.decryptData(encrypted);
    }

    private byte[] encryptData(String data) throws Exception {
        System.out.println("\nStarting Encryption");
        System.out.println("Data before encryption: " + data);

        byte[] dataToEncrypt = data.getBytes();

        PublicKey publicKey = KeyService.readPublicKeyFromFile();

        Cipher cipher = Cipher.getInstance(Encryption_Type);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(dataToEncrypt);

        System.out.println("Encrypted data: " + new String(encrypted));

        return encrypted;
    }

    private byte[] decryptData(byte[] data) throws Exception {
        System.out.println("\nStarting Decryption");

        PrivateKey privateKey = KeyService.readPrivateKeyFromFile();

        Cipher cipher = Cipher.getInstance(this.Encryption_Type);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decrypted = cipher.doFinal(data);
        System.out.println("Decrypted data: " + new String(decrypted));

        return decrypted;
    }


}
