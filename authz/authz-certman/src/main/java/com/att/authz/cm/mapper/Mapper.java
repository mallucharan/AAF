/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.mapper;

import java.io.IOException;
import java.util.List;

import com.att.authz.cm.data.CertDrop;
import com.att.authz.cm.data.CertRenew;
import com.att.authz.cm.data.CertReq;
import com.att.authz.cm.data.CertResp;
import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.dao.aaf.cass.ArtiDAO;

public interface Mapper<REQ,CERT,ARTIFACTS,ERROR>
{
	public enum API{ERROR,VOID,CERT,CERT_REQ,CERT_RENEW,CERT_DROP,ARTIFACTS};
	
	public Class<?> getClass(API api);
	public<A> A newInstance(API api);

	public ERROR errorFromMessage(StringBuilder holder, String msgID, String text, String... detail);
	
	public Result<CERT> toCert(AuthzTrans trans, Result<CertResp> in, String[] trustChain) throws IOException;
	public Result<CertReq> toReq(AuthzTrans trans, REQ req);
	public Result<CertRenew> toRenew(AuthzTrans trans, REQ req);
	public Result<CertDrop>  toDrop(AuthzTrans trans, REQ req);
	
	public List<ArtiDAO.Data> toArtifact(AuthzTrans trans, ARTIFACTS arti);
	public Result<ARTIFACTS> fromArtifacts(Result<List<ArtiDAO.Data>> readArtifactsByMachine);
}
