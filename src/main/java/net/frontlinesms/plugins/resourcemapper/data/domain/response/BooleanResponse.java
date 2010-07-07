package net.frontlinesms.plugins.resourcemapper.data.domain.response;

import java.util.Date;

import javax.persistence.Entity;

import net.frontlinesms.data.domain.Message;
import net.frontlinesms.plugins.resourcemapper.data.domain.HospitalContact;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.BooleanField;

@Entity
public class BooleanResponse extends FieldResponse<BooleanField> {

	public BooleanResponse() {
		super();
	}

	public BooleanResponse(Message message, HospitalContact submitter,
			Date dateSubmitted, String hospitalId, BooleanField mapping) {
		super(message, submitter, dateSubmitted, hospitalId, mapping);
	}

}
