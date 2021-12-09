package com.company.repository;

import com.company.domain.Course;
import com.company.domain.Student;
import com.company.exceptions.RepositoryExceptions.RepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseJDBCRepository implements CrudRepository<Course>{

    private Connection con;

    public CourseJDBCRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/maptema5","root", "admin");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RepoException("The driver for the database couldn't be loaded");
        }
    }

    @Override
    public Course findOne(Long id) throws Exception {
        String findOneQuery = "Select * From course Where Course_ID=?";
        String name = "";
        Long teacher = null;
        int credits = 0;
        int maxEnrolled = 0;
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                name = resultSet.getString("Name_Course");
                teacher = resultSet.getLong("Teacher_ID");
                credits = resultSet.getInt("Credits");
                maxEnrolled = resultSet.getInt("MaxEnrolled");
            }
        }

        String findCoursesId = "Select Student_ID From studentcourses Where Course_ID=?";
        List<Long> idsList = new ArrayList<>();

        try(PreparedStatement pstmt = con.prepareStatement(findCoursesId))
        {
            pstmt.setInt(1, id.intValue());
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                idsList.add((long) resultSet.getInt("Course_ID"));
            }
        }

        if(name == "")
        {
            throw new RepoException("Course not Found");
        }

        return new Course(name,teacher,maxEnrolled,id,credits,idsList);
    }

    @Override
    public Iterable<Course> findAll() {

        String findOneQuery = "Select * From course";
        List<Course> courseList = new ArrayList<>();
        try(PreparedStatement pstmt = con.prepareStatement(findOneQuery))
        {
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("Course_ID");
                String name = resultSet.getString("Name_Course");
                Long teacher = resultSet.getLong("Teacher_ID");
                int credits = resultSet.getInt("Credits");
                int maxEnrolled = resultSet.getInt("MaxEnrolled");
                Course course = new Course(name, teacher, maxEnrolled, id, credits, new ArrayList<>());
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Course c: courseList){
            String findCoursesByStudentId = "Select Student_ID From studentcourses Where Course_ID=?";
            List<Long> idsList = new ArrayList<>();

            try(PreparedStatement pstmt = con.prepareStatement(findCoursesByStudentId))
            {
                pstmt.setInt(1,(int)c.getCourseId());
                ResultSet resultSet = pstmt.executeQuery();
                while(resultSet.next()) {
                    idsList.add((long) resultSet.getInt("Course_ID"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            c.setStudentsEnrolled(idsList);
        }

        return courseList;
    }

    @Override
    public Course save(Course entity) {
        String insertCourse = "Insert into course values(?,?,?,?,?)";
        try(PreparedStatement pstmt = con.prepareStatement(insertCourse))
        {
            pstmt.setInt(1,(int)entity.getCourseId());
            pstmt.setString(2,entity.getName());
            pstmt.setLong(3,entity.getTeacher());
            pstmt.setInt(4,entity.getCredits());
            pstmt.setInt(5,entity.getMaxEnrolled());
            int result = pstmt.executeUpdate();
            if(result== 0)
            {
                throw new RepoException("The course was not saved");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Course delete(Course entity) throws Exception {

        String deleteStudentCourses = "Delete From studentcourses Where Course_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(deleteStudentCourses))
        {
            pstmt.setInt(1,(int)entity.getCourseId());
            int result = pstmt. executeUpdate();
            if(result== 0)
            {
                throw new RepoException("The course was not deleted in studentcourses");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        String deleteCourse = "Delete From course Where Course_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(deleteCourse))
        {
            pstmt.setInt(1,(int)entity.getCourseId());
            int result = pstmt. executeUpdate();
            if(result== 0)
            {
                throw new RepoException("The course was not deleted");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Course update(Course entity) {
        String updateCourse = "Update course Set Name_Course=?, Teacher_ID=?, Credits=?, MaxEnrolled=? Where Course_ID=?";
        try(PreparedStatement pstmt = con.prepareStatement(updateCourse))
        {
            pstmt.setString(1,entity.getName());
            pstmt.setLong(2,entity.getTeacher());
            pstmt.setInt(3,entity.getCredits());
            pstmt.setInt(4,entity.getMaxEnrolled());
            pstmt.setInt(5,(int)entity.getCourseId());
            int result = pstmt.executeUpdate();
            if(result == 0)
            {
                throw new RepoException("The course was not updated");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
