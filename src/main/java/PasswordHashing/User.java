package PasswordHashing;

/**
 * Created by Reino on 14-6-2017.
 */
public class User {
    String name;
    byte[] salt;
    byte[] hashedPassword;


    public User(String name, byte[] salt, byte[] hashedPassword) {
        this.name = name;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
