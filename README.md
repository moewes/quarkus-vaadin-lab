# quarkus-vaadin-lab

An experimental Quarkus-Extension to use Vaadin with Quarkus.

# How to start

Check out branch

Build all with mvn install

See example for how to use it with quarkus.

## run example in dev mode

Switch into example directory and type:

````
mvn clean package quarkus:dev -Dvaadin.compatibilityMode=true
````

Open Browser http://localhost:8080

## run example java vm mode

Switch into example directory and type:

````
mvn clean package

java -Dvaadin.compatibilityMode=true -jar ./target/quarkus-vaadin-extension-example-0.1.0-SNAPSHOT-runner.jar
````

Open Browser http://localhost:8080

## run example in native mode

Switch into example directory and type:

````
mvn clean package -Pnative

./target/quarkus-vaadin-extension-example-0.1.0-SNAPSHOT-runner -Dvaadin.compatibilityMode=true

````

Open Browser http://localhost:8080

Ensure that $GRAALVM_HOME is set.

# Feature Matrix

 Component | Dev Mode | VM Mode | Native Mode |
 -------- | --------- | --------- | --------- |
 Accordion | works | works | works |
 AppLayout | works | works | fails |
 Button | works | works | works |
 Detail | works | works | fails |
 [List box](https://vaadin.com/components/vaadin-list-box) | works | works | works |
 others | not tested | not tested | not tested
 Inject CDI Beans | works | works | works


