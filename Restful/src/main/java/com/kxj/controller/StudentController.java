package com.kxj.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kxj.entity.Student;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kxj
 * @date 2020/2/13 17:43
 * @desc
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

    /**
     * 修改
     *
     * @param id
     * @param student
     * @return
     * @PathVariable(value = "id")  可加可不加
     */
    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable(value = "id") Integer id,
                                 @Valid @RequestBody Student student,
                                 BindingResult bindingResult) {
        System.out.println(student);
        bindingResult.getAllErrors().stream().forEach(error -> {
            System.out.println(error.getDefaultMessage());
        });
        return student;
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping(value = "student/{id}")
    public void deleteMapping(@PathVariable(value = "id") Integer id) {
        System.out.println(id);
    }


    @GetMapping("/student/time")
    public List<Student> getStudents22(String time) {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        return students;
    }

}
