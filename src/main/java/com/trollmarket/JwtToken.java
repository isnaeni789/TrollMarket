package com.trollmarket;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtToken {

    /*Minimal 32 character/Ibaratnya password application 3rd party*/
    private final String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";
    /*Ibaratnya username application 3rd party*/
    private final String AUDIENCE = "TrollMarketWebUI";

    /*Mendapatkan isi payload/claims dari token*/
    private Claims getClaims(String token){
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY); /*Meng-convert secret jadi JWTParser*/
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token); /*memverified String token, sekaligus mengekstrak signature dan claims*/
        Claims claims = signatureAndClaims.getBody(); /*Ambil claims/payloadnya saja*/
        return claims;
    }

    public String getUsername(String token){
        try {
            Claims claims = getClaims(token);
            return claims.get("username", String.class);
        } catch (Exception exception){
            return null;
        }
    }

    /*Memvalidasi apakah token ini benar atau tidak*/
    public Boolean validateToken(String token, UserDetails userDetails){
        Claims claims = getClaims(token);
        /*Mendapatkan username*/
        String username = claims.get("username", String.class);
        /*Mendapatkan audience*/
        String audience = claims.getAudience();
        /*Jika username & audience matched, return true*/
        return (username.equals(userDetails.getUsername()) && audience.equals(AUDIENCE));
    }

    /*Method yang digunakan untuk mebuat token dari hasil yang diterima oleh request 3rd party
    * application dan user*/
    public String generateToken(String username, String secretKey,
                                String audience, String subject){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(subject)
                .claim("username", username)
                .setIssuer("http://localhost:8020")
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

}
