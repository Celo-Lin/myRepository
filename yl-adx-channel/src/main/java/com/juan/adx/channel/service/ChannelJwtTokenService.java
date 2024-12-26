package com.juan.adx.channel.service;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.juan.adx.channel.config.ChannelParameterConfig;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.constants.ChannelJwtClaimsKey;
import com.juan.adx.model.entity.sspmanage.ChannelTokenPayload;

@Service
public class ChannelJwtTokenService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	/* JWT的签发者	*/
	private static final String ISSUER = "auth0";
	
	
	public String getAccessToken(ChannelTokenPayload payload) {
		long currTime = System.currentTimeMillis();
		long expiresTime = 24l * 60l * 60l * 1000l * ChannelParameterConfig.channelTokenExpiresDay;
		Date expiresAt = new Date(currTime + expiresTime);
		payload.setTokenCode(this.generateTokenCode(payload.getUserId()));
		this.saveTokenCode(payload);
		return this.getToken(payload, currTime, expiresAt, null);
	}
	
	
    private String getToken(ChannelTokenPayload payload, long currTime, Date expiresAt, Date notBefore){
        String token = null;
        try {
            token = JWT.create()
                .withIssuer(ISSUER)						//代表这个JWT的签发者
                //负载(Payload)声明
                .withClaim(ChannelJwtClaimsKey.userName, payload.getUserName())
                .withClaim(ChannelJwtClaimsKey.userId, String.valueOf(payload.getUserId()))
                .withClaim(ChannelJwtClaimsKey.sspPartnerId, String.valueOf(payload.getSspPartnerId()))
                .withClaim(ChannelJwtClaimsKey.tokenCode, payload.getTokenCode())
                //.withJWTId(jwtId)						//jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
                .withSubject(payload.getUserId().toString())	//代表这个JWT的主体，即它的所有人
                //.withAudience(audience)					代表这个JWT的接收对象
                .withIssuedAt(new Date(currTime))		//签发时间
                .withExpiresAt(expiresAt)				//过期时间
                .withNotBefore(notBefore == null ? new Date() : notBefore)			//定义在指定时间之前，该jwt都是不可用的
                .sign(Algorithm.HMAC512(ChannelParameterConfig.channelTokenAlgSecretKey));		//使用了HMAC加密算法, SECRET是用来加密数字签名的密钥。
           
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return token;
    }
    
    /**
     * 	生成用户 Token 校验码
     */
    public String generateTokenCode(Integer uid) {
		String radomStr = RandomStringUtils.randomAlphanumeric(8);
		String tokenCode = String.format("%s%d%d", radomStr, uid, System.nanoTime());
		//String tokenCode = MD5Util.getMD5String(codeStr);
		return tokenCode;
	}
    
	/**
	 * 获取token校验码
	 */
	public String getTokenCode(String userId) {
		String key = RedisKeyUtil.getChannelTokenCodeKey(userId);
		return this.redisTemplate.STRINGS.get(key);
	}

	/**
	 * 保存token校验码
	 */
	public void saveTokenCode(ChannelTokenPayload payload) {
		String key = RedisKeyUtil.getChannelTokenCodeKey(String.valueOf(payload.getUserId()));
		this.redisTemplate.STRINGS.setEx(key, RedisKeyExpireTime.DAY_7, payload.getTokenCode());
	}
	
	/**
	 * 删除token校验码
	 */
	public void deleteTokenCode(String userId) {
		String key = RedisKeyUtil.getChannelTokenCodeKey(userId);
		this.redisTemplate.KEYS.del(key);
	}
    
    public DecodedJWT deAccessToken(final String token) {
    	return deToken(token);
    }
    
    
    /**
     * 	先验证token是否被伪造，然后解码token。
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    private DecodedJWT deToken(final String token) {
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC512加密算法, SECRET是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(ChannelParameterConfig.channelTokenAlgSecretKey)).withIssuer(ISSUER).build(); 
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }
        return jwt;
    }


	/**
	 * 校验token code
	 */
	public boolean compareTokenCode(DecodedJWT decodedJWT) {
		//获取 token 的唯一校验码
		Claim tokenCodeClaim = decodedJWT.getClaim(ChannelJwtClaimsKey.tokenCode);
    	String uid = decodedJWT.getSubject();
    	if(tokenCodeClaim.isNull() || StringUtils.isBlank(uid)) {
    		return false;
    	}
    	String tokenCode = tokenCodeClaim.asString();
		if(tokenCode == null || tokenCode.isEmpty()) {
			return false;
		}
		//检查token校验码与服务端是否一致
		String localTokenCode = this.getTokenCode(uid);
		return tokenCode.equals(localTokenCode);
	}

}
