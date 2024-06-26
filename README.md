# Tooling for optimal Vaadin + Spring Boot experience for Java develoer

This project hacks together better project defaults and developer experience for Java developers than what we currently provide out of the box with our official Vaadin starters. Aim is to make it easier and faster to use for a developer who don't yet know the quirks of Vaadin.

Design goals of the project:

 * A Spring Boot app started from IDE or via `mvn spring-boot:run` should automatically end up in development mode.
 * `mvn install` creates a deployment ready production artifact. Reasoning:
   * This is what experienced Java users expect
   * This is what "cloud native tooling" like buildpacks expect. Makes Vaadin easier to use with various "cloud deployments".
   * No need to learn or maintain "Vaadin specialities" like the production profile currently shipped with all  starters
 * Make the pom.xml look like Vaadin is easy to take into use. Current custom profiles, plugin configurations and exclusions makes Vaadin look quite fragile.
 * Collect input and feedback for a proper implementation directly in the Vaadin core. Related Flow issue: https://github.com/vaadin/flow/issues/17737

Tested and expected functionality:

* Project checked into IDE as usual, starting development server both with main method (the true SB method) or with maven plugin (mvn spring-boot:run) always starts Vaadin app in dev mode.
* mvn install; java -jar target/*jar builds and properly runs production mode artifact. The first point still works after this step.
* Dropping in an add-on with client side extensions works as expected. 

Known limitations at this point:

 * Hilla is excluded, otherwise production mode fails (I'm probably excluding something that is actually needed in production )
 * Good looking setup requires app to use parent ATM, try to check if e.g.  "Maven tiles" could help here.
 * Dev mode exclusion list fed to spring-boot plugin is hard coded -> might exclude something you actually need. Tried to workaround this by fixing a related bug in Spring Boot (plugin), but now I can't seem to be able to dynamically feed the list for it as a property 😬 I bet it worked before...
 * Using flow-maven-plugin (to override a performance issue & nasty warning) -> default bundles are not used. Now how to create custom ones??

Random notes:

 * Spring Boot has a bug/design flaw IMO: optional deps are getting into the artifact jar file (only devtools get excluded by default). Thus can't just declare vaadin-dev as optional -> hacks/profiles needed.
 * Gradle all would be simpler 🤪

## How to try:

Create a project with parent:

    in.virit.sb:project-parent:0.0.1

or...

    in.virit.sb:viritin-project-parent:0.0.1

... if you want to get a free viritin for the same deal.

Add Spring Boot App class and a Vaadin view, start in a way you wish. Deploy the build jar as you'd expect.

Or just go to the example directory and play with that project.

## Modules in this repo:


 * sb-configs : Contains a bit of Spring Boot autoconfiguration to enforce dev mode is on whenever vaadin-dev module is on the classpath. This can be used separately as well, if you for example can't use the provided parent pom's
 * project-parent : A pom to inherit from, if you want a clean Vaadin project without a ton of configuration.
 * viritin-project-parent : You guessed it, this parent pom extends the previous one, but gives you a free in.virit:viritin dependency for the same price! (currently) ditches the Copilot as it quite radically slows down the startup off apps in dev mode. flow-react and React router usage are dust off to make the front-end bundle as smaller (~ same as Vaadin 24.3).
 * example : A simple app example for testing, uses project-parent