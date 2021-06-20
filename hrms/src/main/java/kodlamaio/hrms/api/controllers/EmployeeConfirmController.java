package kodlamaio.hrms.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.EmployeeConfirmEmployerService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.EmployeeConfirmEmployer;

@RestController
@RequestMapping("/api/confirmcontroller")
public class EmployeeConfirmController {
private EmployeeConfirmEmployerService employeeConfirmEmployerService;
@Autowired
public EmployeeConfirmController(EmployeeConfirmEmployerService employeeConfirmEmployerService) {
	super();
	this.employeeConfirmEmployerService = employeeConfirmEmployerService;
}

@PostMapping("/confirmemployer")
public Result confirm(@RequestBody EmployeeConfirmEmployer employeeConfirmEmployer) {
	return this.employeeConfirmEmployerService.confirmEmployer(employeeConfirmEmployer);
}

}
