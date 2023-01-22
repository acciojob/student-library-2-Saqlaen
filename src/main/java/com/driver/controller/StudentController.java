package com.driver.controller;

import com.driver.models.Student;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
public class StudentController {

    @Autowired
    StudentService studentservice;

    //Add required annotations
    @GetMapping(" /student/studentByEmail")
    public ResponseEntity<?> getStudentByEmail(@RequestParam("email") String email){
        this.studentservice.getDetailsByEmail( email );
        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    @GetMapping("/student/studentById")
    public ResponseEntity<?> getStudentById(@RequestParam("id") int id){
        this.studentservice.getDetailsById( id );
        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        this.studentservice.createStudent( student );
        return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    @PutMapping("/student")
    public ResponseEntity<?> updateStudent(@RequestBody Student student){
        this.studentservice.updateStudent( student );
        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @DeleteMapping("/student")
    public ResponseEntity<?> deleteStudent(@RequestParam("id") int id){
        this.studentservice.deleteStudent( id );
        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }

}
