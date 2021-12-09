package com.company.domain;
import java.util.List;
import java.util.Objects;

/**
 * @author sncam
 */
public class Student extends Person {

    private long studentId;
    private int totalCredit;
    private List<Long> enrolledCourses;


    public Student(String name, String firstName, long studentId, int totalCredit, List<Long> enrolledCourses) {
        super(name, firstName);
        this.studentId = studentId;
        this.totalCredit = totalCredit;
        this.enrolledCourses = enrolledCourses;
    }

    public Student(String name, String firstName) {
        super(name, firstName);
    }

    /*
    getter and setter
    */

    /**
     * @return studentId
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the id of the student
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /**
     * @return totalCredit
     */
    public int getTotalCredit() {
        return totalCredit;
    }

    /**
     *
     * @param totalCredit credits of the student
     */
    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    /**
     * @return enrolledCourses
     */
    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * @param enrolledCourses courses where the student goes
     */
    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    /**
     *
     * @return tostring
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", totalCredit=" + totalCredit +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
}
