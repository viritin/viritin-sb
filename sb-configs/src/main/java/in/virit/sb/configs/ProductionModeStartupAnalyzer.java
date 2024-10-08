package in.virit.sb.configs;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.context.ApplicationContextException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductionModeStartupAnalyzer extends AbstractFailureAnalyzer<ApplicationContextException> {

    private static final String classNameToTestForDevelopmentMode = "com.vaadin.base.devserver.ServerInfo";

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, ApplicationContextException cause) {
        if(cause.getMessage().contains("Unable to start web server") && otherCheck()) {
            return new FailureAnalysis("It looks like you are trying to start Vaadin app in production mode server without a front-end bundle.",
                    "If you want to run in development mode instead, start with a Spring Boot app from your test sources." +
                            "Either directly from IDE or with `mvn sprint-boot:test-run`" +
                            "Copy a one from your main sources if there isn't one yet and name it e.g. DevModeApplication." +
                            " \n\n" +
                            "If you want to test your app in production mode, make a priming build, e.g. `mvn package`, before starting.", cause);
        }
        return null;
    }

    private boolean otherCheck() {
        try {
            var buildInfoPath = Path.of("target/classes/META-INF/VAADIN/config/flow-build-info.json");
            try {
                if (Files.exists(buildInfoPath) && Files.readString(buildInfoPath).contains("\"productionMode\": true")) {
                    // Some other issue, but no idea what
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(DevModeConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            Class.forName(classNameToTestForDevelopmentMode);
            // Must be something lese then
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }
}
