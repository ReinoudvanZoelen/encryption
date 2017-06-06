package assignments;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Encryptor {

    public void assignment_1(String password) throws Exception {
//        De eerste applicatie genereert een publieke sleutel van 1024 bits volgens het RSA-algoritme en de bijbehorende
//        private sleutel en schrijft beide sleutels in afzonderlijke files weg.


        System.out.println("===== Starting assignment 1, password: " + password);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        WriteToFile("Public_" + password, keyPair.getPublic().getEncoded());
        WriteToFile("Private_" + password, keyPair.getPrivate().getEncoded());
    }

    private void WriteToFile(String fileName, byte[] message) {
        FileOutputStream fos = null;
        try {
            if (fileName.length() == 0) {
                // If the filename is empty generate a filename from the current date and time
                fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            }

            // Write the data to the file
            fos = new FileOutputStream(fileName);
            fos.write(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            try {
                // Close the writer regardless of what happens...
                fos.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }



    public void assignment_2(String path) throws Exception {
//        De tweede applicatie leest een file die een private sleutel bevat en een andere file
//        (die we even INPUT.EXT zullen noemen). Verder wordt de naam van de ondertekenaar(bijv.Lk) opgegeven.
//        Als output wordt een file met de naam INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud:
//        de lengte (in bytes)van de digitale handtekening,  de digitale handtekening (gemaakt met
//        het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.

        System.out.println("===== Starting assignment 2, path: " + path);

        Key key = getKeyFromFile(path);

        System.out.println(key.getEncoded());
    }

    public Key getKeyFromFile(String fileName) throws Exception{
        Key pk = null;
        File f = new File(fileName);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        dis.readFully(keyBytes);
        dis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        pk = kf.generatePublic(spec);
        return pk;
    }

    public void assignment_3() {
//        De derde applicatie leest file INPUT (SIGNEDBYLK).EXT en de publieke sleutel van de ondertekenaar en verifieert
//        de handtekening.Het resultaat (wel of niet goedgekeurd) wordt gemeld.
//        Als de handtekening klopt wordt bovendien de oorspronkelijke file INPUT.EXT gereconstrueerd.
    }
}
