package assignments;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyService {

    public static void generateKeypair() throws Exception {
        System.out.println("\nGenerating keypair");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

        System.out.println("Public Exponent: " + publicKeySpec.getPublicExponent());
        System.out.println("Private Exponent: " + privateKeySpec.getPrivateExponent());

        KeyService.writeToFile("Public.key", publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        KeyService.writeToFile("Private.key", privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
    }

    public static PrivateKey readPrivateKeyFromFile() throws Exception {
        System.out.println("\nReading the private key from the file");

        FileInputStream fis = new FileInputStream(new File("Private.key"));
        ObjectInputStream ois = new ObjectInputStream(fis);

        PrivateKey privateKey = null;

        try {
            BigInteger mod = (BigInteger) ois.readObject();
            BigInteger exp = (BigInteger) ois.readObject();

            System.out.println("Private modulus " + mod);
            System.out.println("Private exponent " + exp);

            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
            fis.close();
        }

        return privateKey;

    }

    public static PublicKey readPublicKeyFromFile() throws Exception {
        System.out.println("\nReading the public key from the file");

        FileInputStream fis = new FileInputStream(new File("Public.key"));
        ObjectInputStream ois = new ObjectInputStream(fis);

        PublicKey publicKey = null;

        try {
            BigInteger mod = (BigInteger) ois.readObject();
            BigInteger exp = (BigInteger) ois.readObject();

            System.out.println("Public modulus: " + mod);
            System.out.println("Public exponent: " + exp + "\n");

            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(mod, exp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
            fis.close();
        }

        return publicKey;
    }


    private static void writeToFile(String fileName, BigInteger mod, BigInteger exp) throws Exception {
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
