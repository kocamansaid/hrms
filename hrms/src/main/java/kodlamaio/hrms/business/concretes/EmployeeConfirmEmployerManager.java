package kodlamaio.hrms.business.concretes;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployeeConfirmEmployerService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployeeConfirmEmployersDao;

import kodlamaio.hrms.entities.concretes.EmployeeConfirmEmployer;

@Service
public class EmployeeConfirmEmployerManager implements EmployeeConfirmEmployerService {

	EmployeeConfirmEmployersDao employeeConfirmEmployerDao;
	
	@Autowired
	public EmployeeConfirmEmployerManager(EmployeeConfirmEmployersDao employeeConfirmEmployerDao) {
		super();
		this.employeeConfirmEmployerDao = employeeConfirmEmployerDao;
	}


	@Override
	public Result confirmEmployer(EmployeeConfirmEmployer employeeConfirmEmployer) {
		
		employeeConfirmEmployer.setConfirmDate(Date.valueOf(LocalDate.now()));
		employeeConfirmEmployer.setConfirmed(true);
		employeeConfirmEmployerDao.save(employeeConfirmEmployer);
		
		return new SuccessResult("Employer is confirmed.");
		
	}

}
