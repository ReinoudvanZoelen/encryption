package assignments;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * De derde applicatie leest file INPUT (SIGNEDBYLK).EXT en de publieke sleutel van de ondertekenaar en verifieert
 * de handtekening.Het resultaat (wel of niet goedgekeurd) wordt gemeld.
 * Als de handtekening klopt wordt bovendien de oorspronkelijke file INPUT.EXT gereconstrueerd.
 */
public class Assignment_3 {

    public Assignment_3() throws Exception {
        System.out.println("\n\n\n===== Starting LoginProgram 3");

        // Haal hash uit het tekstbestand
        byte[] encryptedHash = GetHashFromFile();

        // Decrypt de hash
        byte[] decryptedHash = DecryptHash(encryptedHash);

        // Compare decryptedHash and a newly generated hash
        CompareHashes();
    }

    private byte[] GetHashFromFile() throws IOException {
        System.out.println("\nReading the hash from the file");

        byte[] contents = Files.readAllBytes(Paths.get("hash"));

        System.out.println("Input: " + new String(contents));

        return contents;
    }

    private byte[] DecryptHash(byte[] encryptedHash) throws Exception {
        System.out.println("\nStarting Decryption");

        PrivateKey privateKey = Assignment_1.readPrivateKeyFromFile();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decrypted = cipher.doFinal(encryptedHash);
        System.out.println("Decrypted data: " + new String(decrypted));

        return decrypted;
    }

    private void CompareHashes() {
    }
}
