package com.company.repository;

import com.company.controller.CourseController;
import com.company.controller.MainController;
import com.company.controller.StudentController;
import com.company.controller.TeacherController;
import com.company.domain.Course;
import com.company.domain.Student;
import com.company.domain.Teacher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentInMemoryRepoTest {

    @Test
    void findOne() {
        Student s1 = new Student("S1", "SF1", 1, 0, null);
        Student s2 = new Student("S2", "SF2", 2, 0, null);
        Student s3 = new Student("S3", "SF3", 3, 0, null);
        StudentInMemoryRepo studentInMemoryRepo = new StudentInMemoryRepo(Arrays.asList(s1,s2,s3));

        Student student = studentInMemoryRepo.findOne(Long.parseLong("1"));
        assert student != null;
    }

    @Test
    void findAll() {
        Student s1 = new Student("S1", "SF1", 1, 0, null);
        Student s2 = new Student("S2", "SF2", 2, 0, null);
        Student s3 = new Student("S3", "SF3", 3, 0, null);
        StudentInMemoryRepo studentInMemoryRepo = new StudentInMemoryRepo(Arrays.asList(s1,s2,s3));

        List<Student> studentList = (List<Student>) studentInMemoryRepo.findAll();
        assertEquals(3, studentList.size());
    }

    @Test
    void save() {
        StudentInMemoryRepo studentInMemoryRepo = new StudentInMemoryRepo(new ArrayList<>());

        Student s4 = new Student("S4", "SF4", 4, 0, null);
        studentInMemoryRepo.save(s4);

        List<Student> studentList = (List<Student>) studentInMemoryRepo.findAll();
        assertEquals(1, studentList.size());

    }

    @Test
    void delete() {
        Student s1 = new Student("S1", "SF1", 1, 0, null);
        Student s2 = new Student("S2", "SF2", 2, 0, null);
        Student s3 = new Student("S3", "SF3", 3, 0, null);
        StudentInMemoryRepo studentInMemoryRepo = new StudentInMemoryRepo(new ArrayList<>());

        studentInMemoryRepo.save(s1);
        studentInMemoryRepo.save(s2);
        studentInMemoryRepo.save(s3);

        List<Student> studentList = (List<Student>) studentInMemoryRepo.findAll();
        assertEquals(3, studentList.size());

        studentInMemoryRepo.delete(s2);
        assertEquals(2, studentList.size());

    }

    @Test
    void update() {
        Student s1 = new Student("S1", "SF1", 1, 0, null);
        StudentInMemoryRepo studentInMemoryRepo = new StudentInMemoryRepo(List.of(s1));
        Student s2 = new Student("S2", "SF2", 1, 0, null);

        studentInMemoryRepo.update(s2);
        assertEquals("S2", studentInMemoryRepo.findOne(Long.parseLong("1")).getName() );

    }

    @Test
    void addCourseToStudent() {
        List<Long> listStudentsCourse1 = new ArrayList<>();
        List<Long> listStudentsCourse2 = new ArrayList<>();
        List<Long> listStudentsCourse3 = new ArrayList<>();

        List<Long> listCoursesTeacher1 = new ArrayList<>();
        List<Long> listCoursesTeacher2 = new ArrayList<>();
        List<Long> listCoursesTeacher3 = new ArrayList<>();

        List<Long> listCoursesStudent1 = new ArrayList<>();
        List<Long> listCoursesStudent2 = new ArrayList<>();
        List<Long> listCoursesStudent3 = new ArrayList<>();


        Student s1 = new Student("S1", "SF1", 1, 0, null);
        Student s2 = new Student("S2", "SF2", 2, 0, null);
        Student s3 = new Student("S3", "SF3", 3, 0, null);

        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        Teacher t2 = new Teacher("T2", "TF2", 2, null);
        Teacher t3 = new Teacher("T3", "TF3", 3, null);

        Course c1 = new Course("C1", null, 100, 1, 6, null);
        Course c2 = new Course("C2", null, 2, 2, 25, null);
        Course c3 = new Course("C3", null, 50, 3, 4, null);

        List<Course> courseList = new ArrayList<>(Arrays.asList(c1,c2,c3));

        listCoursesStudent1.add(c1.getCourseId());
        listCoursesStudent1.add(c3.getCourseId());

        listCoursesStudent2.add(c2.getCourseId());
        listCoursesStudent2.add(c3.getCourseId());

        listCoursesStudent3.add(c1.getCourseId());

        s1.setEnrolledCourses(listCoursesStudent1);
        for(Long courseId: listCoursesStudent1)
        {
            Course foundCourse = courseList
                    .stream()
                    .filter(course -> course.getCourseId() == courseId)
                    .toList()
                    .get(0);
            s1.setTotalCredit(s1.getTotalCredit() + foundCourse.getCredits());
        }

        s2.setEnrolledCourses(listCoursesStudent2);
        for(Long courseId: listCoursesStudent2)
        {
            Course foundCourse = courseList
                    .stream()
                    .filter(course -> course.getCourseId() == courseId)
                    .toList()
                    .get(0);
            s2.setTotalCredit(s2.getTotalCredit() + foundCourse.getCredits());
        }

        s3.setEnrolledCourses(listCoursesStudent3);
        for(Long courseId: listCoursesStudent3)
        {
            Course foundCourse = courseList
                    .stream()
                    .filter(course -> course.getCourseId() == courseId)
                    .toList()
                    .get(0);
            s3.setTotalCredit(s3.getTotalCredit() + foundCourse.getCredits());
        }


        listCoursesTeacher1.add(c1.getCourseId());
        listCoursesTeacher2.add(c2.getCourseId());
        listCoursesTeacher3.add(c3.getCourseId());

        t1.setCourses(listCoursesTeacher1);
        t2.setCourses(listCoursesTeacher2);
        t3.setCourses(listCoursesTeacher3);

        listStudentsCourse1.add(s1.getStudentId());
        listStudentsCourse1.add(s2.getStudentId());


        listStudentsCourse2.add(s1.getStudentId());

        listStudentsCourse3.add(s2.getStudentId());
        listStudentsCourse3.add(s3.getStudentId());

        c1.setStudentsEnrolled(listStudentsCourse1);
        c2.setStudentsEnrolled(listStudentsCourse2);
        c3.setStudentsEnrolled(listStudentsCourse3);


        List<Student> studentList = new ArrayList<>(Arrays.asList(s1,s2,s3));
        List<Teacher> teacherList = new ArrayList<>(Arrays.asList(t1,t2,t3));

        CourseInMemoryRepo courseRepo = new CourseInMemoryRepo(courseList);
        TeacherInMemoryRepo teacherRepo = new TeacherInMemoryRepo(teacherList);
        StudentInMemoryRepo studentRepo = new StudentInMemoryRepo(studentList);

        CourseController courseController = new CourseController(courseRepo);
        TeacherController teacherController = new TeacherController(teacherRepo);
        StudentController studentController = new StudentController(studentRepo);

        MainController mainController = new MainController(courseController, studentController, teacherController);
        mainController.registerStudentToCourse(Long.parseLong("3"), (Long.parseLong("1")));

        List<Student> newCourseList = (List<Student>) mainController.getAllStudentsByCourseId(Long.parseLong("1"));
        assertEquals(3,newCourseList.size());
    }
}