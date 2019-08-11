package net.moewes.cloud.extension;

import com.vaadin.flow.router.Route;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;
import net.moewes.MyRecorder;
import net.moewes.MyServlet;
import org.jboss.jandex.DotName;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;
import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

public class MyProcessor {

  static DotName TEST_ANNOTATION = DotName.createSimple(Route.class.getName());

  @BuildStep
  FeatureBuildItem featureBuildItem() {
    System.out.println("My Feature");
    return new FeatureBuildItem("my-extension");
  }

  @Record(STATIC_INIT)
  @BuildStep
  void helloBuildStep(MyRecorder recorder) {
    System.out.println("My Recorder");
    recorder.sayHello("World");
  }


  @BuildStep
  BeanDefiningAnnotationBuildItem registerX() {
    System.out.println("My Bean defining");
    return new BeanDefiningAnnotationBuildItem(TEST_ANNOTATION);
  }

  @BuildStep
  ServletBuildItem myServlet() {
    System.out.println("My Servlet");
    return ServletBuildItem.builder("MyServlet", MyServlet.class.getName())
            .addMapping("/ui/*").build();
  }

}
