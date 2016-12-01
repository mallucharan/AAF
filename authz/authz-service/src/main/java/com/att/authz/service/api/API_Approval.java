/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service.api;

import static com.att.cssa.rserv.HttpMethods.GET;
import static com.att.cssa.rserv.HttpMethods.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.att.aft.dme2.internal.jetty.http.HttpStatus;
import com.att.authz.env.AuthzTrans;
import com.att.authz.facade.AuthzFacade;
import com.att.authz.layer.Result;
import com.att.authz.service.AuthAPI;
import com.att.authz.service.Code;
import com.att.authz.service.mapper.Mapper.API;

public class API_Approval {
	// Hide Public Constructor
	private API_Approval() {}
	
	public static void init(AuthAPI authzAPI, AuthzFacade facade) throws Exception {

		/**
		 * Get Approvals by User
		 */
		authzAPI.route(GET, "/authz/approval/user/:user",API.APPROVALS,
				new Code(facade,"Get Approvals by User", true) {
			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.getApprovalsByUser(trans, resp, pathParam(req,"user"));
				if(r.isOK()) {
					resp.setStatus(HttpStatus.OK_200); 
				} else {
					context.error(trans,resp,r);
				}				
			}			
		});

		/**
		 * Get Approvals by Ticket
		 */
		authzAPI.route(GET, "/authz/approval/ticket/:ticket",API.VOID,new Code(facade,"Get Approvals by Ticket ", true) {
			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.getApprovalsByTicket(trans, resp, pathParam(req,"ticket"));
				if(r.isOK()) {
					resp.setStatus(HttpStatus.OK_200);
				} else {
					context.error(trans,resp,r);
				}				
			}			
		});

		/**
		 * Get Approvals by Approver
		 */
		authzAPI.route(GET, "/authz/approval/approver/:approver",API.APPROVALS,new Code(facade,"Get Approvals by Approver", true) {
			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.getApprovalsByApprover(trans, resp, pathParam(req,"approver"));
				if(r.isOK()) {
					resp.setStatus(HttpStatus.OK_200);
				} else {
						context.error(trans,resp,r);
				}				
			}			
		});


		/**
		 * Update an approval
		 */
		authzAPI.route(PUT, "/authz/approval",API.APPROVALS,new Code(facade,"Update approvals", true) {
			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.updateApproval(trans, req, resp);
				if(r.isOK()) {
					resp.setStatus(HttpStatus.OK_200);
				} else {
					context.error(trans,resp,r);
				}				
			}			
		});
	}
}
