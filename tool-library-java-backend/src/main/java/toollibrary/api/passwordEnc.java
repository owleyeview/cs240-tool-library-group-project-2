package toollibrary.api;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;


public class passwordEnc {

    public static String getHashSHA1(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++){
                sb.append(Integer.toString (
                        (byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger("SHA-1").log(Level.SEVERE, null, e);
            return null;
        }

    }
}
