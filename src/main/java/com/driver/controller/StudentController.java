package com.driver.controller;

import com.driver.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
public class StudentController {

    //Add required annotations
    public ResponseEntity getStudentByEmail(@RequestParam("email") String email){
        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    public ResponseEntity getStudentById(@RequestParam("id") int id){

        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    public ResponseEntity createStudent(@RequestBody Student student){

        return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    public ResponseEntity updateStudent(@RequestBody Student student){

        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    public ResponseEntity deleteStudent(@RequestParam("id") int id){

        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }

}
