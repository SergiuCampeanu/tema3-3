package com.company.repository;
import com.company.domain.Course;
import com.company.domain.Student;
import com.company.exceptions.RepositoryExceptions.StudentRepoExceptions;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentFileRepository implements IFileRepository<Student> {

    private final String fileName;
    private final CrudRepository<Course> courseRepository;

    public StudentFileRepository(String fileName, CrudRepository<Course> courseRepository) {
        this.fileName = fileName;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student findOne(Long id){
        List<Student> studentList = new ArrayList<>();
        this.findAll().forEach(studentList::add);
        return studentList.stream().filter(student -> student.getStudentId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public Iterable<Student> findAll() {
        File studentFile = new File(fileName);
        FileReader studentFileReader;
        try {
            studentFileReader = new FileReader(studentFile);
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }

        BufferedReader studentBufferReader = new BufferedReader(studentFileReader);
        Scanner studentScanner = new Scanner(studentBufferReader);
        List<Student> studentList = new ArrayList<>();
        while (studentScanner.hasNextLine()) {
            String studentLine = studentScanner.nextLine();
            List<String> stringList = List.of(studentLine.split(","));
            List<Long> studentCourseList = new ArrayList<>();
            courseRepository.findAll()
                    .forEach(course -> {
                        if (course.getStudentsEnrolled().stream().filter(student ->
                                student == Integer.parseInt(stringList.get(2))).toList().size() != 0)
                            studentCourseList.add(course.getCourseId());
                    });

            Student s1 = new Student(stringList.get(0), stringList.get(1), Integer.parseInt(stringList.get(2)), Integer.parseInt(stringList.get(3)), studentCourseList);
            studentList.add(s1);
        }

        studentScanner.close();
        try{
            studentBufferReader.close();
            studentFileReader.close();
        }
        catch(Exception e){
            throw new StudentRepoExceptions("the file couldn't be cloe correctly");
        }


        return studentList;
    }

    @Override
    public Student save(Student entity) {
        File studentFile = new File(fileName);
        FileWriter studentFileWriter;
        try {
            studentFileWriter = new FileWriter(studentFile, true);
            BufferedWriter studentBufferWriter = new BufferedWriter(studentFileWriter);
            studentBufferWriter.write(entity.getName() + "," + entity.getFirstName() + "," + entity.getStudentId() + "," + entity.getTotalCredit() + "\n");
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public Student delete(Student entity) throws Exception {

        List<Student> studentList = new ArrayList<>();
        this.findAll().forEach(studentList::add);
        File studentFile = new File(fileName);
        FileWriter studentFileWriter;
        try {
            studentFileWriter = new FileWriter(studentFile, false);
            PrintWriter studentPrintWriter = new PrintWriter(studentFileWriter, false);
            studentPrintWriter.print("");
            studentPrintWriter.close();
            studentFileWriter.close();
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }

        try {
            studentFileWriter = new FileWriter(studentFile, true);
            BufferedWriter studentBufferWriter = new BufferedWriter(studentFileWriter);
            for (Student student : studentList)
                if (student.getStudentId() != entity.getStudentId())
                    studentBufferWriter.write(student.getName() + "," + student.getFirstName() + "," + student.getStudentId() + "," + student.getTotalCredit() + "\n");
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public Student update(Student entity) {

        List<Student> studentList = new ArrayList<>();
        this.findAll().forEach(studentList::add);
        File studentFile = new File(fileName);
        FileWriter studentFileWriter;
        try {
            studentFileWriter = new FileWriter(studentFile, false);
            PrintWriter studentPrintWriter = new PrintWriter(studentFileWriter, false);
            studentPrintWriter.print("");
            studentPrintWriter.close();
            studentFileWriter.close();
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }

        try {
            studentFileWriter = new FileWriter(studentFile, true);
            BufferedWriter studentBufferWriter = new BufferedWriter(studentFileWriter);
            for (Student student : studentList)
                if (student.getStudentId() != entity.getStudentId())
                    studentBufferWriter.write(student.getName() + "," + student.getFirstName() + "," + student.getStudentId() + "," + student.getTotalCredit() + "\n");
                else
                    studentBufferWriter.write(entity.getName() + "," + entity.getFirstName() + "," + entity.getStudentId() + "," + entity.getTotalCredit() + "\n");
        } catch (Exception e) {
            throw new StudentRepoExceptions("file not found");
        }
        return entity;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
