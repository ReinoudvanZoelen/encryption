package PasswordHashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class LoginProgram {

    public static List<User> users = new ArrayList<>();

    public LoginProgram() throws NoSuchAlgorithmException {
        //this.Register("Niels", "MyPassword1234");
    }

    public void Register(String name, String password) throws NoSuchAlgorithmException {
        System.out.println("Creating a random salt value");
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[128];
        random.nextBytes(salt);

        System.out.println("Salt value: " + new String(salt));

        String SaltAndPassword = new String(salt) + ":" + password;

        byte[] hashedPassword = this.getHashWithSalt(SaltAndPassword, salt);

        LoginProgram.users.add(new User(name, salt, hashedPassword));
    }

    public byte[] getHashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(salt);
        byte[] hashedBytes = digest.digest(input.getBytes());

        System.out.println("Initial hash: " + new String(hashedBytes));

        return hashedBytes;
    }

    public boolean Login(String name, String password) throws NoSuchAlgorithmException {

        byte[] salt = null;

        for (User user : LoginProgram.users) {
            if (user.getName() == name) {
                salt = user.getSalt();
            }
        }

        if (salt == null) {
            return false;
        }

        byte[] hashedPassword = this.getHashWithSalt(new String(salt) + ":" + password, salt);

        System.out.println("Final hash: " + new String(hashedPassword));

        for (User user : LoginProgram.users
                ) {
            System.out.println("Password for " + user.getName() + ": " + new String(user.getHashedPassword()));
            for (int i = 0; i < user.getHashedPassword().length; i++) {
                if (user.getHashedPassword()[i] != hashedPassword[i]) return false;
            }
        }

        return true;
    }
}
