package l4;

import l4.EventCalendar;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventCalendarTest {

    private static final Date[] dates = List.of(100, 200, 300, 400, 500).stream().map(Date::new).toArray(Date[]::new);

    @Test
    void addEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void editDate() {
    }

    @Test
    void getEvent() {
    }

    @Nested
    class DataSpanTest {

        @Test
        void zeroSpanNonOverlapTest() {
            Date now = new Date();
            var ds1 = new EventCalendar.DateSpan(now, now);
            var ds2 = new EventCalendar.DateSpan(now, now);
            assertEquals(ds1, ds2);
            assertFalse(ds1.overlaps(ds2));
        }

        @Test
        void spanOverlapTest() {
            assertTrue(new EventCalendar.DateSpan(dates[0], dates[2])
                               .overlaps(new EventCalendar.DateSpan(dates[1], dates[3])));
            assertFalse(new EventCalendar.DateSpan(dates[0], dates[0])
                                .overlaps(new EventCalendar.DateSpan(dates[0], dates[3])));
            assertFalse(new EventCalendar.DateSpan(dates[0], dates[1])
                                .overlaps(new EventCalendar.DateSpan(dates[1], dates[2])));
            assertFalse(new EventCalendar.DateSpan(dates[0], dates[1])
                                .overlaps(new EventCalendar.DateSpan(dates[2], dates[3])));
        }

        @Test
        void invalidSpanTest() {
            assertThrows(IllegalArgumentException.class, () -> {new EventCalendar.DateSpan(dates[1], dates[0]);});
        }
    }

}
