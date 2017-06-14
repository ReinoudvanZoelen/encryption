import PasswordHashing.LoginProgram;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class UserRegistrationTest {


    @Test
    public void Test_Register() throws NoSuchAlgorithmException {
        LoginProgram loginProgram = new LoginProgram();

        loginProgram.Register("Niels", "Password");

        Assert.assertNotNull(LoginProgram.users.get(0));


        byte[] b = LoginProgram.users.get(0).getHashedPassword();
        String s = new String(b);

        System.out.println("Hashed Password: " + s);

        boolean logintest = loginProgram.Login("Niels", "Password");
        Assert.assertTrue(logintest);
    }
}
