package com.hendisantika.springboot.swagger.controller;

import com.hendisantika.springboot.swagger.model.Student;
import com.hendisantika.springboot.swagger.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hendisantika on 4/24/17.
 */

@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
@Tag(name = "Student",
        description = "This API provides the capability to search Student from a Student Repository")
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StudentService studentService;

    @Operation(
            summary = "Get All Student Data",
            description = "Get All Student Data."
    )
    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllStudents() {
        logger.debug("Getting All students ......");
        List<Student> student = null;
        try {
            student = studentService.getAll();
            logger.debug("Getting All students ...... ::");
        } catch (Exception ex) {
            logger.error("Error occurred in searchStudentById >>", ex, ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(student, HttpStatus.OK);
    }

    @Operation(
            summary = "Search Student by studentId",
            description = "Search Student by studentId."
    )
    @GetMapping(value = "/{studentId}")
    public ResponseEntity<Object> searchStudentById(
            @Parameter(name = "studentId",
                    description = "The Id of the Student to be viewed",
                    required = true)
            @PathVariable Integer studentId) {
        logger.debug("Searching for student with studentId ::{}", studentId);
        Student student = null;
        try {
            student = studentService.getStudentById(studentId);
            logger.debug("Student found with studentId ::" + studentId);
        } catch (Exception ex) {
            logger.error("Error occurred in searchStudentById >>", ex, ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @Operation(summary = "Search for all Students whose age is greater than input age")
    @GetMapping(value = "/greaterThanAge/{age}")
    public ResponseEntity<Object> filterStudentsByAge(
            @Parameter(name = "age",
                    description = "filtering age",
                    required = true) @PathVariable Integer age) {
        List<Student> studentList = null;
        try {
            studentList = studentService.filterByAge(age);
        } catch (Exception ex) {
            logger.error("Error occurred in filterStudentsByAge >>", ex, ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(studentList, HttpStatus.OK);
    }

    @Operation(summary = "Search for all Students who are from input city")
    @GetMapping(value = "/fromCity/{cityName}")
    public ResponseEntity<Object> filterStudentsByCity(
            @Parameter(name = "cityName", description = "filtering city name", required = true)
            @PathVariable String cityName) {
        List<Student> studentList = null;
        try {
            studentList = studentService.filterByCity(cityName);
        } catch (Exception ex) {
            logger.error("Error occurred in filterStudentsByCity >>", ex, ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @Operation(summary = "Search for all students who are from given city and "
            + "whose age are greater than input age")
    @Parameters({
            @Parameter(name = "schoolId", description = "School Id", required = true),
            @Parameter(name = "age", description = "Age of Student", required = true),
            @Parameter(name = "cityName", description = "City of Student", required = true)})
    @GetMapping(value = "/filterByAgeAndCity")
    public ResponseEntity<Object> filterStudentsByAgeAndCity(@RequestHeader(name = "schoolId") String userId,
                                                             @RequestParam Integer age, @RequestParam String cityName) {

        List<Student> studentList = null;
        try {
            studentList = studentService.filterByAgeAndCity(age, cityName);
        } catch (Exception ex) {
            logger.error("Error occurred in filterStudentsByAgeAndCity >>", ex, ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }
}
