package com.stsweng.enlistment;

import org.apache.commons.lang3.*;

import java.util.*;

class Student {
    private final int studentNumber;
    private final Collection<Section> sections = new HashSet<>();

    Student(int studentNumber, Collection<Section> sections) {
        if (studentNumber < 0) {
            throw new IllegalArgumentException(
                    "studentNumber should be non-negative, was: " + studentNumber);
        }

        if (sections == null) {
            throw new NullPointerException();
        }

        this.studentNumber = studentNumber;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
    }

    Student(int studentNumber) {
        this(studentNumber, Collections.emptyList());
    }

    void enlist(Section newSection) {
        Validate.notNull(newSection);
        //loop through all current sections, check for same sked
        sections.forEach(currSection -> currSection.checkForConflict(newSection));
        newSection.getRoom().checkIfMax();
        this.sections.add(newSection);
        newSection.getRoom().addEnrollee();
    }

    void cancel(Section selectedSection) {
        Validate.notNull(selectedSection);
        if (sections.contains(selectedSection)) {
            sections.remove(selectedSection);
            selectedSection.getRoom().removeEnrollee();
        }

        else {
            throw new IllegalArgumentException("You are currently not enrolled in section " + selectedSection);
        }
    }

    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return "Student# " + studentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return studentNumber == student.studentNumber;
    }

    @Override
    public int hashCode() {
        return studentNumber;
    }
}
