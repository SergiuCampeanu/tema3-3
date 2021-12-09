package com.company.domain;

import java.util.List;
import java.util.Objects;

/**
 * @author sncam
 */
public class Course {

    private String name;
    private Long teacherId;
    private int maxEnrolled;
    private long courseId;
    private int credits;
    private List<Long> studentsEnrolled;

    public Course(String name, Long teacher, int maxEnrolled, long courseId, int credits, List<Long> studentsEnrolled) {
        this.name = name;
        this.teacherId = teacher;
        this.maxEnrolled = maxEnrolled;
        this.courseId = courseId;
        this.credits = credits;
        this.studentsEnrolled = studentsEnrolled;
    }

    public Course() {
    }

    /*
getter and setter
*/

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return teacher
     */
    public Long getTeacher() {
        return teacherId;
    }

    /**
     * @param teacher, person who teach the course
     */
    public void setTeacher(Long teacher) {
        this.teacherId = teacher;
    }

    /**
     * @return number max of student in the course
     */
    public int getMaxEnrolled() {
        return maxEnrolled;
    }

    /**
     * @param maxEnrolled number max of student in the course
     */
    public void setMaxEnrolled(int maxEnrolled) {
        this.maxEnrolled = maxEnrolled;
    }

    /**
     * @return course id
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * @param courseId, id of the teacher
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * @return credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @param credits, vale of the course
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * @return students that are enrolled
     */
    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    /**
     * @param studentsEnrolled, list of students thate are enrolled
     */
    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }


    /**
     * @return tostring
     */
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacher=" + teacherId +
                ", maxEnrolled=" + maxEnrolled +
                ", courseId=" + courseId +
                ", credits=" + credits +
                ", studentsEnrolled=" + studentsEnrolled +
                '}';
    }
}
