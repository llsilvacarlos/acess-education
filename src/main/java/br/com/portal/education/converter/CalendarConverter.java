package br.com.portal.education.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.portal.education.util.MessageUtil;

@FacesConverter(value = "calendarConverter")
public class CalendarConverter extends BaseConverter implements Converter  {
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	Calendar calendar = null;
	Date convertedToDate;

	if (!value.isEmpty()) {
	    convertedToDate = convertToDate(context, (org.primefaces.component.calendar.Calendar) component, value);
	    calendar = Calendar.getInstance();
	    calendar.setTime(convertedToDate);
	}

	return calendar;
    }

    protected Date convertToDate(FacesContext context, org.primefaces.component.calendar.Calendar pfCalendarComponent, String submittedValue) {
	Locale locale = pfCalendarComponent.calculateLocale(context);
	SimpleDateFormat format = new SimpleDateFormat(pfCalendarComponent.getPattern(), locale);
	format.setTimeZone(pfCalendarComponent.calculateTimeZone());

	try {
	    return format.parse(submittedValue);
	} catch (ParseException e) {
	    
	    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, getStringMessage("error_converter_date",null),null));
	}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	String convertedValue = null;

	if (value != null) {
	    convertedValue = convertToString(context, (org.primefaces.component.calendar.Calendar) component, value);
	}

	return convertedValue;
    }

    protected String convertToString(FacesContext context, org.primefaces.component.calendar.Calendar pfCalendarComponent, Object value) {
	SimpleDateFormat dateFormat = new SimpleDateFormat(pfCalendarComponent.getPattern(), pfCalendarComponent.calculateLocale(context));

	dateFormat.setTimeZone(pfCalendarComponent.calculateTimeZone());

	return dateFormat.format(((Calendar) value).getTime());
    }

}
