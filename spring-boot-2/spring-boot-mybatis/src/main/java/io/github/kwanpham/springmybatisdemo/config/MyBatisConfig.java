package io.github.kwanpham.springmybatisdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by https://github.com/kwanpham
 */
@Configuration
@EnableTransactionManagement
//@MapperScan("kwan.org.mybatistriggerdemo.mapper")   custom mapper if have add mapper from lib
public class MyBatisConfig {

//    @Autowired
//    public DataSource dataSource;
//
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("kwan.org.mybatistriggerdemo.model");
//        return sqlSessionFactoryBean;
//    }
}
