package dk.creatorminds;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DmnConfiguration {

    @Bean
    public DmnEngine createDmnEngine() {
        // create default DMN engine configuration
        DmnEngineConfiguration configuration = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration();

        // build a new DMN engine
        DmnEngine dmnEngine = configuration.buildEngine();
        return dmnEngine;
    }
}
