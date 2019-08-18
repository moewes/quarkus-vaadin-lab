package net.moewes.quarkus.vaadin;

import static net.moewes.quarkus.vaadin.BeanLookup.SERVICE;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.di.DefaultInstantiator;
import com.vaadin.flow.di.Instantiator;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Unmanaged;
import javax.inject.Inject;

/**
 * Default CDI instantiator.
 * <p>
 * Can be overridden by a @{@link VaadinServiceEnabled} CDI Alternative/Specializes, or can be
 * customized with a Decorator.
 *
 * @see Instantiator
 */
//@VaadinServiceScoped
@ApplicationScoped
@VaadinServiceEnabled
public class CdiInstantiator implements Instantiator {

  private static final String CANNOT_USE_CDI_BEANS_FOR_I18N = "Cannot use CDI beans for I18N, falling back to the default behavior.";
  private static final String FALLING_BACK_TO_DEFAULT_INSTANTIATION = "Falling back to default instantiation.";

  private AtomicBoolean i18NLoggingEnabled = new AtomicBoolean(true);
  private DefaultInstantiator delegate;
  @Inject
  private BeanManager beanManager;

  @Override
  public boolean init(VaadinService service) {
    delegate = new DefaultInstantiator(service);
    return delegate.init(service)
        && service instanceof QuarkusVaadinServletService;
  }

  @Override
  public <T> T getOrCreate(Class<T> type) {
    return new BeanLookup<>(beanManager, type)
        .setUnsatisfiedHandler(() -> getLogger().info(
            "'{}' is not a CDI bean. "
                + FALLING_BACK_TO_DEFAULT_INSTANTIATION))
        .setAmbiguousHandler(
            e -> getLogger().info(
                "Multiple CDI beans found. "
                    + FALLING_BACK_TO_DEFAULT_INSTANTIATION
            ))
        .lookupOrElseGet(() -> {
          final T instance = delegate.getOrCreate(type);
          // BeanProvider.injectFields(instance);
          return instance;
        });
  }

  @Override
  public <T extends Component> T createComponent(Class<T> componentClass) {
    Unmanaged<T> unmanagedClass = new Unmanaged<>(componentClass);
    Unmanaged.UnmanagedInstance<T> instance = unmanagedClass.newInstance();
    instance.produce().inject().postConstruct();
    return instance.get();
  }

  @Override
  public I18NProvider getI18NProvider() {
    final BeanLookup<I18NProvider> lookup = new BeanLookup<>(beanManager,
        I18NProvider.class, SERVICE);
    if (i18NLoggingEnabled.compareAndSet(true, false)) {
      lookup.setUnsatisfiedHandler(() -> getLogger().info("VaadinSevice Bean")
              /*"Can't find any @VaadinServiceScoped bean implementing '{}'. "
                      + CANNOT_USE_CDI_BEANS_FOR_I18N,
              I18NProvider.class.getSimpleName())*/).setAmbiguousHandler(
          e -> getLogger().info(
              "Found more beans for I18N. "
                  + CANNOT_USE_CDI_BEANS_FOR_I18N
          ));
    } else {
      lookup.setAmbiguousHandler(e -> {
      });
    }
    return lookup.lookupOrElseGet(delegate::getI18NProvider);
  }

  private static Logger getLogger() {
    return Logger.getLogger(CdiInstantiator.class.getName());
  }

  @Override
  public Stream<VaadinServiceInitListener> getServiceInitListeners() {
    return Stream.concat(delegate.getServiceInitListeners(),
        Stream.of(beanManager::fireEvent));
  }

}

