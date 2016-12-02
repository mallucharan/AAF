/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cass;

import com.att.authz.layer.Result;




/**
 * Add additional Behavior for Specific Applications for Results
 * 
 * In this case, we add additional BitField information accessible by
 * method (
 *
 * @param <RV>
 */
public class Status<RV> extends Result<RV> {
	
	// 
    public final static int ERR_NsNotFound = Result.ERR_General+1,
    						ERR_RoleNotFound = Result.ERR_General+2,
    						ERR_PermissionNotFound = Result.ERR_General+3, 
    						ERR_UserNotFound = Result.ERR_General+4,
    						ERR_UserRoleNotFound = Result.ERR_General+5,
    						ERR_DelegateNotFound = Result.ERR_General+6,
    						ERR_InvalidDelegate = Result.ERR_General+7,
    						ERR_DependencyExists = Result.ERR_General+8,
    						ERR_NoApprovals = Result.ERR_General+9,
    						ACC_Now = Result.ERR_General+10,
    						ACC_Future = Result.ERR_General+11,
    						ERR_ChoiceNeeded = Result.ERR_General+12,
    						ERR_FutureNotRequested = Result.ERR_General+13;
  
	/**
     * Constructor for Result set. 
     * @param data
     * @param status
     */
    private Status(RV value, int status, String details, String[] variables ) {
    	super(value,status,details,variables);
    }

	public static String name(int status) {
		switch(status) {
			case OK: return "OK";
			case ERR_NsNotFound: return "ERR_NsNotFound";
			case ERR_RoleNotFound: return "ERR_RoleNotFound";
			case ERR_PermissionNotFound: return "ERR_PermissionNotFound"; 
			case ERR_UserNotFound: return "ERR_UserNotFound";
			case ERR_UserRoleNotFound: return "ERR_UserRoleNotFound";
			case ERR_DelegateNotFound: return "ERR_DelegateNotFound";
			case ERR_InvalidDelegate: return "ERR_InvalidDelegate";
			case ERR_ConflictAlreadyExists: return "ERR_ConflictAlreadyExists";
			case ERR_DependencyExists: return "ERR_DependencyExists";
			case ERR_ActionNotCompleted: return "ERR_ActionNotCompleted";
			case ERR_Denied: return "ERR_Denied";
			case ERR_Policy: return "ERR_Policy";
			case ERR_BadData: return "ERR_BadData";
			case ERR_NotImplemented: return "ERR_NotImplemented";
			case ERR_NotFound: return "ERR_NotFound";
			case ERR_ChoiceNeeded: return "ERR_ChoiceNeeded";
		}
		//case ERR_General:   or unknown... 
		return "ERR_General";
	}
    
}
