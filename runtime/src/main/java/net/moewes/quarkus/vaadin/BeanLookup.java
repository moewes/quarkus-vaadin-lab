package net.moewes.quarkus.vaadin;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

/**
 * Utility class for CDI lookup, and instantiation.
 * <p>
 * Dependent beans are instantiated without any warning, but do not get destroyed properly. {@link
 * javax.annotation.PreDestroy} won't run.
 *
 * @param <T> Bean Type
 */
class BeanLookup<T> {

  private final BeanManager beanManager;
  private final Class<T> type;
  private final Annotation[] qualifiers;
  private UnsatisfiedHandler unsatisfiedHandler = () -> {
  };
  private Consumer<AmbiguousResolutionException> ambiguousHandler = e -> {
    throw e;
  };

  final static Annotation SERVICE = new ServiceLiteral();
  private final static Annotation[] ANY = new Annotation[]{new AnyLiteral()};

  private static class ServiceLiteral
      extends AnnotationLiteral<VaadinServiceEnabled>
      implements VaadinServiceEnabled {

  }

  private static class AnyLiteral
      extends AnnotationLiteral<Any>
      implements Any {

  }

  @FunctionalInterface
  public interface UnsatisfiedHandler {

    void handle();
  }

  BeanLookup(BeanManager beanManager, Class<T> type, Annotation... qualifiers) {
    this.beanManager = beanManager;
    this.type = type;
    if (qualifiers.length > 0) {
      this.qualifiers = qualifiers;
    } else {
      this.qualifiers = ANY;
    }
  }

  BeanLookup<T> setUnsatisfiedHandler(UnsatisfiedHandler unsatisfiedHandler) {
    this.unsatisfiedHandler = unsatisfiedHandler;
    return this;
  }

  BeanLookup<T> setAmbiguousHandler(
      Consumer<AmbiguousResolutionException> ambiguousHandler) {
    this.ambiguousHandler = ambiguousHandler;
    return this;
  }

  T lookupOrElseGet(Supplier<T> fallback) {
    final Set<Bean<?>> beans = beanManager.getBeans(type, qualifiers);
    if (beans == null || beans.isEmpty()) {
      unsatisfiedHandler.handle();
      return fallback.get();
    }
    final Bean<?> bean;
    try {
      bean = beanManager.resolve(beans);
    } catch (AmbiguousResolutionException e) {
      ambiguousHandler.accept(e);
      return fallback.get();
    }
    final CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
    //noinspection unchecked
    return (T) beanManager.getReference(bean, type, ctx);
  }

  T lookup() {
    return lookupOrElseGet(() -> null);
  }
}