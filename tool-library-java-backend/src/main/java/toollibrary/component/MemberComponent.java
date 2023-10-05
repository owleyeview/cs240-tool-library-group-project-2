package toollibrary.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
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
     * @throws NotSignedInException if it doesn't work
     */
    public Member checkCredentials(String credentials) throws NotSignedInException {
        Member member = findMember(credentials);
        if (member == null) {
            throw new NotSignedInException();
        }
        return member;
    }

    /**
     * checks if a token is valid
     * @return a Member object for that user, or null if it doesn't work
     */
    public Member findMember(String credentials) {
        try {
            Long id = verifier.verify(credentials).getClaim("userId").asLong();
            return memberDao.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * creates an acount with specified username and password
     * @return a token to be used in future requests, or null if the username is taken
     */
    public String addUser(String username, String password) {
    // generate a random salt
    String salt = UUID.randomUUID().toString();
    // hash the password with the salt using SHA-256
    String passwordHash = getHashSHA256(password, salt);
    Member member = new Member(username, passwordHash, salt);
    try {
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
    Member member = memberDao.findByUsername(username);
    if (member == null) {
        return null;
    }
    // hash the password with the salt using SHA-256
    String passwordHash = getHashSHA256(password, member.getSalt());
    if (!passwordHash.equals(member.getPasswordHash())) {
        return null;
    }
    return createSignInToken(member);
}

    private String createSignInToken(Member member) {
        return JWT.create()
            .withClaim("userId", member.getId())
            .withClaim("username", member.getUsername())
            .withIssuedAt(new Date())
            .sign(algorithm);
    }

    private String getHashSHA256(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((password + salt).getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger("SHA-256").log(Level.SEVERE, null, e);
            return null;
        }
    }
}
