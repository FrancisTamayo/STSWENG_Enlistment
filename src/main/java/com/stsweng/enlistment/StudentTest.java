package com.stsweng.enlistment;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.stsweng.enlistment.Period.*;
import static com.stsweng.enlistment.Days.*;

class StudentTest {
    static final Schedule DEFAULT_SCHEDULE = new Schedule(MTH, H1000);

    @Test
    void enlist_2_sections_no_conflict() {
        //Given 1 student & 2 sections w/ no conflict
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, new Room("Z51", 43));
        Section sec2 = new Section("B", new Schedule(MTH, H0830), new Room("Z52", 25));
        //When student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);
        //Then the 2 sections should be found in the student
        //and student should have ONLY 2 sections
        Collection<Section> sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, sections.size())
        );
    }

    @Test
    void enlist_2_sections_same_schedule() {
        //Given a student & 2 sections w/ same schedule
        Student student = new Student(1);
        Section sec1 = new Section("A", DEFAULT_SCHEDULE, new Room("X12", 43));
        Section sec2 = new Section("B", DEFAULT_SCHEDULE, new Room("X13", 44));
        //When student enlists in both sections
        student.enlist(sec1);
        //Then on the 2nd enlistment an exception should be thrown
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_section_full() {
        Student student1 = new Student(3);
        Student student2 = new Student(4);
        Section sec1 = new Section("C", DEFAULT_SCHEDULE, new Room("S13", 1));
        student1.enlist(sec1);
        assertThrows(MaximumEnrolleesException.class, () -> student2.enlist(sec1));
    }

    @Test
    void cancel_enlisted_section() {
        Student student1 = new Student(5);
        Section sec1 = new Section("D", DEFAULT_SCHEDULE, new Room("X05", 43));
        student1.enlist(sec1);
        student1.cancel(sec1);

        Collection<Section> sections = student1.getSections();
        assertAll(
                () -> assertFalse(sections.contains(sec1)),
                () ->assertEquals(0, sections.size())
        );
    }

    @Test
    void cancel_section_not_enlisted() {
        Student student1 = new Student(6);
        Section sec1 = new Section("E", DEFAULT_SCHEDULE, new Room("Z07", 43));
        Section sec2 = new Section("F", DEFAULT_SCHEDULE, new Room("Z08", 43));
        student1.enlist(sec1);

        Collection<Section> sections = student1.getSections();
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> student1.cancel(sec2)),
                () ->assertEquals(1, sections.size())
        );
    }
}
