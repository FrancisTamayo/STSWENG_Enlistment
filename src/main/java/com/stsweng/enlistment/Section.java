package com.stsweng.enlistment;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.*;

class Section {
    private final String sectionId;
    private final Schedule schedule;
    private final Room room;

    Section (String sectionId, Schedule schedule, Room room) {
        notBlank(sectionId);
        notNull(schedule);
        notNull(room);
        isTrue(isAlphanumeric(sectionId), "sectionId must be alphanumeric, was " + sectionId);
        this.sectionId = sectionId;
        this.schedule = schedule;
        this.room = room;
    }

    void checkForConflict(Section other) {
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException("this section " + this + "has same schedule as other section" + other
            + "at schedule " + schedule);
        }
    }

    Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return Objects.equals(sectionId, section.sectionId);
    }

    @Override
    public int hashCode() {
        return sectionId != null ? sectionId.hashCode() : 0;
    }
}
