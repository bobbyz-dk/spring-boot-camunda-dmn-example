package dk.creatorminds;

import com.sun.javafx.scene.control.behavior.OptionalBoolean;
import dk.creatorminds.time.DateTimeProvider;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class DmnServiceImpl {

    private final DmnEngine dmnEngine;
    private DateTimeProvider dateTimeProvider;
    private final Resource resource;

    @Autowired
    public DmnServiceImpl(DmnEngine dmnEngine,
                          DateTimeProvider dateTimeProvider) {
        this.dmnEngine = dmnEngine;
        this.dateTimeProvider = dateTimeProvider;
        resource = new ClassPathResource("dmn/efterlon.dmn");
    }

    public boolean validateEfterlonFleksydelseAge(LocalDate birthDate) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int birthYear = birthDate.getYear();
        int age = Period.between(birthDate, dateTimeProvider.nowAsDate()).getYears();

        VariableMap variables = Variables
                .putValue("birthYear", birthYear)
                .putValue("age", age);

        DmnDecision decision = dmnEngine.parseDecision("decision", is);
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        return Optional.ofNullable(result.getSingleResult()/*.getSingleEntry()*/).isPresent();
    }

}
