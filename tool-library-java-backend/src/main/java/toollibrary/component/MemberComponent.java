package toollibrary.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import toollibrary.dao.MemberDao;
import toollibrary.exception.NotSignedInException;
import toollibrary.model.Member;

@Component
public class MemberComponent {
    @Autowired
    private MemberDao memberDao;

    @Value("${secretKey:secret}")
    private String secretKey;

    private Algorithm algorithm;
    
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).build();
    }

    /**
     * checks if a token is valid
     * @return a Member object for that user
     */
    public Member checkCredentials(String credentials) throws NotSignedInException {
        try {
            Long id = verifier.verify(credentials).getClaim("userId").asLong();
            return memberDao.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new NotSignedInException();
        }
    }

    /**
     * creates an acount with specified username and password
     * @return a token to be used in future requests, or null if the username is taken
     */
    public String addUser(String username, String password) {
        try {
            Member member = new Member(username, getHashSHA1(password));
            memberDao.save(member);
            return createSignInToken(member);
        } catch (DataIntegrityViolationException e) {
            // there is a duplicate username
            return null;
        }
    }

    /**
     * signs a user into their accont
     * @return a token to be used in future requests, or null if the username is taken
     */
    public String signin(String username, String password) {
        Member member = memberDao.findByUsernameAndPasswordHash(username, getHashSHA1(password));
        return member == null ? null : createSignInToken(member);
    }

    private String createSignInToken(Member member) {
        return JWT.create()
            .withClaim("userId", member.getId())
            .withClaim("username", member.getUsername())
            .withIssuedAt(new Date())
            .sign(algorithm);
    }

    private String getHashSHA1(String password){
        try {
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
