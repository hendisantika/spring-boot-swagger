package com.hendisantika.springboot.swagger.controller;

import com.hendisantika.springboot.swagger.model.Student;
import com.hendisantika.springboot.swagger.service.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hendisantika on 4/24/17.
 */

@RestController
@RequestMapping(value = "/students")
@Api(value = "API to search Student from a Student Repository by different search parameters",
        description = "This API provides the capability to search Student from a Student Repository", produces = "application/json")
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "Get All Student", produces = "application/json")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
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

    @ApiOperation(value = "Search Student by studentId", produces = "application/json")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public ResponseEntity<Object> searchStudentById(
            @ApiParam(name = "studentId",
                    value = "The Id of the Student to be viewed",
                    required = true)
            @PathVariable Integer studentId) {
        logger.debug("Searching for student with studentId ::" + studentId);
        Student student = null;
        try {
            student = studentService.getStudentById(studentId);
            logger.debug("Student found with studentId ::" + studentId);
        } catch (Exception ex) {
            logger.error("Error occurred in searchStudentById >>", ex, ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(student, HttpStatus.OK);
    }

    @ApiOperation(value = "Search for all Students whose age is greater than input age", produces = "application/json")
    @RequestMapping(value = "/greaterThanAge/{age}", method = RequestMethod.GET)
    public ResponseEntity<Object> filterStudentsByAge(
            @ApiParam(name = "age",
                    value = "filtering age",
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

    @ApiOperation(value = "Search for all Students who are from input city", produces = "application/json")
    @RequestMapping(value = "/fromCity/{cityName}", method = RequestMethod.GET)
    public ResponseEntity<Object> filterStudentsByCity(
            @ApiParam(name = "cityName", value = "filtering city name", required = true)
            @PathVariable String cityName) {
        List<Student> studentList = null;
        try {
            studentList = studentService.filterByCity(cityName);
        } catch (Exception ex) {
            logger.error("Error occurred in filterStudentsByCity >>", ex, ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(studentList, HttpStatus.OK);
    }

    @ApiOperation(value = "Search for all students who are from given city and "
            + "whose age are greater than input age", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolId", value = "School Id", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "age", value = "Age of Student", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "cityName", value = "City of Student", required = true, dataType = "String", paramType = "query") })
    @RequestMapping(value = "/filterByAgeAndCity", method = RequestMethod.GET)
    public ResponseEntity<Object> filterStudentsByAgeAndCity(@RequestHeader(name = "schoolId") String userId,
                                                             @RequestParam Integer age,@RequestParam String cityName) {

        List<Student> studentList = null;
        try {
            studentList = studentService.filterByAgeAndCity(age, cityName);
        } catch (Exception ex) {
            logger.error("Error occurred in filterStudentsByAgeAndCity >>", ex, ex.getMessage());
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(studentList, HttpStatus.OK);
    }
}
