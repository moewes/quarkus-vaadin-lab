# quarkus-vaadin-lab

This branch is working with Vaadin 13.

## How to start

Check out this repository. Build with mvn install.

The project includes three subproject:

* runtime - You need this to include in your quarkus projekt to use Vaadin
* deployment - Build time Quarkus processor to process the Vaadin @Route annotatins
* example - sample example that show how to use the extension

This was a first POC of a Vaadin Quarkus Extension.

It works with mvn package quarkus:dev

Also you can compile a native executable. See Quarkus docs for more information about native builds and graalvm.


