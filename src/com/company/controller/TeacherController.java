package com.company.controller;
import com.company.domain.Teacher;
import com.company.exceptions.ControllerExceptions.ControllerExceptions;
import com.company.repository.CrudRepository;
import com.company.repository.TeacherInMemoryRepo;

/**
 * @author sncam
 */
public class TeacherController {
    private CrudRepository<Teacher> repository;

    public TeacherController(CrudRepository<Teacher> teacherRepo) {
        this.repository = teacherRepo;
    }

    public Teacher findById(long teacherId){
        try{
            return this.repository.findOne(teacherId);
        }
        catch (Exception e){
            throw new ControllerExceptions("Error");
        }
    }

    public Teacher updateTeacher(Teacher teacher) {
        return this.repository.update(teacher);
    }
}
