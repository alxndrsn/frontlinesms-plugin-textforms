package net.frontlinesms.plugins.resourcemapper.search;

import java.util.List;

import net.frontlinesms.plugins.resourcemapper.ui.components.PagedAdvancedTableController;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

/**
 * A base class for generating HQL search queries
 * Has utilities for paging, query running, and performance monitoring
 * @author Dieterich Lawson
 *
 */
public abstract class QueryGenerator {

	protected String previousQuery;
	
	//paging stuff
	/**
	 * The total number of results in the result set
	 * starts counting from 1
	 */
	protected int totalResults;
	/**
	 * The current page in the result set
	 * starts counting from 0
	 */
	protected int currentPage;
	/**
	 * total pages in the result set
	 * starts counting from 1
	 * calculated by taking totalResults/pageSize
	 */
	protected int totalPages;
	/**
	 * the size of the pages
	 */
	protected int pageSize;
	
	//Hibernate session objects
	protected SessionFactory sessionFactory;
	protected Session session;
	
	/**
	 * the table for results
	 */
	protected PagedAdvancedTableController resultsTable;
	
	public abstract void startSearch(String value);
	public abstract void setSort(int column, boolean ascending);
	
	public QueryGenerator(ApplicationContext appCon, PagedAdvancedTableController resultsTable){
		sessionFactory = (SessionFactory) appCon.getBean("sessionFactory");
		this.resultsTable = resultsTable;
		pageSize=30;
		totalPages=0;
		totalResults=0;
		currentPage=0;
		previousQuery="";
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		if(currentPage < totalPages){
			this.currentPage = currentPage;
		}
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalResults() {
		return totalResults;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void nextPage(){
		if(currentPage < (totalPages - 1)){
			currentPage++;
			refresh();
		}
	}
	
	public void previousPage(){
		if(currentPage > 0){
			currentPage--;
			refresh();
		}
	}
	
	public int getFirstResultOnPage(){
		return currentPage * pageSize + 1;
	}
	
	public int getLastResultOnPage(){
		if(currentPage < totalPages-1){
			return currentPage * pageSize + pageSize;
		}else{
			return totalResults;
		}
	}
	
	public boolean hasNextPage(){
		return currentPage < (totalPages-1);
	}
	
	public boolean hasPreviousPage(){
		return currentPage > 0;
	}
	
	public void refresh(){
		runQuery(previousQuery);
	}
	
	public void resetPaging(){
		currentPage=0;
	}
	
	/**
	 * Takes an already-constructed HQL query and runs it, handling the pagination
	 * and performance measurement
	 * @param query The query to be run
	 */
	protected void runQuery(String query){
		System.out.println(query);
		//check if session is active
		if(session == null){
			session = sessionFactory.openSession();
			session.beginTransaction();
		}
		//construct the count query
		String querySuffix = query.substring(query.indexOf("from"));
		String countQuery = "select count(*) " + querySuffix;
		//run the count query, obtaining the total number of results
		long countPrevTime = System.nanoTime();
		totalResults = ((Long) session.createQuery(countQuery).list().get(0)).intValue();
		long countElapsedTime = System.nanoTime() - countPrevTime;
		System.out.println("Count Time: " + countElapsedTime/1000000.0);
		
		if(totalResults % pageSize == 0){
			totalPages = totalResults / pageSize;
		}else{
			totalPages = (totalResults / pageSize) +1;
		}
		//set up the time measurement
		long prevTime = System.nanoTime();
		//run the query
		List results  = session.createQuery(query).setFirstResult(currentPage * pageSize).setMaxResults(pageSize).list();
		//output time elapsed
		long elapsedTime = System.nanoTime() - prevTime;
		System.out.println("Query Time: " + elapsedTime/1000000.0);
		previousQuery = query;
		resultsTable.setResults(results);
	}
	
}
