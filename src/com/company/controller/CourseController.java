package com.company.controller;
import com.company.domain.Course;
import com.company.domain.Student;
import com.company.exceptions.ControllerExceptions.ControllerExceptions;
import com.company.repository.CourseInMemoryRepo;
import com.company.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sncam
 */
public class CourseController {
    private final CrudRepository<Course> repository;

    public CourseController(CrudRepository<Course> courseInMemoryRepo){
        this.repository = courseInMemoryRepo;
    }

    /**
     *
     * @param courseId id of the course
     * @return the course with the same id
     */
    public Course findCourseById(Long courseId) {
        try{
            return this.repository.findOne(courseId);
        }
        catch (Exception e)
        {
            throw new ControllerExceptions(e.getMessage());
        }

    }

    /**
     *
     * @param courseId id of the course
     * @param student object student
     * @return true or false if the student was added to the course or not
     */
    public boolean addStudentToCourse(Long courseId, Student student) {
        try{
            Course updatedCourse = this.repository.findOne(courseId);
            updatedCourse.getStudentsEnrolled().add(student.getStudentId());
            this.repository.update(updatedCourse);
        }
        catch (Exception e)
        {
            throw new ControllerExceptions("Error");
        }
        return true;
    }

    /**
     *
     * @return all the course
     */
    public Iterable<Course> getAllCourses() {
        return this.repository.findAll();
    }

    /**
     *
     * @return all the course with available places
     */
    public Iterable<Course> getAvailableCourses() {
        Iterable<Course> courseList = this.repository.findAll();
        List<Course> availableCourse = new ArrayList<>();
        for (Course c:courseList) {
            if(c.getStudentsEnrolled().size() < c.getMaxEnrolled())
            {
                availableCourse.add(c);
            }
        }
        return availableCourse;
    }

    /**
     *
     * @param course object course
     * @return the updated course
     */
    public Course updateCourse(Course course) {
       return this.repository.update(course);
    }

    /**
     *  run the methode emptylist of the repo
     * @param courseId the id of the course
     */
    public void emptyCourseStudentList(Long courseId){

        try{
            Course updatedCourse = this.repository.findOne(courseId);
            updatedCourse.setStudentsEnrolled(new ArrayList<>());
            this.repository.update(updatedCourse);
        }
        catch (Exception e) {
            throw new ControllerExceptions("Error");
        }
    }

    /**
     *
     * @return sorted list of course
     */
    public List<Course> getSortCoursesByName(){
        List<Course> courseList = (List<Course>) this.repository.findAll();
        return courseList.stream().sorted((Course c1, Course c2)->c1.getName().compareTo(c2.getName())).toList();

    }

    /**
     *
     * @param maxCredit maximum number of credits that a course can have
     * @return the filtered list of course
     */
    public List<Course> getFilteredCoursesByCreditsMax(int maxCredit){
        List<Course> courseList = (List<Course>) this.repository.findAll();
        return courseList.stream().filter(s1->s1.getCredits()<maxCredit).toList();
    }

    /**
     *
     * @param minCredit minimum number of credits that a course can have
     * @return the filtered list of course
     */
    public List<Course> getFilteredCoursesByCreditsMin(int minCredit){
        List<Course> courseList = (List<Course>) this.repository.findAll();
        return courseList.stream().filter(s1->s1.getCredits()>minCredit).toList();
    }
}
