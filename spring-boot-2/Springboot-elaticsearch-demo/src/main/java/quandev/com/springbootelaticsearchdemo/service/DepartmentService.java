package quandev.com.springbootelaticsearchdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import quandev.com.springbootelaticsearchdemo.entity.Department;
import quandev.com.springbootelaticsearchdemo.repo_elastic.DepartmentElasticsearchRepository;
import quandev.com.springbootelaticsearchdemo.repo.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    DepartmentElasticsearchRepository departmentElasticsearchRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    // Save operation

    public Department saveDepartment(Department department) {
        departmentRepository.save(department);
        return departmentElasticsearchRepository.save(department);
    }

    // Read operation

    public List<Department> getAll() {
        List<Department> departmentList = new ArrayList<>();
        departmentElasticsearchRepository.findAll().forEach(departmentList::add);
        return departmentList;
    }

    // Update operation

    public Department
    updateDepartment(Department department,
                     Long departmentId) {
        Department depDB
                = departmentRepository.findById(departmentId)
                .get();

        if (Objects.nonNull(department.getDepartmentName())
                && !"".equalsIgnoreCase(
                department.getDepartmentName())) {
            depDB.setDepartmentName(
                    department.getDepartmentName());
        }

        if (Objects.nonNull(
                department.getDepartmentAddress())
                && !"".equalsIgnoreCase(
                department.getDepartmentAddress())) {
            depDB.setDepartmentAddress(
                    department.getDepartmentAddress());
        }

        if (Objects.nonNull(department.getDepartmentCode())
                && !"".equalsIgnoreCase(
                department.getDepartmentCode())) {
            depDB.setDepartmentCode(
                    department.getDepartmentCode());
        }

        return departmentRepository.save(depDB);
    }

    // Delete operation

    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
        departmentElasticsearchRepository.deleteById(departmentId);
    }
}