package dk.creatorminds;

import java.time.Instant;

import dk.creatorminds.time.DateTimeProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 *
 * A mutable DateTimeProvider that supplies date-time related functions. The ability to set
 *  time gives this class the flexibility to "stop the clock" during tests.
 */
@Component
@Primary
public class MutableDateTimeProvider implements DateTimeProvider {

    private Instant timeSource;

    public MutableDateTimeProvider() {
        timeSource = Instant.now();
    }

    public void set(Instant newTimeSource) {
        timeSource = newTimeSource;
    }

    @Override
    public Instant nowAsInstant() {
        return timeSource;
    }

}
