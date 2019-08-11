package net.moewes;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier to mark Vaadin service implementations.
 *
 * Qualified CDI beans implementing {@link com.vaadin.flow.i18n.I18NProvider},
 * and {@link com.vaadin.flow.di.Instantiator} interfaces are loaded.
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface VaadinServiceEnabled {
}