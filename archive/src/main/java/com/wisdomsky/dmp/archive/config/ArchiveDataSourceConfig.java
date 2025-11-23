package com.wisdomsky.dmp.archive.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
   basePackages = {
      "com.wisdomsky.dmp.archive.mapper.archive", "com.wisdomsky.dmp.archive.mapper.data", "com.wisdomsky.dmp.archive.mapper.setting",
   },
   sqlSessionTemplateRef = "archiveSqlSessionTemplate")
public class ArchiveDataSourceConfig {
   @Bean(name = "archiveDataSource")
   @ConfigurationProperties(prefix = "spring.datasource.archive")
   @Primary
   public DataSource archiveDataSource() {
      return DataSourceBuilder.create().build();
   }

   @Bean(name = "archiveSqlSessionFactory")
   @Primary
   public SqlSessionFactory archiveSqlSessionFactory(@Qualifier("archiveDataSource") DataSource dataSource)
         throws Exception {
      SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
      bean.setDataSource(dataSource);
      return bean.getObject();
   }

   @Bean(name = "archiveTransactionManager")
   @Primary
   public DataSourceTransactionManager archiveTransactionManager(@Qualifier("archiveDataSource") DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
   }

   @Bean(name = "archiveSqlSessionTemplate")
   @Primary
   public SqlSessionTemplate archiveSqlSessionTemplate(
         @Qualifier("archiveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(sqlSessionFactory);
   }
}