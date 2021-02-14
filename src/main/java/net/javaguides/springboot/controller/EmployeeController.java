package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepo;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private EmployeeRepo employeeRepo;

	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}

	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee e) {
		return employeeRepo.save(e);
	}

	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		return ResponseEntity.ok(employee);
	}
	
	// update employee by id rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employeeDetail){
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		
		employee.setFirstName(employeeDetail.getFirstName());
		employee.setLastName(employeeDetail.getLastName());
		employee.setEmailId(employeeDetail.getEmailId());
		
		Employee updatedEmployee = employeeRepo.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer id){
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		
		employeeRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
}
