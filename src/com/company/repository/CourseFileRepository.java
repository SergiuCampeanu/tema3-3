package com.company.repository;

import com.company.domain.Course;
import com.company.domain.Person;
import com.company.domain.Student;
import com.company.domain.Teacher;
import com.company.exceptions.RepositoryExceptions.CourseRepoExceptions;
import com.company.exceptions.RepositoryExceptions.StudentRepoExceptions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseFileRepository implements IFileRepository<Course> {

    private CrudRepository<Student> studentRepo;
    private CrudRepository<Teacher> teacherRepo;
    private String fileName;

    public CourseFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Course findOne(Long id) {
        List<Course> courseList = new ArrayList<>();
        this.findAll().forEach(courseList::add);
        return courseList.stream().filter(course -> course.getCourseId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public Iterable<Course> findAll() {
        File courseFile = new File(fileName);
        FileReader courseFileReader;
        try {
            courseFileReader = new FileReader(courseFile);
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }

        BufferedReader courseBufferReader = new BufferedReader(courseFileReader);
        Scanner courseScanner = new Scanner(courseBufferReader);
        List<Course> courseList = new ArrayList<>();
        while (courseScanner.hasNextLine()) {
            String courseLine = courseScanner.nextLine();
            List<String> stringList = List.of(courseLine.split(","));

            String listOfIds = stringList.get(stringList.size() - 1);
            listOfIds = listOfIds.substring(1, listOfIds.length() - 1);

            List<Long> studentIdList = Stream.of(listOfIds.split(";")).map(Long::parseLong).toList();

            Course c1 = new Course(stringList.get(0), Long.parseLong(stringList.get(1)), Integer.parseInt(stringList.get(2)), Integer.parseInt(stringList.get(3)), Integer.parseInt(stringList.get(3)), studentIdList);
            courseList.add(c1);
        }
        return courseList;
    }

    @Override
    public Course save(Course entity) {
        File courseFile = new File(fileName);
        FileWriter courseFileWriter;
        try {
            courseFileWriter = new FileWriter(courseFile, true);
            BufferedWriter courseBufferWriter = new BufferedWriter(courseFileWriter);
            courseBufferWriter.write(entity.getName() + "," + entity.getTeacher() + "," + entity.getMaxEnrolled() + "," + entity.getCourseId() + "," + entity.getCredits() + "\n");
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public Course delete(Course entity) throws Exception {
        List<Course> courseList = new ArrayList<>();
        this.findAll().forEach(courseList::add);
        File courseFile = new File(fileName);
        FileWriter courseFileWriter;
        try {
            courseFileWriter = new FileWriter(courseFile, false);
            PrintWriter coursePrintWriter = new PrintWriter(courseFileWriter, false);
            coursePrintWriter.print("");
            coursePrintWriter.close();
            courseFileWriter.close();
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }

        try {
            courseFileWriter = new FileWriter(courseFile, true);
            BufferedWriter courseBufferWriter = new BufferedWriter(courseFileWriter);
            for (Course course : courseList)
                if (course.getCourseId() != entity.getCourseId())
                    courseBufferWriter.write(course.getName() + "," + course.getTeacher() + "," + course.getMaxEnrolled() + "," + course.getCourseId() + "," + course.getCredits() + "\n");
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public Course update(Course entity) {
        List<Course> courseList = new ArrayList<>();
        this.findAll().forEach(courseList::add);
        File courseFile = new File(fileName);
        FileWriter courseFileWriter;
        try {
            courseFileWriter = new FileWriter(courseFile, false);
            PrintWriter coursePrintWriter = new PrintWriter(courseFileWriter, false);
            coursePrintWriter.print("");
            coursePrintWriter.close();
            courseFileWriter.close();
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }

        try {
            courseFileWriter = new FileWriter(courseFile, true);
            BufferedWriter courseBufferWriter = new BufferedWriter(courseFileWriter);
            for (Course course : courseList)
                if (course.getCourseId() != entity.getCourseId())
                    courseBufferWriter.write(course.getName() + "," + course.getTeacher() + "," + course.getMaxEnrolled() + "," + course.getCourseId() + "," + course.getCredits() + "\n");
                else
                    courseBufferWriter.write(entity.getName() + "," + entity.getTeacher() + "," + entity.getMaxEnrolled() + "," + entity.getCourseId() + "," + entity.getCredits() + "\n");
        } catch (Exception e) {
            throw new CourseRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setStudentRepo(CrudRepository<Student> studentRepo) {
        this.studentRepo = studentRepo;
    }

    public void setTeacherRepo(CrudRepository<Teacher> teacherRepo) {
        this.teacherRepo = teacherRepo;
    }
}
