package com.company.repository;

import com.company.domain.Course;
import com.company.domain.Teacher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherInMemoryRepoTest {

    @Test
    void findOne() {
        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        Teacher t2 = new Teacher("T2", "TF2", 2, null);
        Teacher t3 = new Teacher("T3", "TF3", 3, null);
        TeacherInMemoryRepo teacherInMemoryRepo = new TeacherInMemoryRepo(Arrays.asList(t1,t2,t3));

        Teacher teacher = teacherInMemoryRepo.findOne(Long.parseLong("1"));
        assert teacher != null;
    }

    @Test
    void findAll() {

        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        Teacher t2 = new Teacher("T2", "TF2", 2, null);
        Teacher t3 = new Teacher("T3", "TF3", 3, null);
        TeacherInMemoryRepo teacherInMemoryRepo = new TeacherInMemoryRepo(Arrays.asList(t1,t2,t3));

        List<Teacher> teacherList = (List<Teacher>) teacherInMemoryRepo.findAll();
        assertEquals(3, teacherList.size());
    }

    @Test
    void save() {
        TeacherInMemoryRepo teacherInMemoryRepo = new TeacherInMemoryRepo(new ArrayList<>());

        Teacher t4 = new Teacher("T4", "TF4", 4, null);
        teacherInMemoryRepo.save(t4);

        List<Teacher> teacherList = (List<Teacher>) teacherInMemoryRepo.findAll();
        assertEquals(1, teacherList.size());
    }

    @Test
    void delete() {
        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        Teacher t2 = new Teacher("T2", "TF2", 2, null);
        Teacher t3 = new Teacher("T3", "TF3", 3, null);
        TeacherInMemoryRepo teacherInMemoryRepo = new TeacherInMemoryRepo(new ArrayList<>());
        teacherInMemoryRepo.save(t1);
        teacherInMemoryRepo.save(t2);
        teacherInMemoryRepo.save(t3);

        List<Teacher> teacherList = (List<Teacher>) teacherInMemoryRepo.findAll();
        assertEquals(3, teacherList.size());

        teacherInMemoryRepo.delete(t2);
        assertEquals(2, teacherList.size());
    }

    @Test
    void update() {
        Teacher t1 = new Teacher("T1", "TF1", 1, null);
        TeacherInMemoryRepo teacherInMemoryRepo = new TeacherInMemoryRepo(List.of(t1));
        Teacher t2 = new Teacher("T2", "TF2", 1, null);

        teacherInMemoryRepo.update(t2);
        assertEquals("T2", teacherInMemoryRepo.findOne(Long.parseLong("1")).getName() );
    }
}