package com.joinsoft.demo.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JJWTUtils {
	
	private static Map<String, String> passwords = new HashMap<String, String>(){
		{
			put("ieslab", "balsei");
			put("wiztek", "ketziw");
			put("joinsoft", "tfosnioj");
		}
	};

    public JJWTUtils() {
        
    }
	/**
	 * 生成SecretKey
	 * @param secret
	 * @return
	 */
	private static SecretKey generateKey(String secret) {
		byte[] encodedKey = Base64.decodeBase64(secret);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	/**
	 * 新生成token
	 *
	 * @param appid
	 * @param secret
	 * @param exp
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String createToken(String appid, String secret, Integer exp) throws IOException {
		Claims claims = new DefaultClaims();
		claims.setSubject(appid);
		// milliseconds是毫秒  1000毫秒=1秒
		claims.setExpiration(new Date(System.currentTimeMillis() + exp*1000));
		String compactJws = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, generateKey(secret))
				.compact();
		return compactJws;
	}
	
	/**
	 * 解析token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String token, String appid) throws ExpiredJwtException {
		Claims claims = Jwts.parser()
				.setSigningKey(generateKey(passwords.get(appid)))
				.parseClaimsJws(token).getBody();
		return claims;
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String token = JJWTUtils.createToken("ieslab", "balsei", 30*60);
		System.out.println(token);
		Thread.sleep(5000);
		Claims claims = JJWTUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpZXNsYWIiLCJleHAiOjE1NjEzNTQ1MDF9.CqMvcolpUMpdHOI8SuoHtnh2Ua_XZkh2uKPuusrj7gg", "ieslab");
		System.out.println(claims.getSubject());
	}
	
}
