# A parent project for Vaadin Flow add-ons

This project is a parent project for Vaadin Flow add-ons. It uses Spring Boot to provide a 
smooth development server setup, but the produced artifact **has NO dependency to Spring**.

Also configures some common plugins and dependencies for Vaadin Flow add-ons.

 * Minimal set of required dependencies for Vaadin Flow add-ons
 * A Spring Boot based development server dependencies, **in test scope**.
 * Ready for efficient testing with *spring-boot-starter-test* (both unit & end-to-end).
 * *maven-flatten-plugin* is used to flatten the pom.xml for the release. Cleans unnecessary details, that are not relevant for consumers of the add-on (like the reference to this parent pom)
 * *maven-source-plugin* is used to generate source jars
 * *maven-javadoc-plugin* is used to generate javadoc jars
 * *maven-gpg-plugin* is used to sign the artifacts. This is a requirement for the Central Repository
 * *central-publishing-maven-plugin* used to deploy the artifacts to the Central Repository

## How to use

Easieast way for new projects is to use archetype to generate project base (CLI or with IDE using these maven coordinates):

    mvn archetype:generate -DinteractiveMode=false -DgroupId=io.github.youraccount -DartifactId=my-great-addon -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=in.virit -DarchetypeArtifactId=viritin-vaadin-addon-archetype -DarchetypeVersion=2.0.2

The archetype generates a simple add-on stub and examples how to automatically test the add-on.

There is also [Sliders](https://github.com/mstahv/sliders) add-on project, whose main purpose is to provide a working example for this project setup.
