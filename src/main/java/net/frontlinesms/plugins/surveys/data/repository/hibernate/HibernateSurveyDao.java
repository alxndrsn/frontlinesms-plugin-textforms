package net.frontlinesms.plugins.surveys.data.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.repository.hibernate.BaseHibernateDao;
import net.frontlinesms.plugins.surveys.SurveysLogger;
import net.frontlinesms.plugins.surveys.data.domain.Survey;
import net.frontlinesms.plugins.surveys.data.repository.SurveyDao;

/**
 * HibernateSurveyDao
 * @author dalezak
 *
 */
public class HibernateSurveyDao extends BaseHibernateDao<Survey> implements SurveyDao {

	@SuppressWarnings("unused")
	private static final SurveysLogger LOG = SurveysLogger.getLogger(HibernateSurveyDao.class);
	
	protected HibernateSurveyDao() {
		super(Survey.class);
	}

	public List<Survey> getAllSurveys() {
		return super.getAll();
	}
	
	public List<Survey> getAllSurveys(int startIndex, int limit) {
		return super.getAll(startIndex, limit);
	}

	public void deleteSurvey(Survey survey) {
		super.delete(survey);
	}
	
	public void saveSurvey(Survey survey) throws DuplicateKeyException {
		super.save(survey);
	}

	public void saveSurveyWithoutDuplicateHandling(Survey survey) {
		super.saveWithoutDuplicateHandling(survey);
	}

	public void updateSurvey(Survey survey) throws DuplicateKeyException {
		super.update(survey);
	}

	public void updateSurveyWithoutDuplicateHandling(Survey survey) {
		super.updateWithoutDuplicateHandling(survey);
	}
	
	public List<String> getKeywords() {
		List<String> keywords = new ArrayList<String>();
		for (Survey survey : super.getAll()) {
			keywords.add(survey.getKeyword());
		}
		return keywords;
	}

	public Survey getSurveyByKeyword(String keyword) {
		DetachedCriteria criteria = super.getCriterion();
		criteria.add(Restrictions.eq(Survey.FIELD_KEYWORD, keyword));
		return super.getUnique(criteria);		
	}
}