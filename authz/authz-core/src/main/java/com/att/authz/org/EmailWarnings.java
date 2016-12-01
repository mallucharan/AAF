/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.org;

public interface EmailWarnings
{
    public long credExpirationWarning();
    public long roleExpirationWarning();
    public long credEmailInterval();
    public long roleEmailInterval();
    public long apprEmailInterval();
    public long emailUrgentWarning();

}
