package net.moewes.quarkus.vaadin;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * Qualifier to mark Vaadin service implementations.
 *
 * Qualified CDI beans implementing {@link com.vaadin.flow.i18n.I18NProvider}, and {@link
 * com.vaadin.flow.di.Instantiator} interfaces are loaded.
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface VaadinServiceEnabled {

}