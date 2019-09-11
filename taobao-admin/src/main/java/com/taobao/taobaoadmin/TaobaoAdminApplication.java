package com.taobao.taobaoadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用启动入口
 */
@SpringBootApplication
@MapperScan({"com.taobao.taobaoadmin.mapper","com.taobao.taobaoadmin.dao"})
@EnableTransactionManagement
public class TaobaoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaobaoAdminApplication.class, args);
	}
}
