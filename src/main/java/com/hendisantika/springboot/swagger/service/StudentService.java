package com.hendisantika.springboot.swagger.service;

import com.hendisantika.springboot.swagger.model.Student;
import org.jfairy.Fairy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hendisantika on 4/24/17.
 */
@Service
public class StudentService {
    private static Map<Integer, Student> studentDB;
    private Fairy fairy = Fairy.create();

    @PostConstruct
    public void init() throws Exception {
        studentDB = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Student student = new Student(i, fairy.person());
            studentDB.put(new Integer(i), student);
        }
    }

    public List<Student> getAll(){
        List<Student> studentList = studentDB.entrySet().stream()
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return studentList;
    }

    public Student getStudentById(Integer studentId) {
        return studentDB.get(studentId);
    }

    public List<Student> filterByAge(Integer age) {
        List<Student> studentList = studentDB.entrySet().stream().filter(e -> e.getValue().getAge() > age)
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return studentList;
    }

    public List<Student> filterByCity(String cityName) {
        List<Student> studentList = studentDB.entrySet().stream()
                .filter(e -> e.getValue().getAddress().getCity().equals(cityName)).map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return studentList;
    }

    public List<Student> filterByAgeAndCity(Integer age, String cityName) {
        List<Student> studentList = studentDB.entrySet().stream()
                .filter(e -> e.getValue().getAddress().getCity().equals(cityName) && e.getValue().getAge() > age)
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return studentList;
    }
}
