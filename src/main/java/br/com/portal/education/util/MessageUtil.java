package br.com.portal.education.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class MessageUtil {
    
    private ResourceBundle resource;

    private static Logger logger = Logger.getLogger(MessageUtil.class);

    @PostConstruct
    public void init() {
	resource = ResourceBundle.getBundle("messages");
    }

    public String getMessage(String key) {
	String config = null;
	try {
	    config = resource.getString(key);

	} catch (MissingResourceException e) {
	    logger.error("Error ao carregar arquivo de configuracoes");
	}
	return config;

    }

    public String getMessage(String key, Object... param) {
	String msg = null;
	try {
	    msg = MessageFormat.format(resource.getString(key), param);

	} catch (MissingResourceException e) {
	    logger.error("Error ao carregar arquivo de configuracoes");
	}
	return msg;

    }
}
