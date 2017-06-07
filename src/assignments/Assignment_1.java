package assignments;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

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
        System.out.println("===== Starting assignment 1");

        this.Public_Key_File = public_Key_File;
        this.Private_Key_File = private_Key_File;
        this.Encryption_Type = encryption_Type;

        this.generateKeypair();
        byte[] encrypted = this.encryptData(data);
        byte[] decrypted = this.decryptData(encrypted);
    }

    private void generateKeypair() throws Exception {
        System.out.println("\nGenerating keypair");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.Encryption_Type);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance(Encryption_Type);
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

        System.out.println("Public Exponent: " + publicKeySpec.getPublicExponent());
        System.out.println("Private Exponent: " + privateKeySpec.getPrivateExponent());

        this.writeToFile(this.Public_Key_File, publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        this.writeToFile(this.Private_Key_File, privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
    }


    private byte[] encryptData(String data) throws Exception {
        System.out.println("\nStarting Encryption");
        System.out.println("Data before encryption: " + data);

        byte[] dataToEncrypt = data.getBytes();

        PublicKey publicKey = readPublicKeyFromFile();

        Cipher cipher = Cipher.getInstance(Encryption_Type);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(dataToEncrypt);

        System.out.println("Encrypted data: " + new String(encrypted));

        return encrypted;
    }

    private byte[] decryptData(byte[] data) throws Exception {
        System.out.println("\nStarting Decryption");

        PrivateKey privateKey = readPrivateKeyFromFile();

        Cipher cipher = Cipher.getInstance(this.Encryption_Type);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decrypted = cipher.doFinal(data);
        System.out.println("Decrypted data: " + new String(decrypted));

        return decrypted;
    }

    private PrivateKey readPrivateKeyFromFile() throws Exception {
        System.out.println("\nReading the private key from the file");

        FileInputStream fis = new FileInputStream(new File(this.Private_Key_File));
        ObjectInputStream ois = new ObjectInputStream(fis);

        PrivateKey privateKey = null;

        try {
            BigInteger mod = (BigInteger) ois.readObject();
            BigInteger exp = (BigInteger) ois.readObject();

            System.out.println("Private modulus " + mod);
            System.out.println("Private exponent " + exp);

            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance(Encryption_Type);
            privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
            fis.close();
        }

        return privateKey;

    }

    private PublicKey readPublicKeyFromFile() throws Exception {
        System.out.println("\nReading the public key from the file");

        FileInputStream fis = new FileInputStream(new File(this.Public_Key_File));
        ObjectInputStream ois = new ObjectInputStream(fis);

        PublicKey publicKey = null;

        try {
            BigInteger mod = (BigInteger) ois.readObject();
            BigInteger exp = (BigInteger) ois.readObject();

            System.out.println("Public modulus: " + mod);
            System.out.println("Public exponent: " + exp + "\n");

            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance(Encryption_Type);
            publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
            fis.close();
        }

        return publicKey;
    }


    private void writeToFile(String fileName, BigInteger mod, BigInteger exp) throws Exception {
        System.out.println("\nWriting file " + fileName);

        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));

        try {
            oos.writeObject(mod);
            oos.writeObject(exp);

            System.out.println(fileName + " has been saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oos.close();
            fos.close();
        }
    }
}
