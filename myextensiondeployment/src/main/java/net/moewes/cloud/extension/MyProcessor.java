package net.moewes.cloud.extension;

import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import com.vaadin.flow.router.Route;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanArchiveIndexBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;
import java.util.Collection;
import net.moewes.MyBean;
import net.moewes.MyRecorder;
import net.moewes.MyServlet;
import net.moewes.MyVaadinServlet;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

public class MyProcessor {

  static DotName ROUTE_ANNOTATION = DotName.createSimple(Route.class.getName());

  @BuildStep
  FeatureBuildItem featureBuildItem() {
    System.out.println("Processor: add Feature");
    return new FeatureBuildItem("my-extension");
  }

  @Record(STATIC_INIT)
  @BuildStep
  void helloBuildStep(MyRecorder recorder) {
    System.out.println("Processor: My Recorder test");
    recorder.sayHello("World");
  }

  @BuildStep
  BeanDefiningAnnotationBuildItem registerX() {
    System.out.println("Processor: My Bean defining");
    return new BeanDefiningAnnotationBuildItem(ROUTE_ANNOTATION);
  }

  @BuildStep
  AdditionalBeanBuildItem beans() {
    System.out.println("Processor: My Bean defining");
    return new AdditionalBeanBuildItem(MyBean.class, MyServlet.class, MyVaadinServlet.class);
  }

  /*
    @BuildStep
    AdditionalBeanBuildItem beans2() {
      System.out.println("Processor: My Bean defining");
      return new AdditionalBeanBuildItem(false, MyServlet.class);
    }
  */
  @BuildStep
  ServletBuildItem myServlet() {
    System.out.println("Processor: add MyServlet");
    return ServletBuildItem.builder("MyServlet", MyServlet.class.getName())
        .addMapping("/ui/*").build();
  }

  @BuildStep
  ServletBuildItem myVaadinServlet() {
    System.out.println("Processor: add MyVaadinServlet");
    return ServletBuildItem.builder("MyVaadinServlet", MyVaadinServlet.class.getName())
        .addMapping("/vaadin/*").build();
  }

  @BuildStep
  @Record(STATIC_INIT)
  void scanForBeans(MyRecorder recorder, BeanArchiveIndexBuildItem beanArchiveIndex,
      BeanContainerBuildItem beanContainer) {
    System.out.println("Processor: search for @Route annotated views");
    IndexView indexView = beanArchiveIndex.getIndex();
    Collection<AnnotationInstance> testBeans = indexView.getAnnotations(ROUTE_ANNOTATION);
    for (AnnotationInstance ann : testBeans) {
      System.out.println("Processor: found " + ann.target().toString());
      recorder.registerRoute(beanContainer.getValue(), ann.target().toString());
    }
  }


}
