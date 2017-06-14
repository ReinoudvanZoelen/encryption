package assignments;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * De tweede applicatie leest een file die een private sleutel bevat en een andere file
 * (die we even INPUT.EXT zullen noemen). Verder wordt de naam van de ondertekenaar(bijv.Lk) opgegeven.
 * Als output wordt een file met de naam INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud:
 * de lengte (in bytes)van de digitale handtekening,  de digitale handtekening (gemaakt met
 * het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.
 */
public class Assignment_2 {


    public Assignment_2(String signaturer) throws Exception {
        System.out.println("\n\n\n===== Starting assignment 2");
        // Bestand inlezen: input
        String input = ReadInputFromFile();

        // RSA-1 hash genereren van de input file
        String hash = GenerateHashValue(input);

        // Tweede bestand aanmaken met drie regels:
        WriteSignatureToFile(signaturer, input, hash);
    }


    private String ReadInputFromFile() throws IOException {
        System.out.println("\nReading the input from the file");

        String contents = new String(Files.readAllBytes(Paths.get("input")));
        System.out.println("Input: " + contents);

        return contents;
    }


    private String GenerateHashValue(String input) throws NoSuchAlgorithmException {
        System.out.println("\nGenerating the SHA-1 hash");
        // Compute signature
        Signature instance = Signature.getInstance("SHA1withRSA");

        // Compute digest
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        byte[] digest = sha1.digest((input).getBytes());

        String output = new String(digest);

        System.out.println("SHA-1 hash: " + output);

        return output;
    }

    private void WriteSignatureToFile(String signaturer, String input, String hash) throws IOException {
        String fileName = "Input(SignedBy" + signaturer + ")";

        System.out.println("\nWriting file " + fileName);

        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));

        try {
            oos.writeObject("Signature length: " + hash.length());
            oos.writeObject("Signature: " + hash);
            oos.writeObject("Original file:");
            oos.writeObject(input);

            System.out.println(fileName + " has been saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oos.close();
            fos.close();
        }
    }
}
