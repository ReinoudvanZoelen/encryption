package assignments;

import javax.crypto.Cipher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Arrays;

/**
 * De tweede applicatie leest een file die een private sleutel bevat en een andere file
 * (die we even INPUT.EXT zullen noemen). Verder wordt de naam van de ondertekenaar(bijv.Lk) opgegeven.
 * Als output wordt een file met de naam INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud:
 * de lengte (in bytes)van de digitale handtekening,  de digitale handtekening (gemaakt met
 * het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.
 */
public class Assignment_2 {

    byte[] originalValue;
    private String signature;
    private byte[] signatureData;
    private byte[] input;

    public Assignment_2(String signature) throws Exception {
        this.signature = signature;

        this.resetInputFile();

        this.input = readInputFile("Input");

        signatureData = generateSignature();

        writeInputFile();

        readInputFile("input(signedby" + this.signature + ")");
    }

    public byte[] generateSignature() throws Exception {
        System.out.println("\nGenerating a signature");
        // Generate new key
        PrivateKey privateKey = Assignment_1.readPrivateKeyFromFile();
        String plaintext = new String(this.input, StandardCharsets.UTF_8);

        // Compute signature
        Signature instance = Signature.getInstance("SHA1withRSA");
        instance.initSign(privateKey);
        instance.update((plaintext).getBytes());
        byte[] signature = instance.sign();


        // Compute digest
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        byte[] digest = sha1.digest((plaintext).getBytes());

        // Encrypt digest
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherText = cipher.doFinal(digest);

        // Display results
        System.out.println("Input data: " + plaintext);
        System.out.println("Digest: " + new String(digest, StandardCharsets.UTF_8));
        System.out.println("Cipher text: " + new String(cipherText, StandardCharsets.UTF_8));
        System.out.println("Signature: " + new String(signature, StandardCharsets.UTF_8));

        return signature;
    }

    private void resetInputFile() throws Exception {
        System.out.println("\nResetting the input file");

        FileWriter fw = new FileWriter("input(signedby" + this.signature + ")");
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            bw.write("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

            System.out.println("input.ext has been saved. Expected (precalculated) signature value: ");
            System.out.println("80bac6a57a8dd3d0f7d50b0e792e17463b3f19d7");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bw.close();
            fw.close();
        }
    }

    private byte[] readInputFile(String pathname) throws Exception {
        System.out.println("\nReading the input file");

        byte[] readBytes = Files.readAllBytes(new File(pathname).toPath());

        System.out.println("Byte[] Raw data: \n" + Arrays.toString(readBytes));
        System.out.println("Byte[] Converted to String: \n" + new String(readBytes, StandardCharsets.UTF_8));

        this.originalValue = readBytes;

        return readBytes;
    }

    private void writeInputFile() throws Exception {
        System.out.println("\nWriting the input file");

        FileWriter fw = new FileWriter("input(signedby" + this.signature + ")");
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            bw.write("Length of the signature is: " + signatureData.length + "\n");
            bw.write("Signed by: " + signature.toString() + "\n");
            bw.write("Signature: " + new String(this.signatureData, StandardCharsets.UTF_8) + "\n");
            bw.write("Original value: " + new String(this.originalValue, StandardCharsets.UTF_8) + "\n");

            System.out.println("input.ext has been saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bw.close();
            fw.close();
        }
    }
}
