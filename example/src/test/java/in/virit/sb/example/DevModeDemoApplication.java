package in.virit.sb.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevModeDemoApplication /* implements AppShellConfigurator */ // AppShellconfigurator has nasty check (intention to be helpful), should be easy to fix in the core...
{

    // This method should be used to start the application in development mode.
    // You could add other dev mode configs (like Testcontainers) to this class as well
    public static void main(String[] args) {
        SpringApplication.run(DevModeDemoApplication.class, args);
    }
}
