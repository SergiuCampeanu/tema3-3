package com.company.repository;

import com.company.domain.Teacher;
import com.company.exceptions.RepositoryExceptions.RepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherJDBCRepository implements CrudRepository<Teacher>{

    private Connection con;

    public TeacherJDBCRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/maptema5","root", "admin");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RepoException("The driver for the database couldn't be loaded");
        }

    }

    @Override
    public Teacher findOne(Long id) throws Exception {
        String findOneQuery = "Select * From teacher Where Teacher_ID=?";
        String name = "", firstname = "";
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                name = resultSet.getString("Name_Teacher");
                firstname = resultSet.getString("Firstname_Teacher");
            }
        }

        String findCoursesId = "Select Course_ID From Course Where Teacher_ID=?";
        List<Long> idsList = new ArrayList<>();

        try(PreparedStatement pstmt = con.prepareStatement(findCoursesId))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                idsList.add((long) resultSet.getInt("Teacher_ID"));
            }
        }

        if(name == "" || firstname == "")
        {
            throw new RepoException("Teacher not Found");
        }

        return new Teacher(name,firstname,id,idsList);
    }

    @Override
    public Iterable<Teacher> findAll() {

        String findOneQuery = "Select * From teacher";
        List<Teacher> teacherList = new ArrayList<>();
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("Teacher_ID");
                String name = resultSet.getString("Name_Teacher");
                String firstName = resultSet.getString("Firstname_Teacher");
                Teacher teacher = new Teacher(name,firstName, id, new ArrayList<>());
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Teacher t: teacherList){
            String findCoursesByTeacherId = "Select Course_ID From Course Where Teacher_ID=?";
            List<Long> idsList = new ArrayList<>();

            try(PreparedStatement pstmt = con.prepareStatement(findCoursesByTeacherId))
            {
                pstmt.setInt(1,(int)t.getTeacherId());
                ResultSet resultSet = pstmt.executeQuery();
                while(resultSet.next()) {
                    idsList.add((long) resultSet.getInt("Teacher_ID"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            t.setCourses(idsList);
        }

        return teacherList;
    }

    @Override
    public Teacher save(Teacher entity) {
        String insertTeacher = "Insert into Teacher values(?,?,?)";
        try(PreparedStatement pstmt = con.prepareStatement(insertTeacher))
        {
            pstmt.setInt(1,(int)entity.getTeacherId());
            pstmt.setString(2,entity.getName());
            pstmt.setString(3,entity.getFirstName());
            int result = pstmt.executeUpdate();
            if(result == 0)
                throw new RepoException("The teacher was not saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public Teacher delete(Teacher entity) throws Exception {

        String updateCourse = "Update course Set Teacher_ID=? Where Teacher_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(updateCourse))
        {
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setInt(2,(int)entity.getTeacherId());
            int result = pstmt.executeUpdate();
            if(result == 0)
            {
                throw new RepoException("The course was not updated");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        String insertTeacher = "Delete From Teacher  Where Teacher_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(insertTeacher))
        {
            pstmt.setInt(1,(int)entity.getTeacherId());
            int result = pstmt.executeUpdate();
            if(result == 0)
                throw new RepoException("The teacher was not deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Teacher update(Teacher entity) {
        String insertTeacher = "Update Teacher set Name_Teacher=?, Firstname_Teacher=? Where Teacher_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(insertTeacher))
        {
            pstmt.setString(1,entity.getName());
            pstmt.setString(2,entity.getFirstName());
            pstmt.setInt(3,(int)entity.getTeacherId());
            int result = pstmt.executeUpdate();
            if(result == 0)
                throw new RepoException("The teacher was not updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
