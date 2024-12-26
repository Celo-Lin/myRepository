package com.juan.adx.common.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.juan.adx.model.enums.OsType;

public class UserAgentUtils {
	
    private static final ThreadLocal<Pattern> androidOsVersionPattern = ThreadLocal.withInitial(() ->
        Pattern.compile("Android ([0-9._]+)", Pattern.CASE_INSENSITIVE)
    );
    
    private static final ThreadLocal<Pattern> iosOsVersionPattern = ThreadLocal.withInitial(() ->
    	Pattern.compile("iPhone OS ([0-9_]+)", Pattern.CASE_INSENSITIVE)
    );
    
    
    public static String parseOsVersion(String userAgent, OsType systemType) {
    	return OsType.IOS  == systemType ? parseIOSVersion(userAgent) : parseAndroidVersion(userAgent); 
    }
    
    public static String parseAndroidVersion(String userAgent) {
    	if(userAgent == null || userAgent.isEmpty()) {
    		return "N/A";
    	}
        Matcher matcher = androidOsVersionPattern.get().matcher(userAgent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "N/A";
    }

    public static String parseIOSVersion(String userAgent) {
    	if(userAgent == null || userAgent.isEmpty()) {
    		return "N/A";
    	}
        Matcher matcher = iosOsVersionPattern.get().matcher(userAgent);
        if (matcher.find()) {
            String iosVersionWithUnderscores = matcher.group(1);
            return iosVersionWithUnderscores.replace("_", ".");
        }
        return "N/A";
    }
    
    public static void main(String[] args) {
		String ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148";
		System.out.println(parseIOSVersion(ua));
	}


    public static boolean osVerisonEqualstoUa(String os_version, String userAgent, OsType systemType){
        String uaRegStr = null;
        if(systemType == OsType.ANDROID){
            Matcher matcher = androidOsVersionPattern.get().matcher(userAgent);
            try {
                if (matcher.find()) {
                    uaRegStr = matcher.group(0);
                    String[] uaStr = uaRegStr.split(" ");
                    Integer numSt = os_version.length() - uaStr[1].length();
                    if(numSt<0){ //出现版本长度不一致的情况，补齐再比较
                        for (int i=0;i<Math.abs(numSt)/2;i++)
                            os_version = os_version.concat(".0");
                    }else if(numSt>0){
                        for (int i=0;i<Math.abs(numSt)/2;i++)
                            uaStr[1] = uaStr[1].concat(".0");
                    }
                    if(!Objects.equals(os_version,uaStr[1])){
                        return false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(systemType == OsType.IOS){
            Matcher matcher =  androidOsVersionPattern.get().matcher(userAgent);
            try {
                if (matcher.find()) {
                    uaRegStr = matcher.group(0);
                    String[] uaStr = uaRegStr.split(" ");
                    String iosVersion = uaStr[2].replace("_",".");
                    if(!Objects.equals(os_version,iosVersion)){
                        return false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;

    }

}
