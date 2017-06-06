import assignments.Encryptor;

public class Main {
    public static void main(String[] args) throws Exception {
        Encryptor encryptor = new Encryptor();

        String password = "Password1234";

        encryptor.assignment_1(password);
        encryptor.assignment_2("Public_" + password);
        encryptor.assignment_2("Private_" + password);

    }
}