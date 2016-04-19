package dk.creatorminds.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>
 * Main supplier of date-time related functions. The necessity of the interface cannot be understated,
 *  as it is problematic for direct invocations of current time creation to be tested in a controlled
 *  environment, mainly because time is always changing.
 *
 *  </p>
 *  <p>
 *  It is therefore paramount that subclasses of
 *  this interface exist for Production and Test code. The Production subclass immutable - business as
 *  usual, but the Test subclass should be mutable - so the "current" time can be set as a fixed value -
 *  making testing of time easier.
 * </p>
 * <p>
 *  All instantiations of current datetime should go through this interface
 *  instead of using the direct invocations like "new Date() or Instant.now()" - to benefit from the
 *  capability to mock the current datetime.
 *  </p>
 */
public interface DateTimeProvider {

    Instant nowAsInstant();

    default LocalDate nowAsDate() {
        return LocalDateTime.ofInstant(nowAsInstant(), ZoneId.systemDefault()).toLocalDate();
    }
}
