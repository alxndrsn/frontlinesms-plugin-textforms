package net.frontlinesms.plugins.resourcemapper.handler.fields;

import java.util.Collection;
import java.util.List;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.resourcemapper.ResourceMapperLogger;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.ChecklistField;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

/**
 * ChecklistHandler
 * @author dalezak
 *
 */
public class ChecklistHandler extends CodedHandler<ChecklistField> {
	
	private static final ResourceMapperLogger LOG = ResourceMapperLogger.getLogger(ChecklistHandler.class);	
	
	/**
	 * ChecklistField
	 */
	private static final ChecklistField checklistField = new ChecklistField();
	
	/**
	 * ChecklistHandler
	 */
	public ChecklistHandler() {}
	
	/**
	 * Get ChecklistField keywords
	 */
	@Override
	public Collection<String> getKeywords() {
		return this.fieldMappingDao.getKeywordsForField(checklistField);
	}
	
	@Override
	protected boolean isValidResponse(String[] words) {
		return words != null && words.length > 1 && areValidChoices(words[0], words[1]);
	}
	
	@Override
	public boolean shouldHandleCallbackMessage(FrontlineMessage message) {
		Field field = this.callbacks.get(message.getSenderMsisdn());
		if (field != null) {
			String[] words = this.toWords(message.getTextContent(), 2);
			return words != null && words.length == 1 && areValidChoices(field.getKeyword(), words[0]);
		}
		return false;
	}
	
	private boolean areValidChoices(String keyword, String words) {
		Field field = this.fieldMappingDao.getFieldForKeyword(keyword);
		if (field != null) {
			List<String> choices = field.getChoices();
			//TODO improve this if-else logic
			if (words.indexOf(",") > -1) {
				for (String answer : words.split(",")) {
					if (answer.indexOf("-") > -1) {
						for (String range : answer.split("-")) {
							LOG.debug("Range: %s", range);
							if (isValidInteger(choices, range)) {
								//valid integer choice
							}
							else if (isValidString(choices, range)) {
								//valid string choice
							}
							else {
								LOG.error("Invalid Range: %s", range);
								return false;
							}
						}
					}
					else if (isValidInteger(choices, answer)) {
						//valid integer choice
					}
					else if (isValidString(choices, answer)) {
						//valid string choice
					}
					else {
						LOG.error("Invalid Answer: %s", answer);
						return false;
					}
				}	
			}
			else if (words.indexOf("-") > -1) {
				for (String range : words.split("-")) {
					if (isValidInteger(choices, range)) {
						//valid integer choice
					}
					else if (isValidString(choices, range)) {
						//valid string choice
					}
					else {
						LOG.error("Invalid Range: %s", range);
						return false;
					}
				}
			}
			else if (isValidInteger(choices, words)) {
				//valid integer choice
			}
			else if (isValidString(choices, words)) {
				//valid string choice
			}	
			else {
				LOG.error("Invalid Answer: %s", words);
				return false;
			}
			return true;
		}
		return false;
	}
	
}
