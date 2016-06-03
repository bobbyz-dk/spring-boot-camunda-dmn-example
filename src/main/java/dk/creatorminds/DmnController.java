package dk.creatorminds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/dmn")
public class DmnController {

    private final DmnServiceImpl dmnService;

    @Autowired
    public DmnController(DmnServiceImpl dmnService) {
        this.dmnService = dmnService;
    }

    @RequestMapping(value = "/{birth}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean validate(
            @PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate birth) {
        return dmnService.validateEfterlonFleksydelseAge(birth);
    }

}
