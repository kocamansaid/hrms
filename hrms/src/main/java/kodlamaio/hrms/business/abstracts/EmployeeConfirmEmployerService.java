package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.Result;

import kodlamaio.hrms.entities.concretes.EmployeeConfirmEmployer;

public interface EmployeeConfirmEmployerService {
Result confirmEmployer(EmployeeConfirmEmployer employeeConfirmEmployer);
}
