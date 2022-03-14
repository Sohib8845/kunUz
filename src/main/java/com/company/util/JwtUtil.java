package com.company.util;

import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.exeptions.ForbiddenException;
import com.company.exeptions.UnauthorizedException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static  String secretKey="kalitso'z";
    public static String createJwt(Integer id, ProfileRole role){
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)));
        jwtBuilder.setIssuer("kun.uz");
        jwtBuilder.claim("role",role.name());
        String jwt=jwtBuilder.compact();
        return jwt;
    }

    public static String createJwt(Integer id){
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256,secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)));
        jwtBuilder.setIssuer("kun.uz");
        String jwt=jwtBuilder.compact();
        return jwt;
    }

    public static ProfileJwtDTO decodeJwt(String jwt){
        JwtParser jwtParser=Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws=jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        String id=claims.getSubject();
        ProfileRole role = ProfileRole.valueOf((String) claims.get("role"));

        return new ProfileJwtDTO(Integer.parseInt(id),role);
    }

    public static Integer decodeJwtId(String jwt){
        JwtParser jwtParser=Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws=jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        String id=claims.getSubject();

        return (Integer.parseInt(id));
    }

    public static ProfileJwtDTO checkProfile(HttpServletRequest request, ProfileRole requiredRole){
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if(jwtDTO==null){
            throw new UnauthorizedException("Not authorized");
        }
        if(!jwtDTO.getRole().equals(requiredRole)){
            throw new ForbiddenException("Forbidden exp");
        }

        return jwtDTO;
    }

    public static ProfileJwtDTO checkProfile(HttpServletRequest request, ProfileRole... requiredRole){
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if(jwtDTO==null){
            throw new UnauthorizedException("Not authorized");
        }
        ProfileRole []roles=requiredRole;
        boolean b=true;
        for(ProfileRole p:roles){
            if(jwtDTO.getRole().equals(p)){
                b=false;
            }
        }
        if(b) {
            throw new ForbiddenException("Forbidden exp");
        }
        return jwtDTO;
    }

    public static ProfileJwtDTO getProfile(HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return jwtDTO;
    }
}
