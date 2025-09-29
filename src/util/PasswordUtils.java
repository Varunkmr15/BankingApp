package util;

import java.security.SecureRandom; //rng
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;


public class PasswordUtils {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(char[] password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return f.generateSecret(spec).getEncoded();
    }

    public static boolean verifyPassword(char[] attempted, byte[] salt, byte[] expectedHash) throws Exception {
        byte[] pwdHash = hashPassword(attempted, salt);
        if (pwdHash.length != expectedHash.length) return false;
        int diff = 0;
        for (int i = 0; i < pwdHash.length; i++) diff |= pwdHash[i] ^ expectedHash[i];
        return diff == 0;
    }
}
