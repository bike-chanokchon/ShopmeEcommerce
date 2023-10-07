package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest // call testing from Spring data jpa
@AutoConfigureTestDatabase(replace = Replace.NONE) // config for test with real data in db
@Rollback(false) // ระบุว่าหลังจากการทดสอบ จะต้องทำการย้อนกลับหรือไม่
public class RoleRepositoryTests {
	private RoleRepository repo;
	
	@Autowired
	public RoleRepositoryTests(RoleRepository repo) {
		this.repo = repo;
	}
	
	@Test
	public void TestCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void TestCreateRestRoles() {
		Role roleSaleperson = new Role("Saleperson", "manage product price, customer, shopping, order, sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders, update order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		
		// List.of ส่งคืน list ที่ด้านในเก็บ element ที่เราส่งไปให้
		repo.saveAll(List.of(roleSaleperson, roleEditor, roleShipper, roleAssistant));
	}
}
