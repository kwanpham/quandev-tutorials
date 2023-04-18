package io.github.kwanpham.springmybatisdemo.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kwanpham.springmybatisdemo.model.BankAccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface BankAccountRepo extends BaseMapper<BankAccountVO> {

    @Select("select * from BANK_ACCOUNT where id = #{id}")
    Optional<BankAccountVO> findById(Long id);

    @Select("select * from BANK_ACCOUNT where id = #{id} for update")
    Optional<BankAccountVO> findByIdForUpdate(Long id);


    @Update("UPDATE BANK_ACCOUNT SET BALANCE = (BALANCE - #{amount}) WHERE ID = #{id} AND (BALANCE - #{amount}) >= 0")
    int updateBalance(Long id , Long amount);

}
