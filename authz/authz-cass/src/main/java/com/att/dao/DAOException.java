/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1527904125585539823L;

//    // TODO -   enum in result class == is our intended design, currently the DAO layer does not use Result<RV> so we still use these for now
//    public final static DAOException RoleNotFoundDAOException = new DAOException("RoleNotFound");
//    public final static DAOException PermissionNotFoundDAOException = new DAOException("PermissionNotFound");
//    public final static DAOException UserNotFoundDAOException = new DAOException("UserNotFound");

    public DAOException() {
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
