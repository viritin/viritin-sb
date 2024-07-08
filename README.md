# Tooling for optimal Vaadin + Spring Boot experience for Java develoer

This project hacks together better project defaults and developer experience for Java developers than what we currently provide out of the box with our official Vaadin starters. Aim is to make it easier and faster to use for a developer who don't yet know the quirks of Vaadin.

## Design goals of the project:

 * A Spring Boot app started from IDE or via `mvn spring-boot:test-run` should automatically end up in development mode.
 * `mvn install` creates a deployment ready production artifact. Reasoning:
   * This is what experienced Java users expect
   * This is what "cloud native tooling" like buildpacks expect. Makes Vaadin easier to use with various "cloud deployments".
   * No need to learn or maintain "Vaadin specialities" like the production profile currently shipped with all  starters
 * Make the pom.xml look like Vaadin is easy to take into use. Current custom profiles, plugin configurations and exclusions makes Vaadin look quite fragile.
 * Collect input and feedback for a proper implementation directly in the Vaadin core. Related Flow issue: https://github.com/vaadin/flow/issues/17737

## Tested and expected functionality:

* Project checked into IDE as usual, starting development server both with main method (the true SB method) or with maven plugin (mvn spring-boot:test-run) always starts Vaadin app in dev mode.
* mvn install; java -jar target/*jar builds and properly runs production mode artifact. The first point still works after this step.
* Dropping in an add-on with client side extensions works as expected.

## How to try:

Create a project with parent:

    in.virit.sb:project-parent:0.0.3

or...

    in.virit.sb:viritin-project-parent:0.0.3

... if you want to get a free viritin for the same deal.

Add Spring Boot App class and a Vaadin view.
Declare an additional Spring Boot application to the test side for development mode runs and start via main method or with `mvn spring-boot:test-run`. Deploy the built jar file as you'd expect, no need for Vaadin specific tricks.

Or just go to the `example` directory in this folder and play with that project.

*The `example-flattened` works in the same way but doesn't use custom parent pom.xml*. It still uses Spring Boot parent pom, but that can be ditched (check Spring Boot docs) as well if needed. The build file is not a cute, but the developer workflow is the same. This approach is so far only lightly tested.

## Known limitations at this point:

 * You can't make your Spring Boot application class(es) directly extend `AppShellConfigurator` due to assertions made at app start. To make those configs work for both dev & production, introduce a separate class for those configs, as in Example class. Probably this is a better habit anyway to keep Vaadin specific stuff separately. Not a biggie, but you'll face this if migrating existing projects...
 * ~~Hilla is excluded, otherwise production mode fails (I'm probably excluding something that is actually needed in production )~~
 * Good looking setup requires app to use parent ATM, try to check if e.g.  "Maven tiles" could help here.
 * ~~Dev mode exclusion list fed to spring-boot plugin is hard coded -> might exclude something you actually need. Tried to workaround this by fixing a related bug in Spring Boot (plugin), but now I can't seem to be able to dynamically feed the list for it as a property 😬 I bet it worked before...~~ SB repackge exclusions not used at all in this branch...
 * Using flow-maven-plugin (to override a performance issue & nasty warning).

## Implementation details:

 This is now a second iteration/version. The difference to the first version is that Vaadin dev mode deps are now declared to test scope only. Thus, no exclusions in the spring-boot:repackage is needed -> the PoC is much cleaner, simpler and more stable (I could even consider people trying it out in actual projects the need to maintain). So essentially implemented in the same manner as Testcontainers support in Spring Boot.

The downside is that old farts might need to learn bit new things. There is now two Spring Boot main methods:

 * Development mode server is started from a SB app on the test side, or with `mvn spring-boot:test-run`.
 * The actual SB application will always try to be in production mode. Running it will fail if no priming build is done ( `mvn package`).


## Random notes:

 * ~~ Spring Boot has a bug/design flaw IMO: optional deps are getting into the artifact jar file (only devtools get excluded by default). Thus can't just declare vaadin-dev as optional -> hacks/profiles needed. ~~ Not affected by this branch using different approach.
 * Gradle all would be simpler 🤪

## Modules in this repo:


 * sb-configs : Contains a bit of Spring Boot autoconfiguration to enforce dev mode is on whenever vaadin-dev module is on the classpath. This can be used separately as well, if you for example can't use the provided parent pom's
 * project-parent : A pom to inherit from, if you want a clean Vaadin project without a ton of configuration.
 * viritin-project-parent : You guessed it, this parent pom extends the previous one, but gives you a free in.virit:viritin dependency for the same price! (currently) ditches the Copilot as it quite radically slows down the startup off apps in dev mode. flow-react and React router usage are dust off to make the front-end bundle as smaller (~ same as Vaadin 24.3).
 * example : A simple app example for testing, uses project-parent