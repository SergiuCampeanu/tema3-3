package com.company;

import com.company.controller.CourseController;
import com.company.controller.MainController;
import com.company.controller.StudentController;
import com.company.controller.TeacherController;
import com.company.domain.Course;
import com.company.domain.Student;
import com.company.domain.Teacher;
import com.company.repository.*;
import com.company.view.RegistrationSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        /*List<Student> listStudentsCourse1 = new ArrayList<>();
        List<Student> listStudentsCourse2 = new ArrayList<>();
        List<Student> listStudentsCourse3 = new ArrayList<>();

        List<Course> listCoursesTeacher1 = new ArrayList<>();
        List<Course> listCoursesTeacher2 = new ArrayList<>();
        List<Course> listCoursesTeacher3 = new ArrayList<>();

        List<Course> listCoursesStudent1 = new ArrayList<>();
        List<Course> listCoursesStudent2 = new ArrayList<>();
        List<Course> listCoursesStudent3 = new ArrayList<>();


        Student s1 = new Student("S1", "SF1", 1, 0, null);
        Student s2 = new Student("S2", "SF2", 2, 0, null);
        Student s3 = new Student("S3", "SF3", 3, 0, null);

        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        Teacher t2 = new Teacher("T2", "TF2", 2, null);
        Teacher t3 = new Teacher("T3", "TF3", 3, null);

        Course c1 = new Course("C1", t1, 100, 1, null, 6);
        Course c2 = new Course("C2", t2, 2, 2, null, 25);
        Course c3 = new Course("C3", t3, 50, 3, null, 4);


        listCoursesStudent1.add(c1);
        listCoursesStudent1.add(c3);

        listCoursesStudent2.add(c2);
        listCoursesStudent2.add(c3);

        listCoursesStudent3.add(c1);

        s1.setEnrolledCourses(listCoursesStudent1);
        for(Course course: listCoursesStudent1)
            s1.setTotalCredit(s1.getTotalCredit()+ course.getCredits());

        s2.setEnrolledCourses(listCoursesStudent2);
        for(Course course: listCoursesStudent2)
            s2.setTotalCredit(s2.getTotalCredit()+ course.getCredits());

        s3.setEnrolledCourses(listCoursesStudent3);
        for(Course course: listCoursesStudent3)
            s3.setTotalCredit(s3.getTotalCredit()+ course.getCredits());

        listCoursesTeacher1.add(c1);
        listCoursesTeacher2.add(c2);
        listCoursesTeacher3.add(c3);

        t1.setCourses(listCoursesTeacher1);
        t2.setCourses(listCoursesTeacher2);
        t3.setCourses(listCoursesTeacher3);

        listStudentsCourse1.add(s1);
        listStudentsCourse1.add(s2);
        listStudentsCourse1.add(s3);

        listStudentsCourse2.add(s1);

        listStudentsCourse3.add(s2);
        listStudentsCourse3.add(s3);

        c1.setStudentsEnrolled(listStudentsCourse1);
        c2.setStudentsEnrolled(listStudentsCourse2);
        c3.setStudentsEnrolled(listStudentsCourse3);


        List<Course> courseList = new ArrayList<>(Arrays.asList(c1,c2,c3));
        List<Student> studentList = new ArrayList<>(Arrays.asList(s1,s2,s3));
        List<Teacher> teacherList = new ArrayList<>(Arrays.asList(t1,t2,t3));

        CourseInMemoryRepo courseRepo = new CourseInMemoryRepo(courseList);
        TeacherInMemoryRepo teacherRepo = new TeacherInMemoryRepo(teacherList);
        StudentInMemoryRepo studentRepo = new StudentInMemoryRepo(studentList);*/

//        CourseFileRepository courseRepo = new CourseFileRepository("D:\\University\\Info\\materii\\MAP\\tema\\tema3\\src\\com\\company\\RepoData\\Course.txt");
//        CrudRepository<Student> studentRepo = new StudentFileRepository("D:\\University\\Info\\materii\\MAP\\tema\\tema3\\src\\com\\company\\RepoData\\Student.txt", courseRepo);
//        CrudRepository<Teacher> teacherRepo = new TeacherFileRepository("D:\\University\\Info\\materii\\MAP\\tema\\tema3\\src\\com\\company\\RepoData\\Teacher.txt",courseRepo);
//        courseRepo.setStudentRepo(studentRepo);
//        courseRepo.setTeacherRepo(teacherRepo);

        CrudRepository<Course> courseRepo = new CourseJDBCRepository();
        CrudRepository<Student> studentRepo = new StudentJDBCRepository();
        CrudRepository<Teacher> teacherRepo = new TeacherJDBCRepository();


        CourseController courseController = new CourseController(courseRepo);
        TeacherController teacherController = new TeacherController(teacherRepo);
        StudentController studentController = new StudentController(studentRepo);

        MainController mainController = new MainController(courseController, studentController, teacherController);

        RegistrationSystem registrationSystem = new RegistrationSystem(mainController);
        registrationSystem.startApplication();
    }
}
