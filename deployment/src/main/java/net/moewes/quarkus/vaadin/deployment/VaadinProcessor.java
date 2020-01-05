package net.moewes.quarkus.vaadin.deployment;

import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import com.vaadin.flow.router.Route;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanArchiveIndexBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;
import java.util.Collection;
import net.moewes.quarkus.vaadin.QuarkusVaadinServlet;
import net.moewes.quarkus.vaadin.RegisteredRoutesBean;
import net.moewes.quarkus.vaadin.VaadinRecorder;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.jboss.logging.Logger;

public class VaadinProcessor {

  private static final Logger log = Logger.getLogger(VaadinProcessor.class);
  static private DotName ROUTE_ANNOTATION = DotName.createSimple(Route.class.getName());

  @BuildStep
  FeatureBuildItem featureBuildItem() {
    log.info("Add Feature");
    return new FeatureBuildItem("quarkus-vaadin");
  }

  @BuildStep
  BeanDefiningAnnotationBuildItem registerX() {
    return new BeanDefiningAnnotationBuildItem(ROUTE_ANNOTATION);
  }

  @BuildStep
  AdditionalBeanBuildItem beans() {
    return new AdditionalBeanBuildItem(RegisteredRoutesBean.class, QuarkusVaadinServlet.class);
  }

  @BuildStep
  ServletBuildItem vaadinServlet() {
    log.info("Add QuarkusVaadinServlet");
    return ServletBuildItem.builder("QuarkusVaadinServlet", QuarkusVaadinServlet.class.getName())
        .addMapping("/vaadin/*")
        .addMapping("/frontend/*")
        .build();
  }

  @BuildStep
  @Record(STATIC_INIT)
  void scanForBeans(VaadinRecorder recorder,
      BeanArchiveIndexBuildItem beanArchiveIndex,
      BeanContainerBuildItem beanContainer,
      BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
    log.info("Search for @Route annotated views");
    IndexView indexView = beanArchiveIndex.getIndex();
    Collection<AnnotationInstance> testBeans = indexView.getAnnotations(ROUTE_ANNOTATION);
    for (AnnotationInstance ann : testBeans) {
      log.info("Found " + ann.target().toString());
      recorder.registerRoute(beanContainer.getValue(), ann.target().toString());
      reflectiveClass.produce(new
          ReflectiveClassBuildItem(false, false, ann.target().toString()));
    }
  }

  @BuildStep
  void reflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
    log.info("Register reflective CLasses");

    // Vaadin
    ReflectiveClassBuildItem vaadinClassBuildItem = ReflectiveClassBuildItem
        .builder("com.vaadin.flow.component.UI",
            "com.vaadin.flow.component.PollEvent",
            "com.vaadin.flow.component.ClickEvent",
            "com.vaadin.flow.component.CompositionEndEvent",
            "com.vaadin.flow.component.CompositionStartEvent",
            "com.vaadin.flow.component.CompositionUpdateEvent",
            "com.vaadin.flow.component.KeyDownEvent",
            "com.vaadin.flow.component.KeyPressEvent",
            "com.vaadin.flow.component.KeyUpEvent",
            "com.vaadin.flow.component.splitlayout.GeneratedVaadinSplitLayout$SplitterDragendEvent",
            "com.vaadin.flow.component.details.Details$OpenedChangeEvent",
            "com.vaadin.flow.component.details.Details",
            "com.vaadin.flow.router.InternalServerError",
            "com.vaadin.flow.router.RouteNotFoundError",
            "com.vaadin.flow.theme.lumo.Lumo")
        .constructors(true)
        .methods(true)
        .build();

    reflectiveClass.produce(vaadinClassBuildItem);
    // Athmosphere
    ReflectiveClassBuildItem athmosClassBuildItem = ReflectiveClassBuildItem
        .builder("org.atmosphere.cpr.DefaultBroadcaster",
            "org.atmosphere.cpr.DefaultAtmosphereResourceFactory",
            "org.atmosphere.cpr.DefaultBroadcasterFactory",
            "org.atmosphere.cpr.DefaultMetaBroadcaster",
            "org.atmosphere.cpr.DefaultAtmosphereResourceSessionFactory",
            "org.atmosphere.util.VoidAnnotationProcessor",
            "org.atmosphere.cache.UUIDBroadcasterCache",
            "org.atmosphere.websocket.protocol.SimpleHttpProtocol",
            "org.atmosphere.interceptor.IdleResourceInterceptor",
            "org.atmosphere.interceptor.OnDisconnectInterceptor",
            "org.atmosphere.interceptor.WebSocketMessageSuspendInterceptor",
            "org.atmosphere.interceptor.JavaScriptProtocol",
            "org.atmosphere.interceptor.JSONPAtmosphereInterceptor",
            "org.atmosphere.interceptor.SSEAtmosphereInterceptor",
            "org.atmosphere.interceptor.AndroidAtmosphereInterceptor",
            "org.atmosphere.interceptor.PaddingAtmosphereInterceptor",
            "org.atmosphere.interceptor.CacheHeadersInterceptor",
            "org.atmosphere.interceptor.CorsInterceptor")
        .constructors(true)
        .methods(true)
        .build();

    reflectiveClass.produce(athmosClassBuildItem);
  }
}
