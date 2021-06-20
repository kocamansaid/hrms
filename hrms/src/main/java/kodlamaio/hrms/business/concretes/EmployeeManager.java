package kodlamaio.hrms.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployeeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployeeDao;
import kodlamaio.hrms.entities.concretes.Employee;

@Service
public class EmployeeManager implements EmployeeService{

	EmployeeDao employeeDao;
	
	public EmployeeManager(EmployeeDao employeeDao) {
		super();
		this.employeeDao = employeeDao;
	}
	
	@Override
	public DataResult<List<Employee>> getAll() {
		
		return new SuccessDataResult<List<Employee>>(this.employeeDao.findAll(),"Sistem çalışanları listelenmiştir.");
	}

	

	@Override
	public Result add(Employee employee) {
		List<Result> results = new ArrayList<Result>();
        boolean isFail = false;
		
        Result nullControl = nullControlForAdd(employee);
        
        results.add(nullControl);
        
        for (var result : results) {
            if(!result.isSuccess())
            {
                isFail = true;
                return result;
            }
        }
        
        if(isFail == false)
        {
            this.employeeDao.save(employee);
            return new SuccessResult("Çalışanı eklendi.");
        }
        
        return new ErrorResult(); 
		
	}
	
	
	public Result nullControlForAdd(Employee employee) {
        if (    employee.getFirstName() == ""
                || employee.getLastName() == ""
                || employee.getEmail() == ""
                || employee.getPassword() == "" )
        {
            return new ErrorResult("Tüm alanların doldurulması gerekmektedir.");
        }
        return new SuccessResult();
    }

}
