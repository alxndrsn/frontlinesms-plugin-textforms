package net.frontlinesms.plugins.resourcemapper.data.domain.response;

import java.util.Date;

import javax.persistence.Entity;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.resourcemapper.data.domain.HospitalContact;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.ChecklistField;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

@Entity
public class ChecklistResponse extends CodedResponse<ChecklistField> {

	public ChecklistResponse() {
		super();
	}

	public ChecklistResponse(FrontlineMessage message, HospitalContact submitter, Date dateSubmitted, String hospitalId, ChecklistField mapping) {
		super(message, submitter, dateSubmitted, hospitalId, mapping);
	}
	
	@Override
	public boolean isResponseFor(Field field) {
		return field.getClass() == ChecklistField.class;
	}
}
