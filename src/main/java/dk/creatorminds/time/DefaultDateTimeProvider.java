package dk.creatorminds.time;

import java.time.Instant;

import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 *
 * An immutable DateTimeProvider that supplies date-time related functions.
 */
@Component
public class DefaultDateTimeProvider implements DateTimeProvider {

    @Override
    public Instant nowAsInstant() {
        return Instant.now();
    }
}
