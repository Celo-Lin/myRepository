package com.juan.adx.manage.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.juan.adx.model.entity.permission.BrowserSession;


public class RequestWrapper extends HttpServletRequestWrapper {

	private static final Set<String> CONTEXT_PARAMETERS = new HashSet<String>();

	private final BrowserSession browserSession;
	
	private Map<String, String[]> params = null;

	static {
		CONTEXT_PARAMETERS.add( SessionKey.USER_ID.toLowerCase() );
		CONTEXT_PARAMETERS.add( SessionKey.USER_NAME.toLowerCase() );
		CONTEXT_PARAMETERS.add( SessionKey.ROLE_IDS.toLowerCase() );
	}

	public RequestWrapper( HttpServletRequest request, BrowserSession browserSession ) {
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
		} else if(SessionKey.ROLE_IDS.equalsIgnoreCase( name )) {
			return this.getRoleIds();
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
        nameList.add(SessionKey.ROLE_IDS);
        return nameList.elements();
    }

	private String[] getUserId() {
		String uid = browserSession.getUserId();
		return this.toArray( uid );
	}
	
	private String[] getUserName() {
		String uid = browserSession.getUserName();
		return this.toArray( uid );
	}
	
	private String[] getRoleIds() {
		String roleIds = browserSession.getRoleIds();
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
