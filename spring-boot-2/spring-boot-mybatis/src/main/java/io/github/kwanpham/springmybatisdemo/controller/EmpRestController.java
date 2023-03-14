package io.github.kwanpham.springmybatisdemo.controller;

import com.github.javafaker.Faker;
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.repository.EmployeeRepo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EmpRestController {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    SqlSession sqlSession;


    @GetMapping("${preUrl}/abc")
    public List<Employee> getAll(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        return employeeRepo.findAll();
    }

    @GetMapping("/inserRandom")
    public String insertRandom() throws InterruptedException {
        Thread.sleep(25000);
        Faker faker = new Faker();
        Employee employee1 = new Employee()
                .setName(faker.name().name())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setEmail(faker.internet().emailAddress())
                .setGender(faker.dog().gender())
                .setStatus("A")
                .setCreateDate(LocalDateTime.now());

        long key = employeeRepo.insert(employee1);
        return key+"";
    }


}
