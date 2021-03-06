package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.enumeration.Status;
import com.example.demo.exception.EntityExistsNotExistsException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTest {
	@Mock
	private EmployeeRepository emplRepository;

	@Test
	public void createEmployee() {
		EmployeeEntity employeePojo = new EmployeeEntity();
		employeePojo.setEmployeeId(1);
		// stubbing the value
		Mockito.when(emplRepository.save(ArgumentMatchers.any(EmployeeEntity.class))).thenReturn(employeePojo);
		EmployeeEntity employeePojo1 = emplRepository.save(employeePojo);
		// checking for equals
		assertEquals(1, employeePojo1.getEmployeeId());
		// failure case
		// assertEquals(2,employeePojo1.getEmployeeId());

		Mockito.verify(emplRepository).save(employeePojo1);
	}
	
	@Test
	public void createEmployeeTestCaseTwo() {
		EmployeeEntity employeePojo = new EmployeeEntity();
		employeePojo.setEmployeeId(1);
		employeePojo.setEmailId("test@test.com");
		// stubbing the value
		Mockito.when(emplRepository.findByEmailId(ArgumentMatchers.any(String.class))).thenThrow(new EntityExistsNotExistsException("EmailId alresdy exssits in our db",1));
		EntityExistsNotExistsException exception = assertThrows(EntityExistsNotExistsException.class,()->
		{
			emplRepository.findByEmailId(employeePojo.getEmailId());
		});
		// checking for equals
		assertEquals("EmailId alresdy exssits in our db", exception.getErrorMessage());
		Mockito.verify(emplRepository).findByEmailId(employeePojo.getEmailId());
	}

	@Test
	public void getEmployeeById() {
		EmployeeEntity employeePojo = new EmployeeEntity();
		employeePojo.setEmployeeId(1);
		Optional<EmployeeEntity> optional = Optional.ofNullable(employeePojo);
		// stubbing the value
		Mockito.when(emplRepository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(optional);
		Optional<EmployeeEntity> employeePojo1 = emplRepository.findById(1);
		// checking for equals
		assertEquals(1, employeePojo1.get().getEmployeeId());
		Mockito.verify(emplRepository).findById(1);
	}

	@Test
	public void getAllEmployee() {
		EmployeeEntity employeePojo = new EmployeeEntity();
		employeePojo.setEmployeeId(1);
		EmployeeEntity employeePojo2 = new EmployeeEntity();
		employeePojo.setEmployeeId(2);
		List<EmployeeEntity> list = new ArrayList<>();
		list.add(employeePojo);
		list.add(employeePojo2);

		// stubbing the value
		Mockito.when(emplRepository.findAll()).thenReturn(list);
		List<EmployeeEntity> listEmployee = emplRepository.findAll();
		// checking for equals
		assertTrue(listEmployee.size() > 0);
		Mockito.verify(emplRepository).findAll();
	}

	@Test
	public void getEmployeeByStatus() {
		EmployeeEntity employeePojo = new EmployeeEntity();
		employeePojo.setEmployeeId(1);
		employeePojo.setStatus(Status.DE_ACTIVE);
		EmployeeEntity employeePojo2 = new EmployeeEntity();
		employeePojo.setEmployeeId(2);
		employeePojo.setStatus(Status.ACTIVE);
		List<EmployeeEntity> list = new ArrayList<>();
		list.add(employeePojo);
		list.add(employeePojo2);
		// stubbing the value
		Mockito.when(emplRepository.findByStatus(ArgumentMatchers.any(Status.class))).thenReturn(list);
		List<EmployeeEntity> employeePojoList = emplRepository.findByStatus(Status.ACTIVE);
		// checking for equals
		assertEquals(Status.ACTIVE, employeePojoList.get(0).getStatus());
		Mockito.verify(emplRepository).findByStatus(Status.ACTIVE);
	}

}
