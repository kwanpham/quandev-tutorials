package io.github.kwanpham.springmybatisdemo.service;

import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
public class MoneyService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW , isolation = Isolation.SERIALIZABLE)
    public void withDrawMoney(long id , long money) throws Exception {

        addAmount(id,money);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(Long id, long amount) throws Exception {
        Optional<Employee> employeeOptional = employeeRepo.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new Exception("Account not found " + id);
        }
        Employee employee = employeeOptional.get();
        long newBalance = employee.getMoney() + amount;
        if (newBalance < 0) {
            throw new Exception(
                    "The money in the account '" + id + "' is not enough (" + employee.getMoney() + ")");
        }

        // Update to DB
        String sqlUpdate = "Update EMPLOYEE set money = ? where employee_id = ?";
        jdbcTemplate.update(sqlUpdate, newBalance, employee.getEmployeeId());
    }

}
