package net.frontlinesms.plugins.resourcemapper.data.domain.mapping;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import net.frontlinesms.plugins.resourcemapper.ResourceMapperConstants;
import net.frontlinesms.ui.i18n.InternationalisationUtils;

/**
 * A field that stores plain text only
 * @author dieterichlawson
 *
 */
@Entity
@DiscriminatorValue(value="plaintext")
public class PlainTextField extends Field {

	public PlainTextField() {
		super(null, null);
	}

	public PlainTextField(String fullName, String abbreviation) {
		super(fullName, abbreviation);
	}
	
	@Override
	public void setChoices(Set<String> choices) {
		//do nothing for PlainTextField
	}
	
	@Override
	public Set<String> getChoices() {
		//return nothing for PlainTextField
		return null;
	}
	
	@Override
	public String getType() {
		return "plaintext";
	}
	
	@Override
	public String getTypeLabel() {
		return InternationalisationUtils.getI18NString(ResourceMapperConstants.TYPE_PLAIN_TEXT);
	}

	@Override
	public String[] getAdditionalInstructions() {
		return null;
	}

	@Override
	public String getPathToElement() {
		return null;
	}
}
