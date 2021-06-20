package kodlamaio.hrms.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.EmployerVerificationCodeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager extends UserManager implements EmployerService{
	
	private EmployerDao employerDao;
	private EmployerVerificationCodeService employerVerificationCodeService;
	
	@Autowired
	public EmployerManager(EmployerDao employerDao, EmployerVerificationCodeService employerVerificationCodeService, UserDao userDao) {
		super(userDao);
		this.employerDao = employerDao;
		this.employerVerificationCodeService = employerVerificationCodeService;
	}

	
	

	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),"said");
		
	}

	@Override
	public Result add(Employer employer) {
		
		List<Result> results = new ArrayList<Result>();
		boolean isFail = false;
		
		Result nullControl = nullControlForAdd(employer);
		Result emailControl = emailControl(employer);
		Result employerEmailControl = employerEmailControl(employer);
		
		results.add(nullControl);
		results.add(emailControl);
		results.add(employerEmailControl);
		
		
		for (var result : results) {
			if(!result.isSuccess())
			{
				isFail = true;
				return result;
			}
		}
		
		if(isFail == false)
		{
			this.employerDao.save(employer);
			Result isCodeSaved = employerVerificationCodeService.add(employer);
			if(!isCodeSaved.isSuccess()) {
				
			return new ErrorResult("Employer added. Verification mail can't send.");
			}else {
				return new SuccessResult("Employer added. Verification mail sent.");
			}
				
		}else {
			return new ErrorResult();
		}
		
	}
	
	
	private Result nullControlForAdd(Employer employer) {
        if (	employer.getCompanyName() == ""
                || employer.getWebsite() == ""
                || employer.getPhoneNumber() == ""
                || employer.getEmail() == ""
                || employer.getPassword() == "" )
        {
            return new ErrorResult("Tüm alanların doldurulması gerekmektedir.");
        }
        return new SuccessResult();
	}
	
	public Result employerEmailControl(Employer employer) {
		String[] emailSplit = employer.getEmail().split("@");
		if (!employer.getWebsite().contains(emailSplit[1])) {
			return new ErrorResult("Lütfen geçerli bir mail adresi giriniz.");
		}
		else {
			return new SuccessResult();
		}
	}

	
}
