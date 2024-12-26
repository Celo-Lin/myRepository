package com.juan.adx.channel.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.juan.adx.model.entity.sspmanage.ChannelBrowserSession;


public class RequestWrapper extends HttpServletRequestWrapper {

	private static final Set<String> CONTEXT_PARAMETERS = new HashSet<String>();

	private final ChannelBrowserSession browserSession;
	
	private Map<String, String[]> params = null;

	static {
		CONTEXT_PARAMETERS.add( SessionKey.USER_ID.toLowerCase() );
		CONTEXT_PARAMETERS.add( SessionKey.USER_NAME.toLowerCase() );
		CONTEXT_PARAMETERS.add( SessionKey.SSP_PARTNER_ID.toLowerCase() );
	}

	public RequestWrapper( HttpServletRequest request, ChannelBrowserSession browserSession ) {
		super( request );
		this.browserSession = browserSession;
		this.params = new HashMap<String, String[]>(request.getParameterMap());
	}

	@Override
	public String getParameter( String name ) {
		String[] values = this.getParameterValues( name );
		if( null == values ) {
			return null;
		} else {
			return values[ 0 ];
		}
	}
	
	@Override
    public Map<String,String[]> getParameterMap(){
        return params;
    }
    
	@Override
	public String[] getParameterValues( String name ) {
		if( null == name ) {
			return null;
		}
		String lowerCaseName = name.toLowerCase();
		if( !CONTEXT_PARAMETERS.contains( lowerCaseName ) ) {
			return super.getParameterValues( name );
		}

		if(SessionKey.USER_ID.equalsIgnoreCase( name )) {
			return this.getUserId();
		} else if(SessionKey.USER_NAME.equalsIgnoreCase( name )) {
			return this.getUserName();
		} else if(SessionKey.SSP_PARTNER_ID.equalsIgnoreCase( name )) {
			return this.getSspPartnerId();
		} else {
			return super.getParameterValues( name );
		}
	}
	
	
	@Override
    public Enumeration<String> getParameterNames(){
		Vector<String> nameList = new Vector<String>();
        for(Entry<String,String[]> entry: params.entrySet()){
            nameList.add(entry.getKey());
        }
        nameList.add(SessionKey.USER_ID);
        nameList.add(SessionKey.USER_NAME);
        nameList.add(SessionKey.SSP_PARTNER_ID);
        return nameList.elements();
    }

	private String[] getUserId() {
		String uid = browserSession.getSession_key_user_id();
		return this.toArray( uid );
	}
	
	private String[] getUserName() {
		String userName = browserSession.getSession_key_user_name();
		return this.toArray( userName );
	}
	
	private String[] getSspPartnerId() {
		String roleIds = browserSession.getSession_key_ssp_partner_id();
		return this.toArray( roleIds );
	}

	
	private String[] toArray( String content ) {
		if( null == content ) {
			return null;
		} else {
			return new String[] { content };
		}
	}

}
