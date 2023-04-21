package io.github.kwanpham.springmybatisdemo.service;

import io.github.kwanpham.springmybatisdemo.model.BankAccountVO;
import io.github.kwanpham.springmybatisdemo.repository.BankAccountRepo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MoneyService {

    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Transactional()
    public void withDrawMoneyOptimisticLock(long id, long amount) throws Exception {
        Optional<BankAccountVO> bankAccountVOOptional = bankAccountRepo.findById(id);
        if (!bankAccountVOOptional.isPresent()) {
            throw new Exception("Account not found " + id);
        }
        BankAccountVO bankAccountVO = bankAccountVOOptional.get();
        long newBalance = bankAccountVO.getBalance() - amount;
        if (newBalance < 0) {
            throw new Exception(
                    "The money in the account '" + id + "' is not enough (" + bankAccountVO.getBalance() + ")");
        }
        bankAccountVO.setBalance(newBalance);
        // Update to DB
        int result = bankAccountRepo.updateById(bankAccountVO);
        if (result < 1 ) {
            throw new Exception("WithDrawMoney failed because version is old : " + bankAccountVO.getVersion());
        }
    }


    @Transactional()
    public void withDrawMoneyPessimisticLock(long id, long amount) throws Exception {
        Optional<BankAccountVO> bankAccountVOOptional = bankAccountRepo.findById(id);
        if (!bankAccountVOOptional.isPresent()) {
            throw new Exception("Account not found " + id);
        }
        int result = bankAccountRepo.updateBalance(id ,amount);
        if (result < 1 ) {
            throw new Exception("WithDrawMoney failed  : " + amount);
        }

    }

    @Transactional
    public void  withDrawMoneyPessimisticLock2(long id, long amount) throws Exception {
        Optional<BankAccountVO> bankAccountVOOptional = bankAccountRepo.findByIdForUpdate(id);
        if (!bankAccountVOOptional.isPresent()) {
            throw new Exception("Account not found " + id);
        }
//        Thread.sleep(50000);

        BankAccountVO bankAccountVO = bankAccountVOOptional.get();
        long newBalance = bankAccountVO.getBalance() - amount;
        if (newBalance < 0) {
            throw new Exception(
                    "The money in the account '" + id + "' is not enough (" + bankAccountVO.getBalance() + ")");
        }
        int result = jdbcTemplate.update("UPDATE BANK_ACCOUNT SET BALANCE = ? WHERE ID = ?" , newBalance , id);
        if (result < 1 ) {
            throw new Exception("WithDrawMoney failed  : " + amount);
        }
    }


}
