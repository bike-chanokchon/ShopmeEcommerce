package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	private UserRepository repo;
	private TestEntityManager entityManager;
	
	@Autowired
	public UserRepositoryTests(UserRepository repo, TestEntityManager entityManager) {
		this.repo = repo;
		this.entityManager = entityManager;
	}
	
	@Test 
	public void testCreateNewUserWithOneRow() {
		Role adminRole = this.entityManager.find(Role.class, 1);
		
		User user = new User("bike.chanokchon@gmail.com", "Cnkcn@0853654129", "Chanokchon", "Wongjampa");
		user.addRole(adminRole);
		
		User savedUser = this.repo.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithMultiRole() {		
		User user = new User("bike.chanokchon2001@gmail.com", "Cnkcn@0853654129", "Chanokchon", "Wongjampa");
		
		Role editor = new Role(3);
		Role assistant = new Role(5);
		user.addRole(editor);
		user.addRole(assistant);
		
		User savedUser = this.repo.save(user);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> users = this.repo.findAll();
		
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testGetUserById() {
		User user = this.repo.findById(1).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User user = this.repo.findById(1).get();
		
		user.setEnabled(true);

		this.repo.save(user);
		
		assertThat(user.isEnabled()).isTrue();
	}
	
	@Test
	public void testUpdateUserRole() {
		User user = this.repo.findById(2).get();
		user.getRoles().remove(new Role(3));
		user.addRole(new Role(2));
		
		this.repo.save(user);
	}
	
	@Test
	public void testDeleteUserById() {
		Integer userId = 2;
		this.repo.deleteById(userId);
	}
}
