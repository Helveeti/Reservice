package com.varausjarjestelma.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

/**
 * Tool for international String selection.
 * 
 * @author O. Närhi
 * 
 */
public final class I18n {

	/** The current selected Locale. */
	private static final ObjectProperty<Locale> locale;

	/**
	 * A constant for the Finnish locale.
	 */
	public static final Locale FINNISH = new Locale("fi");

	static {
		locale = new SimpleObjectProperty<Locale>(getDefaultLocale());
		locale.addListener(new ChangeListener<Locale>() {

			@Override
			public void changed(ObservableValue<? extends Locale> observable, Locale oldValue, Locale newValue) {
				Locale.setDefault(newValue);
			}

		});

	}

	/**
	 * Provides a list of supported locales.
	 * 
	 * @return list of locales
	 */
	public static List<Locale> getSupportedLocales() {
		return new ArrayList<Locale>(Arrays.asList(Locale.ENGLISH, FINNISH));
	}

	/**
	 * Get the default locale. If not set, get English locale.
	 * 
	 * @return the default locale
	 */
	public static Locale getDefaultLocale() {
		Locale sysDefault = Locale.getDefault();
		return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
	}

	/**
	 * Provides the currently selected locale.
	 * 
	 * @return selected locale
	 */
	public static Locale getLocale() {
		return locale.get();
	}

	/**
	 * Updates the default locale.
	 * 
	 * @param locale
	 */
	public static void setLocale(Locale locale) {
		localeProperty().set(locale);
		Locale.setDefault(locale);
	}

	/**
	 * Provides the current selected locale as a ObjectProperty type object.
	 * 
	 * @return locale as ObjectProperty<Locale>
	 */
	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	/**
	 * Provides the string with the given key from the resource bundle for the
	 * current locale and uses it as first argument to MessageFormat.format, passing
	 * in the optional args and returning the result.
	 *
	 * @param key  message key
	 * @param args optional arguments for the message
	 * @return localized formatted string
	 */
	public static String get(final String key, final Object... args) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.varausjarjestelma.I18n.Resources", getLocale());
		return MessageFormat.format(bundle.getString(key), args);
	}

	/**
	 * Creates a String binding to a localized String for the given message bundle
	 * key.
	 *
	 * @param key key
	 * @return String binding
	 */
	public static StringBinding createStringBinding(final String key, final Object... args) {
		return Bindings.createStringBinding(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return get(key, args);
			}

		}, locale);
	}

	/**
	 * Creates a button, whose text is picked from a properties file based on 'key'.
	 * 
	 * @param key
	 * @param args
	 * @return a new button
	 */
	public static Button buttonForKey(final String key, final Object... args) {
		Button button = new Button();
		button.textProperty().bind(createStringBinding(key, args));
		return button;
	}

	/**
	 * Creates a tooltip based on the given key.
	 * 
	 * @param key
	 * @param args
	 * @return a new tooltip
	 */
	public static Tooltip tooltipForKey(final String key, final Object... args) {
		Tooltip tooltip = new Tooltip();
		tooltip.textProperty().bind(createStringBinding(key, args));
		return tooltip;
	}

	/**
	 * Creates a label, whose text is picked from properties using 'key'.
	 * 
	 * @param key
	 * @param args
	 * @return a new text label
	 */
	public static Label stringForLabel(final String key, final Object... args) {
		Label label = new Label();
		label.textProperty().bind(createStringBinding(key, args));
		return label;
	}

	/**
	 * Creates a text object, whose text is the property matching 'key'.
	 * 
	 * @param key
	 * @param args
	 * @return a new text object
	 */
	public static Text stringForText(final String key, final Object... args) {
		Text text = new Text();
		text.textProperty().bind(createStringBinding(key, args));
		return text;
	}

}
