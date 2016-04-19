package dk.creatorminds;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DmnApplication.class)
public class DmnApplicationTests {

    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private MutableDateTimeProvider mutableDateTimeProvider;

    private static final int YEAR = 2016;
    private static final int MONTH = 1;
    private static final int DAY = 1;
    private static final int HOUR = 8;
    private static final int MINUTE = 0;
    private static final int SECOND = 0;
    private static final int NANO_SECOND = 0;

    @Autowired private DmnEngine dmnEngine;
    private DmnServiceImpl dmnService;

    @Before
    public void setup() throws Exception {
        mutableDateTimeProvider = new MutableDateTimeProvider();
        dmnService = new DmnServiceImpl(dmnEngine, mutableDateTimeProvider);

        setDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
    }

    private void setDateTime(int year, int month, int day, int hour, int minute, int second) {
        Instant instant = LocalDateTime.of(year, month, day, hour, minute, second).atZone(ZoneId.systemDefault()).toInstant();
        Date dateNow = Date.from(instant);

        mutableDateTimeProvider.set(dateNow.toInstant());
    }

    @After
    public void teardown() throws Exception {
        mutableDateTimeProvider.set(Instant.now());
    }

	@Test
	public void testValidEfterlonBirth1953() {
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1953,1,1)));
	}

    @Test
    public void testValidEfterlonBirth1954() {
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1954,1,1)));
    }

    @Test
    public void testValidEfterlonBirth1955() {
        setDateTime(2020, MONTH, DAY, HOUR, MINUTE, SECOND);
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1955,1,1)));
    }

    @Test
    public void testInvalidEfterlonBirth1955() {
        assertFalse(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1955,1,1)));
    }

    @Test
    public void testValidEfterlonBirth1956() {
        setDateTime(2020, MONTH, DAY, HOUR, MINUTE, SECOND);
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1956,1,1)));
    }

    @Test
    public void testValidEfterlonBirth1959() {
        setDateTime(2024, MONTH, DAY, HOUR, MINUTE, SECOND);
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1959,1,1)));
    }

    @Test
    public void testValidEfterlonBirth1963() {
        setDateTime(2029, MONTH, DAY, HOUR, MINUTE, SECOND);
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1963,1,1)));
    }

    @Test
    public void testValidEfterlonBirth1967() {
        setDateTime(2033, MONTH, DAY, HOUR, MINUTE, SECOND);
        assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1967,1,1)));
    }
}
