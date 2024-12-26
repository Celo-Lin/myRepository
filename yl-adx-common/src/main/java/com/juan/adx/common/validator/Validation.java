package com.juan.adx.common.validator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validation {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(Validation.class);
	
	/**
     * 验证整数的正则式
     */
    private static final String P_INT = "^\\d+$";
    /**
     * 验证浮点数的正则式
     */
    private static final String P_FLOAT = "^\\d+(\\.\\d+){0,1}$";
    /**
     * 验证手机号码的正则式
     * 该正则目前支持 13、14、15、17、18 开头的号码，基本能支持目前市面上所有的手机号码
     * 目前移动、联通、电信三大运营商的手机号段大致如下： 
     * 移动号段有 134,135,136,137,138,139,147,150,151,152,157,158,159,178,182,183,184,187,188。 
     * 联通号段有 130，131，132，155，156，185，186，145，176
     * 电信号段有 133，153，177，180，181，189
     */
    //private static final String P_PHONE = "^\\d+(-\\d+)*$";
    private static final String P_MOBILE_PHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|6|7|8])\\d{8}$";
    
    /**
     * 验证固定电话的正则式
     */
    private static final String P_TELEPHONE = "^[0][1-9]{2,3}-[0-9]{5,10}$";
    /**
     * 验证 e-mail 的正则式
     */
    private static final String P_EMAIL = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
    
    /**
     * 验证账号
     */
    private static final String P_ACCOUNT = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=]){2,20}$";
    
    /**
     * 验证密码正则
     * 6-20 位，只允许是字母、数字、指定字符：^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=]){6,20}$
     * 
     * 中英文、数字、减号或下划线： ^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$
     * 6-20 位，字母+数字+指定字符：^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()+=]).{6,20}$
     * 
     * "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$"
     * "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$"
     */
    private static final String P_PASSWD =  "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()+=]).{6,20}$";
    
    /**
     * 验证昵称 中英文、数字、减号或下划线
     * “.”(点符号)匹配的是除了换行符“\n”以外的所有字符： "^(.*)$"
     */
    public static final String P_NICK_NAME ="^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{2,50}$";

	/**
	 * 验证昵称  只能为英文字母、数字、下滑线或减号
	 */
	public static final String E_NICK_NAME = "^[-_a-zA-Z0-9-]{2,20}$";
    
    /**
     * 验证是否为整数
     */
    public static final int INT = 1;
    /**
     * 验证是否为浮点数
     */
    public static final int FLOAT = 2;
    /**
     * 验证是否为手机号码
     */
    public static final int MOBILE_PHONE = 3;
    /**
     * 验证是否为 e-mail
     */
    public static final int EMAIL = 4;
    /**
     * 验证密码格式是否正确
     */
    public static final int PASSWD = 5;
    
    /**
     * 验证昵称
     */
    public static final int NICK_NAME = 7;
    
    /**
     * 验证固定电话
     */
    public static final int TELEPHONE = 8;

	/**
	 * 验证英文昵称
	 */
	public static final int EN_NICK_NAME = 9;
	
	/**
	 * 验证英文昵称
	 */
	public static final int ACCOUNT = 10;
    

    /**
     * 对字符串进行验证
     * @param input 需要验证的字符串
     * @param matcher 验证规则
     * @return 验证是否通过
     */
    public static boolean validate(String input, int matcher) {
        if (!isNotNULL(input)) {
            return false;
        }
        String regex = null;
        switch (matcher) {
            case INT:
                regex = P_INT;
                break;
            case FLOAT:
                regex = P_FLOAT;
                break;
            case MOBILE_PHONE:
                regex = P_MOBILE_PHONE;
                break;
            case TELEPHONE:
            	regex = P_TELEPHONE;
            	break;
            case EMAIL:
                regex = P_EMAIL;
                break;
            case PASSWD:
            	regex = P_PASSWD;
            	break;
            case NICK_NAME:
            	regex = P_NICK_NAME;
            	break;
			case EN_NICK_NAME:
				regex = E_NICK_NAME;
				break;
			case ACCOUNT:
				regex = P_ACCOUNT;
				break;
            default:
                return false;
        }
        return Pattern.matches(regex, input);
    }
    
	public static String toString(Object value){
	
		if( !isNotNULL( value ) )
			return "";
		else
			return value.toString().trim();
	}
	
	public static String isNbsp(Object value){
		if(!isNotNULL(value)){
			return "&nbsp;";
		}else{
			return value.toString();
		}
	}

	
	public static Boolean isNotNULL(Object ...value){
		
		if(null == value || value.length < 1)
			return false;
		else
			for(int i = 0;i<value.length;i++){
				if( null == value[i] || "".equals( value[i].toString() ) || "null".equals(value[i].toString().toLowerCase()) ){
					return false;
				}
			}
			return true;
	}
	
	/**
	 * 
	 * @param format  "" ##.##   ,###.##  ,000.00  0.00  ,###  ,000
	 * @param value  数字
	 * @return
	 */
	public static String toNumber(String format ,Object value){
		if( !isNotNULL( value ) )
			return "";
		
//			return new DecimalFormat( format ).format(Double.parseDouble( value.toString() ));
		DecimalFormat   df   =   new   DecimalFormat( format ); 
   	 	StringBuffer   sb   =   new   StringBuffer(); 
   	 	df.format(new BigDecimal(value.toString()),   sb,   new   FieldPosition(0)); 
   	 	return sb.toString();
		
	}
	
	public static Boolean equals(Object o,Object x){
		if(!isNotNULL( o ) || !isNotNULL( x )){
			return false;
		}

		if(o == x || o.equals( x ) || o.toString().equals( x )){
			return true;
		}
		return false;
	}
 
	
	public static Float toFloat(Object value){
		if( !isNotNULL( value ) )
			return 0.0f;
		else
			return Float.parseFloat(value.toString());
	}
	
	public static Boolean isBoolean(Object value){
		
		if( !isNotNULL( value ) )
			return false;
		else
			if ( value.toString().equals("0") || value.toString().equalsIgnoreCase("false")) 
				return false;
			else				
				return true;
	}
	
	public static Integer toInteger(Object value){
		
		if( !isNotNULL( value ) )
			return 0;
		else
			return Integer.parseInt( value.toString() );
	}
	
	public static Long toLong(Object value){
		
		if( !isNotNULL( value ) )
			return 0l;
		else
			return Long.parseLong( value.toString() );
	}
	
	public static Double toDouble(Object value){
		
		if( !isNotNULL( value ) )
			return 0.0d;
		else
			return Double.parseDouble( value.toString() );
	}
	
	public static Boolean toBoolean(Object value){
		if( !isNotNULL( value ) )
			return false;
		else
			return Boolean.parseBoolean( value.toString() );
	}
	
	 /**
     * 取消字符串中的空字符
     * @param 取消空字符
     * @return 取消了空字符的字符串
     */
    public static String toTrim(String str)
    {
    	String s = "";
    	if(null!=str)
    	{
    	s = str.trim();
    	}
    	return s;
    }
	
	 /**
     * 检查字符串是否为日期
     * @author RASCAL
     */
    public static boolean isDate(Object value)
    {
        return isDateFormat(value, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
    }
    
    /**
     * 检查字符串是否为日期时间类型
     * @author RASCAL
     */
    public static boolean isDateTime(Object value)
    {
        return isDateFormat(value, "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    }
    
    /**
     * 检查字符串是否为指定的日期类型
     * @param fromatReg 格式正则表达式
     * @author RASCAL
     */
    public static boolean isDateFormat(Object value, String fromatReg)
    {
        Pattern pattern = Pattern.compile(fromatReg);
        if ( !isNotNULL(value) || !pattern.matcher(value.toString()).matches())
        	return false;
        else
        	return true;
    }
    
    /**
     * 解析日期类型，格式"yyyyMMdd"
     * @author RASCAL
     */
    public static Date toDate(Object value)
    {
        return parseDate(value, "yyyy-MM-dd");
    }


    /**
     * 解析日期时间类型，格式"yyyyMMdd hh:mm:ss"
     * @author RASCAL
     */
    public static Date toDateTime(Object value)
    {
        return parseDate(value, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 解析日期类型
     * @param fromat 日期格式
     * @author RASCAL
     */
    public static Date parseDate(Object value, String fromat)
    {
        //解析日期格式
        try
        {
            DateFormat df =  new SimpleDateFormat(fromat);
            return df.parse(value.toString());
        }
        catch (Exception ex)
        {
            LOGGER.warn(("TimeConvertException:: "+value+"\r\nfromat::"+fromat + "\r\nerror info::"+ex.getMessage()), ex);
        }
        return null;
    }
    
    /**
     * 
     * @param format 日期格式
     * @return 当日日期
     * @author RASCAL
     */
    public static String toDay(String format){
    	if ( !isNotNULL(format))
    		return new SimpleDateFormat(format).format(new Date());
    	else
    		return new SimpleDateFormat(format).format(new Date());
    }
    
    
    
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    //生成 32 位的 id
    public static String getCode(int mnum){
    	char strRandom[] = { 
    			'a','b','c','d','e','f','g','h','i','j','k','l','m',
    			'n','o','p','q','r','s','t','u','v','w','x','y','z',
    			'A','B','C','D','E','F','G','H','I','J','K','L','M',
    			'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    			'0','1','2','3','4','5','6','7','8','9'
		};
    	
//    	 建立字符串数组
		StringBuffer finalStr = new StringBuffer();
		for (int count = 0; count < mnum; count++) {
			int randomNum =   new Random().nextInt(62); 
			// 随机字符串数组中字符的位置
			finalStr.append(strRandom[randomNum]);

		}
		
		return finalStr.toString();
    }
    
    public static String[][] toArray(String x) {
		String[] xx = x.split("=!=");
		String [][] xxx= new String[xx.length][];
		for(int i = 0;i<xx.length;i++){
			xxx[i]= xx[i].split("-!-");
			
		}
		return xxx;
	}
     

	public static Object[] toByte(Object value) {
		
		return null;
	}

	public static Object[] toTime(Object value) {
		
		return null;
	}
}