package net.frontlinesms.plugins.resourcemapper.data.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

public interface FieldMappingDao {

	/**
	 * Get all fields
	 * @return
	 */
	public List<Field> getAllFieldMappings();
	
	/**
	 * Deletes a field mapping from the system
	 * @param mapping the field mapping to delete
	 */
	public void deleteFieldMapping(Field field);
	
	/**
	 * Saves a field mapping to the system
	 * @param mapping the field mapping to save
	 * @throws DuplicateKeyException if the field mapping's phone number is already in use by another plaintext mapping 
	 */
	public void saveFieldMapping(Field field) throws DuplicateKeyException;
	
	/**
	 * Updates a field mapping's details in the data source
	 * @param mapping the field mapping whose details should be updated
	 * @throws DuplicateKeyException if the field mapping's phone number is already in use by another plaintext mapping
	 */
	public void updateFieldMapping(Field field) throws DuplicateKeyException;
	
	/**
	 * Saves a field mapping to the system
	 * @param mapping the field mapping to save
	 * @throws DuplicateKeyException if the field mapping's phone number is already in use by another plaintext mapping 
	 */
	public void saveFieldMappingWithoutDuplicateHandling(Field field);
	
	/**
	 * Updates a field mapping's details in the data source
	 * @param mapping the field mapping whose details should be updated
	 * @throws DuplicateKeyException if the field mapping's phone number is already in use by another plaintext mapping
	 */
	public void updateFieldMappingWithoutDuplicateHandling(Field field);
	
	public List<String> getAbbreviations();
	
	public Field getFieldForAbbreviation(String abbrev);
	
//	/**
//	 * Returns the mapping with the designated short code
//	 * @param shortcode
//	 * @return
//	 */
//	public Field getMappingForShortCode(String shortcode);
//	
//	public List<String> getShortCodes();
//	
//	public List<String> getAllShortCodes();
//	
//	public Field searchAllMappingsForForCode(String shortcode);
	
}
