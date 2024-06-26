package in.virit.sb.example;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H1("Hello!"));
        // Make some runtime checks to see if we are in dev or prod
        try {
            // strict check for dev mode for testing, if dev mode classes are even present,
            // report as such!
            Class.forName("com.vaadin.base.devserver.ServerInfo");
            if(!VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode()) {
                add("Vaadin is in DEVMODE!");
            } else {
                add("Production mode, but dev mode classes are there.");
            }
        } catch (ClassNotFoundException e) {
            if(VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode()) {
                add("PROD 😎");
            } else {
                add("No dev mode classes, but still in production mode!?");
            }
        }
        
    }

}
