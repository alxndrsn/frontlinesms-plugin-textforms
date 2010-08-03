package net.frontlinesms.plugins.resourcemapper.handler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.resourcemapper.ResourceMapperLogger;
import net.frontlinesms.plugins.resourcemapper.ResourceMapperMessages;
import net.frontlinesms.plugins.resourcemapper.ResourceMapperProperties;
import net.frontlinesms.plugins.resourcemapper.data.repository.FieldMappingDao;
import net.frontlinesms.plugins.resourcemapper.data.domain.mapping.Field;

import org.springframework.context.ApplicationContext;

/**
 * InfoHandler
 * @author dalezak
 *
 */
public class InfoHandler extends MessageHandler {
	
	private static final ResourceMapperLogger LOG = ResourceMapperLogger.getLogger(InfoHandler.class);
	
	/**
	 * FieldMappingDao
	 */
	protected FieldMappingDao fieldMappingDao;
	
	/**
	 * InfoHandler
	 */
	public InfoHandler() {}
	
	/**
	 * Set ApplicationContext
	 * @param appContext appContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext appContext) { 
		this.fieldMappingDao = (FieldMappingDao) appContext.getBean("fieldMappingDao");
	}
	
	/**
	 * Get Info keywords
	 */
	@Override
	public Collection<String> getKeywords() {
		return Arrays.asList(ResourceMapperProperties.getInfoKeywords());
	}
	
	/**
	 * Handle Info message
	 */
	@Override
	public void handleMessage(FrontlineMessage message) {
		LOG.debug("handleMessage: %s", message.getTextContent());
		String[] words = this.toWords(message.getTextContent(), 2);
		if (words.length == 2) {
			Field field = this.fieldMappingDao.getFieldForKeyword(words[1]);
			if (field != null) {
				StringBuilder reply = new StringBuilder(field.getName());
				reply.append(" (");
				reply.append(field.getTypeLabel());
				reply.append(")");
				if (field.getInfoSnippet() != null && field.getInfoSnippet().length() > 0) {
					reply.append(" ");
					reply.append(field.getInfoSnippet());	
				}
				sendReply(message.getSenderMsisdn(), reply.toString(), false);
			}
			else {
				sendReply(message.getSenderMsisdn(), ResourceMapperMessages.getHandlerInvalidKeyword(getAllKeywords()), true);
			}		
		}
		else {
			sendReply(message.getSenderMsisdn(), ResourceMapperMessages.getHandlerHelp(getAllKeywords()), false);
		}
	}
	
	/**
	 * Get all keywords for all Fields
	 * @return
	 */
	private String [] getAllKeywords() {
		List<String> keywords = this.fieldMappingDao.getKeywords();
		return keywords.toArray(new String[keywords.size()]);
	}

}
