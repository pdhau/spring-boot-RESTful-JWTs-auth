package com.pdhau.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.pdhau.model.Role;
import com.pdhau.model.User;
import com.pdhau.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

	@Autowired
	@Qualifier("userDetailsService")
	UserService userDetailsService;

	private static final long serialVersionUID = 1L;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getRoleFromToken(String token) {
		Jws<Claims> claims;
		List<Map<String, String>> scopes = null;
		try {
			claims = Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token);
			scopes = (List<Map<String, String>>) claims.getBody().get("scopes");
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return scopes;
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = null;
		try {
			claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(User user) {
		return doGenerateToken(user);
	}

	private String doGenerateToken(User user) {
		List<Role> roles = userDetailsService.getRoles(user);
		List<SimpleGrantedAuthority> scopes = new ArrayList<>();
		for (Role role : roles) {
			scopes.add(new SimpleGrantedAuthority(role.getName()));
		}
		String subject = user.getUsername();
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("scopes", scopes);

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
				.signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY).compact();
	}

	/**
	 * this function will check token: check username and expired time
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
