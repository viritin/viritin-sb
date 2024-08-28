package in.virit.sb.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class DevModeDemoApplication extends DemoApplication {

    // This method should be used to start the application in development mode.
    // You could add other dev mode configs (like Testcontainers) to this class as well
    public static void main(String[] args) {
        enforeDevMode();
        SpringApplication.run(DevModeDemoApplication.class, args);
    }

    private static void enforeDevMode() {
        var buildInfoPath = Path.of("target/classes/META-INF/VAADIN/config/flow-build-info.json");
        try {
            // TODO figure out if the dev mode could be enforced in some other way (~ better hack for the PoC..)
            if (Files.exists(buildInfoPath) && Files.readString(buildInfoPath).contains("\"productionMode\": true")) {
                FileSystemUtils.deleteRecursively(Path.of("target/classes/META-INF/VAADIN"));
                Logger.getLogger(DevModeDemoApplication.class.getName()).log(Level.INFO,
                        "Deleted packaged production bundle as going devmode.");
            }
        } catch (IOException ex) {
            Logger.getLogger(DevModeDemoApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
