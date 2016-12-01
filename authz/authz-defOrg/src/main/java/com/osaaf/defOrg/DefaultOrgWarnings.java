package com.osaaf.defOrg;

import com.att.authz.org.EmailWarnings;

public class DefaultOrgWarnings implements EmailWarnings {

	@Override
    public long credEmailInterval()
    {
        return 604800000L; // 7 days in millis 1000 * 86400 * 7
    }
    
	@Override
    public long roleEmailInterval()
    {
        return 604800000L; // 7 days in millis 1000 * 86400 * 7
    }
	
	@Override
	public long apprEmailInterval() {
        return 259200000L; // 3 days in millis 1000 * 86400 * 3
	}
    
	@Override
    public long  credExpirationWarning()
    {
        return( 2592000000L ); // One month, in milliseconds 1000 * 86400 * 30  in milliseconds
    }
    
	@Override
    public long roleExpirationWarning()
    {
        return( 2592000000L ); // One month, in milliseconds 1000 * 86400 * 30  in milliseconds
    }

	@Override
    public long emailUrgentWarning()
    {
        return( 1209600000L ); // Two weeks, in milliseconds 1000 * 86400 * 14  in milliseconds
    }

}
