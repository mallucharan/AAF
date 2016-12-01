/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service.api;

import static com.att.authz.layer.Result.OK;
import static com.att.cssa.rserv.HttpMethods.DELETE;
import static com.att.cssa.rserv.HttpMethods.GET;
import static com.att.cssa.rserv.HttpMethods.POST;
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

public class API_Delegate {
	public static void init(AuthAPI authzAPI, AuthzFacade facade) throws Exception {
		/**
		 * Add a delegate
		 */
		authzAPI.route(POST, "/authz/delegate",API.DELG_REQ,new Code(facade,"Add a Delegate", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.createDelegate(trans, req, resp);
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.CREATED_201); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});
		
		/**
		 * Update a delegate
		 */
		authzAPI.route(PUT, "/authz/delegate",API.DELG_REQ,new Code(facade,"Update a Delegate", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.updateDelegate(trans, req, resp);
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.OK_200); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});
		
		/**
		 * DELETE delegates for a user
		 */
		authzAPI.route(DELETE, "/authz/delegate",API.DELG_REQ,new Code(facade,"Delete delegates for a user", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.deleteDelegate(trans, req, resp);
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.OK_200); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});
		
		/**
		 * DELETE a delegate
		 */
		authzAPI.route(DELETE, "/authz/delegate/:user_name",API.VOID,new Code(facade,"Delete a Delegate", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.deleteDelegate(trans, pathParam(req, "user_name"));
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.OK_200); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});
		
		/**
		 * Read who is delegating for User
		 */
		authzAPI.route(GET, "/authz/delegates/user/:user",API.DELGS,new Code(facade,"Get Delegates by User", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.getDelegatesByUser(trans, pathParam(req, "user"), resp);
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.OK_200); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});

		/**
		 * Read for whom the User is delegating
		 */
		authzAPI.route(GET, "/authz/delegates/delegate/:delegate",API.DELGS,new Code(facade,"Get Delegates by Delegate", true) {

			@Override
			public void handle(AuthzTrans trans, HttpServletRequest req, HttpServletResponse resp) throws Exception {
				Result<Void> r = context.getDelegatesByDelegate(trans, pathParam(req, "delegate"), resp);
				switch(r.status) {
					case OK:
						resp.setStatus(HttpStatus.OK_200); 
						break;
					default:
						context.error(trans,resp,r);
				}				
			}			
		});

	}
}
