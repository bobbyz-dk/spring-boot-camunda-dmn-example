package dk.creatorminds;

import com.sun.javafx.scene.control.behavior.OptionalBoolean;
import dk.creatorminds.time.DateTimeProvider;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class DmnServiceImpl {

    private DateTimeProvider dateTimeProvider;
    @Autowired
    private DmnEngine dmnEngine;
    @Autowired @Qualifier(value = "efterlon")
    private DmnDecision decision;

    @Autowired
    public DmnServiceImpl(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public boolean validateEfterlonFleksydelseAge(LocalDate birthDate) {
        int birthYear = birthDate.getYear();
        int age = Period.between(birthDate, dateTimeProvider.nowAsDate()).getYears();

        VariableMap variables = Variables
                .putValue("currentDate", Date.from(dateTimeProvider.nowAsDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .putValue("birthYear", birthYear)
                .putValue("age", age);

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        //return Optional.ofNullable(result.getSingleResult().get("efterlon")).isPresent();
        return ((Boolean) result.getSingleResult().get("efterlon")).booleanValue();
    }

}
