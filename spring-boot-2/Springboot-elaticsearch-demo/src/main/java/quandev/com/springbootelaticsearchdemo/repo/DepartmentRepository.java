package quandev.com.springbootelaticsearchdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quandev.com.springbootelaticsearchdemo.entity.Department;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}