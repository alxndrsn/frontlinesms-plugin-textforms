package net.frontlinesms.plugins.textforms.search;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.frontlinesms.plugins.textforms.TextFormsLogger;
import net.frontlinesms.plugins.textforms.search.QueryGenerator;
import net.frontlinesms.plugins.textforms.ui.components.PagedAdvancedTableController;

import org.springframework.context.ApplicationContext;

import com.ibm.icu.util.Calendar;

/**
 * AnswerQueryGenerator
 * @author dalezak
 *
 */
public class AnswerQueryGenerator extends QueryGenerator {

	private static final TextFormsLogger LOG = TextFormsLogger.getLogger(AnswerQueryGenerator.class);
	
	public AnswerQueryGenerator(ApplicationContext appCon, PagedAdvancedTableController resultsTable) {
		super(appCon, resultsTable);
	}

	@Override
	public void startSearch(String text) {
		super.runQuery(getSearchQuery(text, null, false, null, null));
	}
	
	@Override
	public void startSearch(String text, String sortColumn, boolean sortAscending) {
		super.runQuery(getSearchQuery(text, sortColumn, sortAscending, null, null));
	}

	public void startSearch(String text, String sortColumn, boolean sortAscending, String date, String phoneNumber) {
		super.runQuery(getSearchQuery(text, sortColumn, sortAscending,  date, phoneNumber));
	}
	
	private String getSearchQuery(String text, String sortColumn, boolean sortAscending, String dateString, String phoneNumber) {
		LOG.debug("text=%s date=%S contact=%s", text, dateString, phoneNumber);
		String query = "SELECT fr FROM Answer fr";
		query += " WHERE (lower(fr.organizationId) LIKE lower('%"+text+"%')";
		query += " OR lower(fr.question.name) LIKE lower('%"+text+"%')";
		query += " OR lower(fr.question.keyword) LIKE lower('%"+text+"%'))";
		if (phoneNumber != null && phoneNumber.length() > 0) {
			query += " AND lower(fr.contact.phoneNumber) LIKE lower('%"+phoneNumber+"%')";
		}
		if (dateString != null && dateString.length() > 0) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date = dateFormat.parse(dateString);
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(date);
				startDate.set(Calendar.HOUR_OF_DAY, 0);
				query += String.format(" AND fr.dateSubmitted >= %d ", startDate.getTimeInMillis());
				
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(date);
				endDate.set(Calendar.HOUR_OF_DAY, 23);
				endDate.set(Calendar.MINUTE, 59);
				query += String.format(" AND fr.dateSubmitted <= %d ", endDate.getTimeInMillis());
			} 
			catch (Exception e) {
				//do nothing
			}
		}
		if (sortColumn != null && sortColumn.length() > 0) {
			if (sortAscending) {
				query += String.format(" ORDER BY fr.%s ASC", sortColumn);
			}
			else {
				query += String.format(" ORDER BY fr.%s DESC", sortColumn);
			}
		}
		return query;
	}

}
