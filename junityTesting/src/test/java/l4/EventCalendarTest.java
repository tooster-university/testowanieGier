package l4;

import l4.EventCalendar;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventCalendarTest {
	EventCalendarTest() {
		now = new Date();
    	Date tmp = new Date();
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(tmp); 
    	c.add(Calendar.HOUR, 1);
    	nextH = c.getTime();
	}
	
	private Date now;
	private Date nextH;
    private static final Date[] dates = List.of(100, 200, 300, 400, 500).stream().map(Date::new).toArray(Date[]::new);

    @Test
    void addEvent() {
    	EventCalendar ec = new EventCalendar();
    	
    	ec.addEvent("event1", new EventCalendar.DateSpan(now, nextH));
        
    	assertThrows(IllegalArgumentException.class, () -> {
    		ec.addEvent("event1", new EventCalendar.DateSpan(now, nextH));
    	  }).getMessage().contains("event with given name already exists");
    	
    	assertThrows(IllegalArgumentException.class, () -> {
    		ec.addEvent("event2", new EventCalendar.DateSpan(now, nextH));
    	  }).getMessage().contains("would overlap with event");
    }

    @Test
    void deleteEvent() {
    	EventCalendar.DateSpan ds = new EventCalendar.DateSpan(now, nextH);
    	EventCalendar ec = new EventCalendar();
    	ec.addEvent("event1", new EventCalendar.DateSpan(now, nextH));
    	EventCalendar.DateSpan ds2 = ec.deleteEvent("event1");
    	
    	assertEquals(ds, ds2);
    }

    @Test
    void editDate() {
    	EventCalendar.DateSpan ds = new EventCalendar.DateSpan(now, nextH);
    	EventCalendar ec = new EventCalendar();
    	assertThrows(IllegalArgumentException.class, () -> {
    		ec.editDate("event2", new EventCalendar.DateSpan(now, nextH));
    	  }).getMessage().contains("doesn't exist.");
    	ec.addEvent("event1", new EventCalendar.DateSpan(now, nextH));
    	assertThrows(IllegalArgumentException.class, () -> {
    		ec.editDate("event2", new EventCalendar.DateSpan(now, nextH));
    	  }).getMessage().contains("would overlap with event");
    }

    @Test
    void getEvent() {
    	EventCalendar.DateSpan ds = new EventCalendar.DateSpan(now, nextH);
    	EventCalendar ec = new EventCalendar();
    	ec.addEvent("event1", new EventCalendar.DateSpan(now, nextH));
    	EventCalendar.DateSpan ds2 = ec.getEvent("event1");
    	
    	assertEquals(ds, ds2);
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
