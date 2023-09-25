package com.example.backend.service.impl;

import com.example.backend.dto.response.AuthenticationResponseDTO;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
// in order to manipulate jwt token extract, validate .. we need to include new dependency jjwt api ..
@Service
public class JWTService {

    private static final String SECRET_KEY = "jEjgDsULQeGqdtZmldVPFqjtXiQnZtR0Pzz9d5RkKHqAfNkkimNWCD/pYecJ3IHL";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // getSubject :user name or user email

    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()                 // allows to set various configurations for the parser
                .setSigningKey(getSignInKey())   // generate or decode a token + also we need sign alorism specified
                .build()
                // once the object is build
                .parseClaimsJws(token)   //returns a Jws<Claims>
                .getBody();
    }

    public String generateToken( UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);

    }
     // token genration user details + extraClams
    public String generateToken(
            Map<String, Object> extraClams // contains the extra clams that we want to add :
            // is the one for example want to path authorities , any information i want to store on my token
            , UserDetails userDetails) {

//         i can add whet ever i wand on extra came here ..............
        List<String> roles = new ArrayList<>();
        Map<String, Object> rolesClaim = new HashMap<>();
        userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
        rolesClaim.put("roles", roles);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        Date expirationDate = calendar.getTime();
//        System.currentTimeMillis() + 1000 * 60 * 72
        return Jwts
                .builder()
                .setClaims(extraClams)
                .setSubject(userDetails.getUsername())     // getSubject :user name or user email
                .setIssuedAt(new Date(System.currentTimeMillis()))  // when this claim was created :
        // and this info help us to calculate the expiration date or
        // to check if the token is vaild or not
                .setExpiration(new Date( System.currentTimeMillis() + 1000 * 60 * 72)) // expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // witch key u want to use to sign this token
                 .addClaims(rolesClaim)
                .compact();            // is the one will generate and return the token

    }
/// Unauthorized Exception
//  if (user.isPresent()){
//        var jwtToken = jwtService.generateToken(user.get());
//        return AuthenticationResponseDTO.builder()
//                // just for testing
//                .userId(user.get().getId())
////               .role(user.getRoles().name())
//                .token(jwtToken)
//                .build();
//    }else
//            throw  new handleUnauthorizedException("Invalid email or password ");


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean isValidToken (String token , UserDetails userDetails){
        final String username = extractUsername(token);   // email
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return expirationDate(token).before(new Date());
    }

    private Date expirationDate(String token){
        // extract the expiration date form  extractClaim
        return extractClaim(token , Claims::getExpiration);
    }



}








/* -> JWT : json web token = is a compact url safe means of representing clams to be transferred between 2 party's
// -> Clams in jwt : are encoded as json object that is digitally assigned using web signature
--------------
JWT consists of 3 parts
1- Header  : 1.1 type of token "typ" which is JWT , the assign alorism is been used "alg" : "H256"
2-Payload = data : which contain the clams (are statement about entity and additional data , metadata to data
                                       3. types of clams : registered , public and private
3- Signature :used to verify the center of jwt  and unsure the message didn't change along the way
 */