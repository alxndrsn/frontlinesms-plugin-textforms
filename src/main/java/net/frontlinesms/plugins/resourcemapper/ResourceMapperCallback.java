package net.frontlinesms.plugins.resourcemapper;

import net.frontlinesms.plugins.resourcemapper.data.domain.HospitalContact;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

/*
 * ResourcesMapperCallback
 * @author Dale Zak
 * 
 * see {@link "http://www.frontlinesms.net"} for more details. 
 * copyright owned by Kiwanja.net
 */
public interface ResourceMapperCallback {
	public void viewResponses(HospitalContact contact);
	public void viewResponses(Field field);
	public void refreshContact(HospitalContact contact);
	public void refreshField(Field field);
}