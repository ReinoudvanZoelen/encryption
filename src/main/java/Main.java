import assignments.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String Public_Key_File = "Public.key";
        String Private_Key_File = "Private.key";
        String Encryption_Type = "RSA";
        String signature = "Reinoud";

        //Assignment_1 assignment1 = new Assignment_1(Public_Key_File, Private_Key_File, Encryption_Type);
        Assignment_2 assignment2 = new Assignment_2(signature);
        //Assignment_3 assignment3 = new Assignment_3();

    }
}
