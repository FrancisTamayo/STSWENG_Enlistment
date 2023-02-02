package com.stsweng.enlistment;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;

class Room {
    private final String roomName;
    private final int capacity;
    private int enrollees;

    Room (String roomName, int capacity) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was " + roomName);
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity should be not less than 0, was: " + capacity);
        }

        this.roomName = roomName;
        this.capacity = capacity;
        this.enrollees = 0;
    }

    void checkIfMax() {
        if (enrollees == capacity) {
            throw new MaximumEnrolleesException("this room " + this + "has reached its maximum capacity of " +
                    capacity);
        }
    }

    void addEnrollee() {
        enrollees += 1;
    }

    void removeEnrollee() {
        enrollees -= 1;
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return capacity == room.capacity && Objects.equals(roomName, room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, capacity);
    }
}
