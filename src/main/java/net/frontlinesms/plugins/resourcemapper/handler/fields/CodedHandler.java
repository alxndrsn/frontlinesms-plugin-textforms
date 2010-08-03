package net.frontlinesms.plugins.resourcemapper.handler.fields;

import java.util.List;

import net.frontlinesms.plugins.resourcemapper.ResourceMapperLogger;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.CodedField;

/**
 * CodedHandler
 * @author dalezak
 *
 * @param <M> CodedField
 */
public abstract class CodedHandler<M extends CodedField> extends CallbackHandler<M> {

	@SuppressWarnings("unused")
	private static final ResourceMapperLogger LOG = ResourceMapperLogger.getLogger(CodedHandler.class);
	
	/**
	 * CodedHandler
	 */
	public CodedHandler() {}
	
	/**
	 * Is this a valid integer range?
	 * @param choices possible choices
	 * @param answer answer
	 * @return true if valid
	 */
	protected boolean isValidInteger(List<String> choices, String answer) {
		if (answer != null && isValidInteger(answer.trim())) {
			int value = Integer.parseInt(answer.trim());
			if (value > 0 && value <= choices.size()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Is this a valid string?
	 * @param choices possible choices
	 * @param answer answer
	 * @return true if valid
	 */
	protected boolean isValidString(List<String> choices, String answer) {
		if (choices != null && choices.size() > 0 && answer != null && answer.length() > 0) {
			String answerTrimmed = answer.trim();
			for (String choice : choices) {
				//TODO improve fuzzy string comparison logic
				if (choice.equalsIgnoreCase(answerTrimmed) || 
					choice.toLowerCase().startsWith(answerTrimmed.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
