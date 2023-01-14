package com.driver.services;

import com.driver.models.Student;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = null;

        return student;
    }

    public Student getDetailsById(int id){
        Student student = null;

        return student;
    }

    public void createStudent(Student student){

    }

    public void updateStudent(Student student){

    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
    }
}
