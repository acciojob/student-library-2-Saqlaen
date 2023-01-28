package com.driver.services;

import com.driver.models.Card;
import com.driver.models.Student;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;


    public Student getDetailsByEmail(String email){
        Student student = this.studentRepository4.findByEmailId( email );
        return student;
    }

    public Student getDetailsById(int id){
        Optional<Student> student = this.studentRepository4.findById( id );
        return student.get();
    }

    public void createStudent(Student student){
        Card cardGeneratedForStudent = this.cardService4.createAndReturn( student );
        this.studentRepository4.save( student );
    }

    public void updateStudent(Student student){
        this.studentRepository4.updateStudentDetails( student );
    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
        this.cardService4.deactivateCard(id);
    	this.studentRepository4.deleteCustom( id );
        
    }
}
