package net.frontlinesms.plugins.surveys.data.domain.answers;

import java.util.Date;

import javax.persistence.Entity;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.surveys.data.domain.HospitalContact;
import net.frontlinesms.plugins.surveys.data.domain.questions.Question;
import net.frontlinesms.plugins.surveys.data.domain.questions.MultiChoiceQuestion;

@Entity
public class MultiChoiceAnswer extends CodedAnswer<MultiChoiceQuestion> {

	public MultiChoiceAnswer() {
		super();
	}

	public MultiChoiceAnswer(FrontlineMessage message, HospitalContact submitter, Date dateSubmitted, String hospitalId, MultiChoiceQuestion question) {
		super(message, submitter, dateSubmitted, hospitalId, question);
	}
	
	@Override
	public boolean isAnswerFor(Question question) {
		return question.getClass() == MultiChoiceQuestion.class;
	}
	
	@Override
	public String getAnswerValue() {
		String[] words = this.toWords(2);
		if (words != null && words.length == 1) {
			return toMultiChoiceString(words[0]);
		}
		else if (words != null && words.length == 2) {
			return toMultiChoiceString(words[1]);
		}
		return null;
	}
	
	private String toMultiChoiceString(String word) {
		if (this.isValidInteger(word)) {
			int index = Integer.parseInt(word);
			if (index > 0 && index <= this.question.getChoices().size()) {
				return this.question.getChoices().get(index - 1);
			}
		}
		else {
			for (String choice : this.question.getChoices()) {
				if (choice.equalsIgnoreCase(word)) {
					return choice;
				}
			}
		}
		return null;
	}
}