package in.virit.sb.configs;

import com.vaadin.base.devserver.ServerInfo;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.FileSystemUtils;

/**
 * If Vaadin dev mode is on classpath (checked from one class), make sure a production bundle is disabled. 
 * TODO add an explicit flag to bypass for come test scenarios.
 */
@Configuration
@ConditionalOnClass(ServerInfo.class)
public class DevModeConfig implements BeanPostProcessor {

    public DevModeConfig() {
        Logger.getLogger(DevModeConfig.class.getName()).log(Level.INFO, "Enforcing Vaadin dev mode...");
        var buildInfoPath = Path.of("target/classes/META-INF/VAADIN/config/flow-build-info.json");
        try {
            if (Files.exists(buildInfoPath) && Files.readString(buildInfoPath).contains("\"productionMode\": true")) {
                try {
                    Class.forName("org.springframework.boot.test.context.SpringBootTest");
                    // Testing, leave to whatever Vaadin mode...
                } catch (ClassNotFoundException e) {
                    FileSystemUtils.deleteRecursively(Path.of("target/classes/META-INF/VAADIN"));
                    Logger.getLogger(DevModeConfig.class.getName()).log(Level.INFO,
                            "Deleted packaged production bundle as going devmode.");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DevModeConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
