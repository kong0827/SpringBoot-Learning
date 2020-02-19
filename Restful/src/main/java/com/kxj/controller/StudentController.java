package com.kxj.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kxj.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kxj
 * @date 2020/2/13 17:43
 * @Desc
 */
@RestController
public class StudentController {

    @GetMapping("/student")
    @JsonView(Student.simpleView.class)
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        return students;
    }

    @GetMapping("/student2")
    @JsonView(Student.detailView.class)
    public List<Student> getStudents2() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        return students;
    }


}
