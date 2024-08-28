package in.virit.sb.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class DemoApplication {

    // This method is for "production mode server" and might need a priming
    // build (mvn package) to be run directly.
    // Use DevModeDemoApplication during development to enable Livereload & Copilot
    public static void main(String[] args) {
        enforceProdcutionMode();
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * This method enforces that the application is started in production mode
     * and if not, exits the application with a meaningful error message.
     * Hoping this to be part of Vaadin in the future releases...
     */
    private static void enforceProdcutionMode() {
        InputStream resourceAsStream = DemoApplication.class.getResourceAsStream("/META-INF/VAADIN/config/flow-build-info.json");
        try {
            String s = new String(resourceAsStream.readAllBytes());
            if(!s.contains("\"productionMode\": true")) {
                throw new RuntimeException("Production bundle not available!!!");
            }
        } catch (Exception ex) {
            System.out.println("Production bundle not available. Exiting...");
            System.out.println("Production mode needs Vaadin plugin, usually triggered with e.g.: mvn package");
            System.out.println("If you are trying to launch in development mode, try the application class in src/test instead..");
            System.exit(1);
        }
    }
}
