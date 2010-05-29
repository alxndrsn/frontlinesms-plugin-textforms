package net.frontlinesms.plugins.resourcemapper.data.domain.response;

import java.util.Date;

import javax.persistence.Entity;

import net.frontlinesms.data.domain.Message;
import net.frontlinesms.plugins.resourcemapper.data.domain.HospitalContact;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.PlainTextMapping;

@Entity
public class PlainTextResponse extends FieldResponse<PlainTextMapping> {

	public PlainTextResponse() {
		super();
	}

	public PlainTextResponse(Message message, HospitalContact submitter,
			Date dateSubmitted, String hospitalId, PlainTextMapping mapping) {
		super(message, submitter, dateSubmitted, hospitalId, mapping);
		// TODO Auto-generated constructor stub
	}

}
