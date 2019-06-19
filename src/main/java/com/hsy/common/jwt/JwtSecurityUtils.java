/**
 * 
 */
package com.hsy.common.jwt;

import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.hsy.common.constant.CommonConstant;
import com.hsy.common.utils.DateUtils;
import com.hsy.common.utils.MD5Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author 张梓枫
 * @date 2019年4月28日上午10:00:47
 * @desc 根据jwt生成用户token
 */
public final class JwtSecurityUtils {

    /**
     * @author 张梓枫
     * @param id  用户id
     * @param unique 用户唯一编码(登录名)
     * @param ttlMillis 过期时间，传0为永久有效
     * @return String
     * @throws Exception 
     * @desc  生成用户token
     */
	public static String createToken(String id, String unique, String subject, long ttlMillis) {

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去
		SecretKey key = generalKey();
        
        //设置签名签发时间
		Date now = DateUtils.nowDate();
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setIssuer(unique)
				.setSubject(subject).signWith(SignatureAlgorithm.HS512, key);
        
		// 设置前面过期时间,如果等于0,则永久有效
        if (ttlMillis > 0) {
			Date exp = DateUtils.add(now, ttlMillis, ChronoUnit.MILLIS);
            builder.setExpiration(exp);
        }else {
            Date exp = DateUtils.add(DateUtils.nowDate(), 100, ChronoUnit.YEARS);
            builder.setExpiration(exp);
        }
		return builder.compact();
    }

    /**
     * @author 张梓枫
     * @param secretkey
     * @return
     * @return SecretKey
     * @throws Exception 
     * @desc   创建密钥
     */
	private static SecretKey generalKey() {
		String stringKey = MD5Utils.encrypt(CommonConstant.SECRET_KEY);

        byte[] encodedKey = Base64.getDecoder().decode(stringKey);

		SecretKey key = new SecretKeySpec(encodedKey, SignatureAlgorithm.HS512.getJcaName());

        return key;
    }
    
    /**
     * @author 张梓枫
     * @param token
     * @return
     * @return Claims
     * @throws Exception 
     * @desc   解析token，得到用户信息
     */
	public static Claims parseToken(String token) {
		SecretKey key = generalKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
    
    /**
	 * @author 张梓枫
	 * @param token
	 * @return
	 * @return boolean
	 * @throws Exception
	 * @desc 判断token是否已失效
	 */
	public static boolean isExpiration(String token) {
		Claims claims = parseToken(token);
        return claims.getExpiration().before(DateUtils.nowDate());
    }
}
