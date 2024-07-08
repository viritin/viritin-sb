# An example project for Vaadin with clean pom and without production profile

Just use it, but forget the production profile 😎

## Notes

 * This project by default uses both Hilla and Flow. Until performance regressions
   in Vaadin 24.4 are fixed, and in case you can do it in pure Java, you might 
   want to use the `viritin-project-parent` instead of `project-parent`, which
   excludes Hilla and Copilot -> app starts (and restarts) way faster.
 * With Hilla, it might be necessary to add mvnw to the project for certain IDEs
