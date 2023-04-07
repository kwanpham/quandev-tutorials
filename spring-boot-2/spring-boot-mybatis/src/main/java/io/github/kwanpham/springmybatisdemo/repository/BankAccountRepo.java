package io.github.kwanpham.springmybatisdemo.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kwanpham.springmybatisdemo.model.BankAccountVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BankAccountRepo extends BaseMapper<BankAccountVO> {
}
