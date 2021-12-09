package com.company.repository;
import com.company.domain.Teacher;
import com.company.exceptions.RepositoryExceptions.TeacherRepoExceptions;

import java.util.List;

/**
 * @author sncam
 */
public class TeacherInMemoryRepo implements CrudRepository<Teacher>{
    private List<Teacher> teacher;

    public TeacherInMemoryRepo(List<Teacher> teacher) {
        this.teacher = teacher;
    }

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null if there is no entity with the given id
     * @throws TeacherRepoExceptions if the id is null or teacher list is empty
     */
    @Override
    public Teacher findOne(Long id) {

        if (teacher.isEmpty())
        {
            throw new TeacherRepoExceptions("Teacher list is empty");
        }
        if(id==null)
        {
            throw new TeacherRepoExceptions("Id can't be null");
        }
        else
        {
            for (Teacher t : teacher) {
                if (id == t.getTeacherId()) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<Teacher> findAll() {

        return teacher;
    }

    /**
     * @param entity entity must be not null
     * @return null if the given entity is saved, otherwise returns the entity (id already exists)
     */
    @Override
    public Teacher save(Teacher entity) {
        for(Teacher t:teacher)
        {
            if(t.equals(entity))
            {
                return t;
            }
        }
        teacher.add(entity);
        return null;

    }

    /**
     * @param entity entity must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws TeacherRepoExceptions if teacher list is empty
     */
    @Override
    public Teacher delete(Teacher entity) {

        if(teacher.isEmpty())
        {
            throw new TeacherRepoExceptions("Teacher list is empty");
        }
        else
        {
            for (Teacher t : teacher) {

                if (t.equals(entity)) {
                    teacher.remove(entity);
                    return entity;
                }
            }
        }
        return null;
    }

    /**
     * @param entity entity must not be null
     * @return null if the entity is updated, otherwise returns the entity
     */
    @Override
    public Teacher update(Teacher entity) {

        for(Teacher teacher: teacher) {
            if (teacher.getTeacherId() == entity.getTeacherId()) {
                teacher.setName(entity.getName());
                teacher.setFirstName(entity.getFirstName());
                teacher.setCourses(entity.getCourses());
                return null;
            }
        }
        return entity;
    }
}

