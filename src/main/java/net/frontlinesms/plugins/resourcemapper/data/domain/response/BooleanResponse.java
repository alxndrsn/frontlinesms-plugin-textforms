package net.frontlinesms.plugins.resourcemapper.data.domain.response;

import java.util.Date;

import javax.persistence.Entity;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.resourcemapper.data.domain.HospitalContact;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.BooleanField;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

@Entity
public class BooleanResponse extends FieldResponse<BooleanField> {

	public BooleanResponse() {
		super();
	}

	public BooleanResponse(FrontlineMessage message, HospitalContact submitter,
			Date dateSubmitted, String hospitalId, BooleanField mapping) {
		super(message, submitter, dateSubmitted, hospitalId, mapping);
	}
	
	@Override
	public boolean isResponseFor(Field field) {
		return field.getClass() == BooleanField.class;
	}
}
