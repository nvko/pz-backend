package pz.recipes.recipes.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private String jwtSecret = "secret";
    private int jwtExpirationInMs = 553600000; // every token lasts 1 hour (changed to more, TODO: switch back to 3600000)

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // sending username in token
                .setIssuedAt(new Date()) // issued date
                .setExpiration(expiryDate) // expiration date
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //encrypted signature key
                .compact();
    }

    String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parse(authToken);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
