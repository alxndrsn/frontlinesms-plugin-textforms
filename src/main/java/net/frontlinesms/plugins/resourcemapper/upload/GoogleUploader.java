package net.frontlinesms.plugins.resourcemapper.upload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import net.frontlinesms.plugins.resourcemapper.ResourceMapperConstants;
import net.frontlinesms.plugins.resourcemapper.ResourceMapperLogger;
import net.frontlinesms.plugins.resourcemapper.data.domain.response.FieldResponse;
import net.frontlinesms.ui.i18n.InternationalisationUtils;

/**
 * Upload Google XML Document
 * @author dalezak
 *
 */
@SuppressWarnings("unchecked")
public class GoogleUploader extends DocumentUploader {
	
	private static final ResourceMapperLogger LOG = ResourceMapperLogger.getLogger(GoogleUploader.class);
	
	/**
	 * Atom Namespace
	 */
	private static final Namespace NAMESPACE_ATOM = new Namespace("atom", "http://www.w3.org/2005/Atom");
	
	/**
	 * Status Namespace
	 */
	private static final Namespace NAMESPACE_REPORT = new Namespace("report", "http://schemas.google.com/report/2010");
	
	/**
	 * GS Namespace
	 */
	private static final Namespace NAMESPACE_GS = new Namespace("gs", "http://schemas.google.com/spreadsheets/2006");
	
	/**
	 * GoogleUploader
	 */
	public GoogleUploader() { }
	
	/**
	 * Generate Google XML document for uploading
	 */
	@Override
	public String toString() {
		DocumentFactory factory = DocumentFactory.getInstance();
		Document document = factory.createDocument();
		
		//root
		Element rootElement = document.addElement("entry");
		rootElement.addNamespace("", NAMESPACE_ATOM.getURI());
		rootElement.addNamespace(NAMESPACE_REPORT.getPrefix(), NAMESPACE_REPORT.getURI());
		rootElement.addNamespace(NAMESPACE_GS.getPrefix(), NAMESPACE_GS.getURI());
		
		//author
		Element authorElement = rootElement.addElement("author");
		Element uriElement = authorElement.addElement("uri");
		if (this.phoneNumber != null) {
			if (this.phoneNumber.startsWith("+")) {
				uriElement.setText(String.format("tel:%s", this.phoneNumber));
			}
			else {
				uriElement.setText(String.format("tel:+%s", this.phoneNumber));
			}
		}
		
		//subject
		Element subjectElement = rootElement.addElement(new QName("subject", NAMESPACE_REPORT));
		if (this.hospitalId != null) {
			subjectElement.setText(this.hospitalId);
		}
		
		//observed
		Element observedElement = rootElement.addElement(new QName("observed", NAMESPACE_REPORT));
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-2"));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		observedElement.setText(dateFormat.format(now.getTime()));
		
		//add responses
		Element contentElement = rootElement.addElement(new QName("content", NAMESPACE_REPORT));
		Element rowElement = contentElement.addElement(new QName("row", NAMESPACE_REPORT));
		for (FieldResponse fieldResponse : this.getFieldResponses()) {
			String schema = fieldResponse.getMappingSchema();
			if (schema != null && schema.length() > 0) {
				String responseValue = fieldResponse.getResponseValue();
				if (responseValue != null) {
					Element entry = rowElement.addElement(new QName("field", NAMESPACE_GS));
					entry.addAttribute("name", schema);
					entry.setText(responseValue);
				}
				else {
					LOG.error("ResponseValue is NULL for %s", schema);
				}
			}
		}
		return document.asXML();
	}
	
	@Override
	public String getTitle() {
		return InternationalisationUtils.getI18NString(ResourceMapperConstants.DOCUMENT_UPLOAD_GOOGLE);
	}
	
	@Override
	public String getPanelXML() {
		return "/ui/plugins/resourcemapper/upload/GoogleUploader.xml";
	}

	/**
	 * Get ContentType
	 */
	@Override
	public String getContentType() {
		return "text/xml";
	}
}