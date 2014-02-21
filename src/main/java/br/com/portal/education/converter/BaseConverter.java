package br.com.portal.education.converter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class BaseConverter {

    protected String getStringMessage(String key, String... params) {
	ResourceBundle bundle = ResourceBundle.getBundle("messages");
	return MessageFormat.format(bundle.getString(key), params);

    }

}
