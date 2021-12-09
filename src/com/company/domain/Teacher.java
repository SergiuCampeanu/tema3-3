package com.company.domain;

import java.util.List;
import java.util.Objects;

/**
 * @author sncam
 */
public class Teacher extends Person {

    private long teacherId;
    private List<Long> courses;

    public Teacher(String name, String firstName, long teacherId, List<Long> courses) {
        super(name, firstName);
        this.teacherId = teacherId;
        this.courses = courses;
    }

    public Teacher(String name, String firstName) {
        super(name, firstName);
    }
    /*
    getter and setter
    */

    /**
     * @return teacher id
     */
    public long getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId, id of the teacher
     */
    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * @return courses
     */
    public List<Long> getCourses() {
        return courses;
    }

    /**
     * @param courses courses teched by the teacher
     */
    public void setCourses(List<Long> courses) {
        this.courses = courses;
    }

    /**
     *
     * @return tostring
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", courses=" + courses +
                '}';
    }
}