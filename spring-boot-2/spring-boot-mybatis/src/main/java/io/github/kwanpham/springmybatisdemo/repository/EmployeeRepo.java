package io.github.kwanpham.springmybatisdemo.repository;

/**
 * Created by https://github.com/kwanpham
 */
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.model.EmployeeSearch;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface EmployeeRepo {

    @Select("Select * from employee")
    List<Employee> findAll();

    @Select("Select * from employee where status = #{status}")
    List<Employee> findByStatus(String status);


//    @Select("SELECT * FROM employee WHERE employee_id = #{employee_id}")
    Optional<Employee> findById(long id);

    Optional<Employee> findByIdForUpdate(long id);


    //@Insert("INSERT INTO employee (name, phone, email, gender, status, create_date) VALUES(#{name}, #{phone}, #{email}, #{gender}, #{status}, #{status})")
//    @Options(useGeneratedKeys = true, keyProperty = "employee_id")
    long insert(Employee customer);


 //   @Update("update employee set name = #{name} , gender = #{gender} , status = #{status} where employee_id = #{employee_id}")
    void update(Employee customer);

    // delete emp by id
//    @Delete("DELETE from employee WHERE employee_id = #{employee_id}")
    public void deleteById(int id);

    List<Employee> findByEmployeeSearch(EmployeeSearch employeeSearch);

    long countByEmployeeSearch(EmployeeSearch employeeSearch);

    @Select("select money from employee where employee_id = #{id} for update")
    Optional<Long> findMoneyById(Long id);

}