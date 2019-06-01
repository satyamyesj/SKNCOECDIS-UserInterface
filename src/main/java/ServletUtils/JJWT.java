package ServletUtils;

import DatabaseAccessObjects.QueryObjects.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author windows
 */
public class JJWT {

    String secret = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    private Map<String, Object> getClaimsMap(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.username);
        claims.put("first_name", user.first_name);
        claims.put("last_name", user.last_name);
        claims.put("email", user.email);
        claims.put("mobile_no", user.mobile_no);
        claims.put("verification_status", user.verification_status);
        claims.put("user_role", user.user_role);
        return claims;
    }

    //Method to construct a JWT
    public String createJWT(String subject, User user, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);

        //Siginig key specifications secret bytes & algorithm to be used
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        Map<String, Object> claims = getClaimsMap(user);
        JwtBuilder builder = Jwts.builder().setIssuedAt(now)
                .setSubject(subject)
                .addClaims(claims)
                .signWith(signingKey, signatureAlgorithm);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

//Method to validate and read the JWT
    public User parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(jwt).getBody();
            Date expirationDate = claims.getExpiration();
            long nowMillis = System.currentTimeMillis();
            Date currentDate = new Date(nowMillis);
            User user = new User();
            if (currentDate.compareTo(expirationDate) == -1) {
                user.username = (String) claims.get("username");
                user.first_name = (String) claims.get("first_name");
                user.last_name = (String) claims.get("last_name");
                user.email = (String) claims.get("email");
                user.mobile_no = (String) claims.get("mobile_no");
                user.verification_status = (int) claims.get("verification_status");
                user.user_role = (String) claims.get("user_role");
                return user;
            } else {
                //TODO handle incorrect jwt
                return null;
            }
        } catch (JwtException ex) {
            return null;
        }
    }

}
