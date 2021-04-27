package l4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class EventCalendar {

    private final HashMap<String, DateSpan> events = new HashMap<>();

    /***
     * Adds event to calendar. Event's can't overlap, with exception to events of time span duration of length 0.
     * @param name unique name of the event
     * @param dateSpan date span of event
     * @throws IllegalArgumentException if either one below holds true:
     * <ul>
     * <li>event with given name already exists</li>
     * <li>event would overlap with existing event of non-zero length</li>
     * </ul>
     * @implNote new dateSpan is created to prevent invalidating calendar by existing dateSpan reference
     */
    public void addEvent(@NotNull String name, @NotNull DateSpan dateSpan) throws IllegalArgumentException {
        if (events.containsKey(name)) throw new IllegalArgumentException("event with given name already exists");
        var overlapping = events.entrySet().stream()
                .filter(e -> e.getValue().overlaps(dateSpan))
                .findAny();
        if (overlapping.isPresent()) throw new IllegalArgumentException(
                "Event '" + name + "' would overlap with event '" + overlapping.get().getValue() + "'."
        );

        events.put(name, new DateSpan(new Date(dateSpan.start.getTime()), new Date(dateSpan.end.getTime())));
    }

    /**
     * Adds single, duration-less event at given date.
     *
     * @see #addEvent(String, DateSpan)
     * @param name name of the event
     * @param date date of the event
     */
    public void addEvent(@NotNull String name, @NotNull Date date) {
        addEvent(name, new DateSpan(date, date));
    }

    /***
     * Deletes event from calendar and returns date span of this event.
     * @param name name of the event
     * @return removed event or null if event doesn't exist
     */
    public @Nullable DateSpan deleteEvent(@NotNull String name) {
        return events.remove(name);
    }

    /**
     * Changes the date span of event.
     *
     * @param name        name of event to edit
     * @param newDateSpan new date for event
     *
     * @throws IllegalArgumentException if new span doesn't
     */
    public void editDate(@NotNull String name, @NotNull DateSpan newDateSpan) {
        if (!events.containsKey(name)) throw new IllegalArgumentException("Event '" + name + "'doesn't exist.");
        var oldSpan = events.remove(name);
        var overlapping = events.entrySet().stream()
                .filter(e -> e.getValue().overlaps(newDateSpan))
                .findAny();
        if (overlapping.isPresent()) throw new IllegalArgumentException(
                "Event '" + name + "' would overlap with event '" + overlapping.get().getValue() + "'."
        );
        events.put(name, new DateSpan(new Date(newDateSpan.start.getTime()), new Date(newDateSpan.end.getTime())));
    }

    /**
     * Returns event's info.
     *
     * @param name name of event to return
     *
     * @return DateSpan of corresponding event or {@code null} if event doesn't exist
     */
    public @Nullable DateSpan getEvent(@NotNull String name) { return events.get(name); }

    /**
     * Class representing valid, date span in time
     */
    public static class DateSpan {
        @NotNull Date start;
        @NotNull Date end;

        /**
         * Creates new date span. Date span is any non-negative time interval.
         *
         * @param start start date of event
         * @param end   end date of event - can be the same as start
         *
         * @throws IllegalArgumentException if end is before start
         */
        public DateSpan(@NotNull Date start, @NotNull Date end) {
            if (end.before(start)) throw new IllegalArgumentException("End date can't be earlier than start date");
            this.start = start;
            this.end = end;
        }

        /**
         * Checks if two date spans overlap. Two spans of length 0 don't overlap.
         *
         * @param other other date span to compare against
         *
         * @return Returns true if spans strictly overlap. Non-zero length spans overlap if they are equal or one starts
         * while the other one didn't end yet. They DON'T overlap if they are non-zero length and one ends exactly at
         * the same time the other one starts.
         */
        public boolean overlaps(@NotNull DateSpan other) {
            if (this.start.equals(this.end) || other.start.equals(other.end)) return false;
            return this.equals(other) || this.end.after(other.start) && this.start.before(other.end);
        }

        /***
         * Two date spans are equal only if their start and end dates are equal
         * @param o other date
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DateSpan dateSpan = (DateSpan) o;
            return start.equals(dateSpan.start) && end.equals(dateSpan.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
}
