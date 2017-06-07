import assignments.Assignment_1;
import assignments.Assignment_2;
import assignments.Assignment_3;

public class Main {
    public static void main(String[] args) throws Exception {

        String Public_Key_File = "Public.key";
        String Private_Key_File = "Private.key";
        String Encryption_Type = "RSA";

        Assignment_1 assignment1 = new Assignment_1(Public_Key_File, Private_Key_File, Encryption_Type);
        //Assignment_2 assignment2 = new Assignment_2();
        //Assignment_3 assignment3 = new Assignment_3();

    }
}