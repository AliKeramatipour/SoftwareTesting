package org.springframework.samples.petclinic.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@Spy Role role;
	@Spy User user;
	@Mock UserRepository userRepository;

	@InjectMocks UserServiceImpl userService;

	Set<Role> roles = new HashSet<>();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveUserTestNullRoles() {
		Mockito.when(user.getRoles()).thenReturn(null);
		try {
			userService.saveUser(user);
			fail("saveUserTestNullRoles failed");
		}catch (Exception e) {
			assertEquals(e.getMessage(), "User must have at least a role set!");
			Mockito.verify(user, Mockito.times(1)).getRoles();
			Mockito.verify(role, Mockito.times(0)).getName();
			Mockito.verify(role, Mockito.times(0)).getUser();
			Mockito.verify(userRepository, Mockito.times(0)).save(any(User.class));
		}
	}

	@Test
	public void saveUserTestEmptyRoles() {
		Mockito.when(user.getRoles()).thenReturn(new HashSet<>());
		try {
			userService.saveUser(user);
			fail("saveUserTestEmptyRoles failed");
		}catch (Exception e) {
			assertEquals(e.getMessage(), "User must have at least a role set!");
			Mockito.verify(user, Mockito.times(2)).getRoles();
			Mockito.verify(role, Mockito.times(0)).getName();
			Mockito.verify(role, Mockito.times(0)).getUser();
			Mockito.verify(userRepository, Mockito.times(0)).save(any(User.class));
		}
	}

	@Test
	public void saveUserTestNameDoesNotStartWithRoleNullUser() {
		//	will go in both ifs in for
		roles.add(role);

		Mockito.when(user.getRoles()).thenReturn(roles);
		Mockito.when(role.getName()).thenReturn("notRole");
		Mockito.when(role.getUser()).thenReturn(null);

		try {
			userService.saveUser(user);

			Mockito.verify(user, Mockito.times(3)).getRoles();
			Mockito.verify(role, Mockito.times(2)).getName();
			Mockito.verify(role, Mockito.times(1)).getUser();
			Mockito.verify(role, Mockito.times(1)).setName(any(String.class));
			Mockito.verify(role, Mockito.times(1)).setUser(any(User.class));
			Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
		}catch (Exception e) {
			fail("saveUserTestNameDoesNotStartWithRoleNullUser failed");
		}
	}

	@Test
	public void saveUserTestNameStartsWithRoleNullUser() {
		//	will go in both ifs in for
		roles.add(role);

		Mockito.when(user.getRoles()).thenReturn(roles);
		Mockito.when(role.getName()).thenReturn("ROLE_someRole");
		Mockito.when(role.getUser()).thenReturn(null);

		try {
			userService.saveUser(user);

			Mockito.verify(user, Mockito.times(3)).getRoles();
			Mockito.verify(role, Mockito.times(1)).getName();
			Mockito.verify(role, Mockito.times(1)).getUser();
			Mockito.verify(role, Mockito.times(0)).setName(any(String.class));
			Mockito.verify(role, Mockito.times(1)).setUser(any(User.class));
			Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
		}catch (Exception e) {
			fail("saveUserTestNameDoesNotStartWithRoleNullUser failed");
		}
	}

	@Test
	public void saveUserTestNameStartsWithRoleNotNullUser() {
		//	will go in both ifs in for
		roles.add(role);

		Mockito.when(user.getRoles()).thenReturn(roles);
		Mockito.when(role.getName()).thenReturn("ROLE_someRole");
		Mockito.when(role.getUser()).thenReturn(user);

		try {
			userService.saveUser(user);

			Mockito.verify(user, Mockito.times(3)).getRoles();
			Mockito.verify(role, Mockito.times(1)).getName();
			Mockito.verify(role, Mockito.times(1)).getUser();
			Mockito.verify(role, Mockito.times(0)).setName(any(String.class));
			Mockito.verify(role, Mockito.times(0)).setUser(any(User.class));
			Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
		}catch (Exception e) {
			fail("saveUserTestNameDoesNotStartWithRoleNullUser failed");
		}
	}
}
