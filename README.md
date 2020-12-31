# News:

2020-12-31 I have thought about another way to combine the principles of vaadin flow, ui microservices and quarkus. May have a look at my project [CloudUi](https://moewes.github.io/cloud-ui). I am looking forward for feedback!

# quarkus-vaadin-lab

An experimental Quarkus-Extension to use Vaadin with Quarkus.

Quarkus: 0.23.2
Vaadin: 14.0.7

# How to start

Check out branch

Build all with mvn install

See example for how to use it with quarkus.

## run example in dev mode

Switch into example directory and type:

````
mvn clean package quarkus:dev
````

Open Browser http://localhost:8080

## run example java vm mode

Switch into example directory and type:

````
mvn clean package

java -jar ./target/quarkus-vaadin-extension-example-0.1.0-SNAPSHOT-runner.jar
````

Open Browser http://localhost:8080

## run example in native mode

Switch into example directory and type:

````
mvn clean package -Pnative

./target/quarkus-vaadin-extension-example-0.1.0-SNAPSHOT-runner

````

Open Browser http://localhost:8080

Ensure that $GRAALVM_HOME is set.

# Feature Matrix

 Component | Dev Mode | VM Mode | Native Mode |
 -------- | --------- | --------- | --------- |
 Accordion | works | works | works |
 AppLayout | works | works | fails |
 Button | works | works | works |
 [Check box](https://vaadin.com/components/vaadin-checkbox) | works | works | works
 Detail | works | works | works |
 [List box](https://vaadin.com/components/vaadin-list-box) | works | works | works |
 [Split Layout](https://vaadin.com/components/vaadin-split-layout) | works | works | works
 others | not tested | not tested | not tested
 Inject CDI Beans | works | works | works


