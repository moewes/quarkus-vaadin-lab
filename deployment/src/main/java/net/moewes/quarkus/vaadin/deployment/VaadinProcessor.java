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
import io.quarkus.deployment.builditem.substrate.ReflectiveClassBuildItem;
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

  static DotName ROUTE_ANNOTATION = DotName.createSimple(Route.class.getName());

  private static final Logger log = Logger.getLogger(VaadinProcessor.class);

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
  void scanForBeans(VaadinRecorder recorder, BeanArchiveIndexBuildItem beanArchiveIndex,
      BeanContainerBuildItem beanContainer) {
    log.info("Search for @Route annotated views");
    IndexView indexView = beanArchiveIndex.getIndex();
    Collection<AnnotationInstance> testBeans = indexView.getAnnotations(ROUTE_ANNOTATION);
    for (AnnotationInstance ann : testBeans) {
      log.info("Found " + ann.target().toString());
      recorder.registerRoute(beanContainer.getValue(), ann.target().toString());
    }
  }

  @BuildStep
  void reflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
    log.info("Register reflective CLasses");

    ReflectiveClassBuildItem classBuildItem = ReflectiveClassBuildItem
        .builder("com.vaadin.flow.component.UI")
        .constructors(true)
        .methods(true)
        .build();

    reflectiveClass.produce(classBuildItem);

  }
  /*
  @BuildStep
  ReflectiveClassBuildItem vaadin() {
    log.info("Register reflective CLasses 2");
    return new ReflectiveClassBuildItem(true, true, "com.vaadin.flow.components.UI");
  }

   */

}
