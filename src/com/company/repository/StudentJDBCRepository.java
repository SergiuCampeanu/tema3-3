package com.company.repository;

import com.company.domain.Student;
import com.company.domain.Teacher;
import com.company.exceptions.RepositoryExceptions.RepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCRepository implements CrudRepository<Student>{

    private Connection con;

    public StudentJDBCRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/maptema5","root", "admin");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RepoException("The driver for the database couldn't be loaded");
        }
    }

    @Override
    public Student findOne(Long id) throws Exception {
        String findOneQuery = "Select * From student Where Student_ID=?";
        String name = "", firstname = "";
        int credits = 0;
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                name = resultSet.getString("Name_Student");
                firstname = resultSet.getString("Firstname_Student");
                credits = resultSet.getInt("TotalCredits");
            }
        }

        String findCoursesId = "Select Course_ID From studentcourses Where Student_ID=?";
        List<Long> idsList = new ArrayList<>();

        try(PreparedStatement pstmt = con.prepareStatement(findCoursesId))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                idsList.add((long) resultSet.getInt("Student_ID"));
            }
        }

        if(name == "" || firstname == "")
        {
            throw new RepoException("Student not Found");
        }

        return new Student(name,firstname,id,credits,idsList);
    }

    @Override
    public Iterable<Student> findAll() {

        String findOneQuery = "Select * From student";
        List<Student> studentList = new ArrayList<>();
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("Student_ID");
                String name = resultSet.getString("Name_Student");
                String firstName = resultSet.getString("Firstname_Student");
                int totalCredits = resultSet.getInt("TotalCredits");
                Student student = new Student(name,firstName, id, totalCredits, new ArrayList<>());
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Student s: studentList){
            String findCoursesByStudentId = "Select Course_ID From studentcourses Where Student_ID=?";
            List<Long> idsList = new ArrayList<>();

            try(PreparedStatement pstmt = con.prepareStatement(findCoursesByStudentId))
            {
                pstmt.setInt(1,(int)s.getStudentId());
                ResultSet resultSet = pstmt.executeQuery();
                while(resultSet.next()) {
                    idsList.add((long) resultSet.getInt("Student_ID"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            s.setEnrolledCourses(idsList);
        }

        return studentList;
    }

    @Override
    public Student save(Student entity) {
        String insertStudent = "Insert into Student values(?,?,?,?)";
        try(PreparedStatement pstmt = con.prepareStatement(insertStudent))
        {
            pstmt.setInt(1,(int)entity.getStudentId());
            pstmt.setString(2,entity.getName());
            pstmt.setString(3,entity.getFirstName());
            pstmt.setInt(4,entity.getTotalCredit());
            int result = pstmt.executeUpdate();
            if(result == 0)
                throw new RepoException("The student was not saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public Student delete(Student entity) throws Exception {
        return null;
    }

    @Override
    public Student update(Student entity) {
        return null;
    }
}
