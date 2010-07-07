package net.frontlinesms.plugins.resourcemapper.data.repository;

import java.util.List;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.CodedField;

public interface CodedMappingDao {

	public List<String> getShortCodes();
	
	public List<CodedField> getAllCodedMappings();
	/**
	 * Deletes a coded mapping from the system
	 * @param coded mapping the coded mapping to delete
	 */
	public void deleteCodedMapping(CodedField mapping);
	
	/**
	 * Saves a coded mapping to the system
	 * @param coded mapping the coded mapping to save
	 * @throws DuplicateKeyException if the mapping's short code is already in use 
	 */
	public void saveCodedMapping(CodedField mapping) throws DuplicateKeyException;
	
	/**
	 * Updates a coded mapping's details in the data source
	 * @param coded mapping the coded mapping whose details should be updated
	 * @throws DuplicateKeyException if the mapping's short code is already in use
	 */
	public void updateCodedMapping(CodedField mapping) throws DuplicateKeyException;
	
	/**
	 * Saves a coded mapping to the system without duplicate handling
	 * @param coded mapping the coded mapping to save
	 */
	public void saveCodedMappingWithoutDuplicateHandling(CodedField mapping);
	
	/**
	 * Updates a coded mapping's details in the data source without duplicate handling
	 * @param coded mapping the coded mapping whose details should be updated
	 */
	public void updateCodedMappingWithoutDuplicateHandling(CodedField mapping);
	
	/**
	 * Returns the mapping with the designated short code
	 * @param shortcode
	 * @return
	 */
	public CodedField getMappingForShortCode(String shortcode);
}
