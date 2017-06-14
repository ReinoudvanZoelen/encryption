package assignments;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        byte[] hash = GenerateHashValue(input);

        // Tweede bestand aanmaken met drie regels:
        WriteSignatureToFile(signaturer, input, hash);
    }


    private String ReadInputFromFile() throws IOException {
        System.out.println("\nReading the input from the file");

        String contents = new String(Files.readAllBytes(Paths.get("input")));
        System.out.println("Input: " + contents);

        return contents;
    }


    private byte[] GenerateHashValue(String input) throws Exception {
        System.out.println("\nGenerating the SHA-1 hash");

        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initSign(KeyService.readPrivateKeyFromFile());
        signer.update(input.getBytes());

        byte[] hash = signer.sign();
        System.out.println(new String(hash));

        return hash;
    }

    private void WriteSignatureToFile(String signaturer, String input, byte[] hash) throws IOException {
        String fileName = "Input(SignedBy" + signaturer + ")";

        System.out.println("\nWriting file " + fileName);

        FileOutputStream fos = new FileOutputStream(fileName);

        try {
            fos.write(new String("\nSignature length: " + new String(hash).length()).getBytes());
            fos.write(new String("\nSignature: " + hash).getBytes());
            fos.write(new String("\nOriginal file below").getBytes());
            fos.write(new String("\n" + input).getBytes());

            System.out.println(fileName + " has been saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.WriteSignatureToIndividualFile(hash);
            fos.close();
        }
    }

    private void WriteSignatureToIndividualFile(byte[] hash) throws IOException {
        System.out.println("\nWriting signature to individual file");

        FileOutputStream fos = new FileOutputStream("hash");

        try {
            fos.write(hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }
}
