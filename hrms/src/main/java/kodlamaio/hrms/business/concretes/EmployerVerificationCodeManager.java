package kodlamaio.hrms.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerVerificationCodeService;
import kodlamaio.hrms.business.abstracts.VerificationService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerVerificationCodeDao;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.EmployerVerificationCode;

@Service
public class EmployerVerificationCodeManager implements EmployerVerificationCodeService {

	EmployerVerificationCodeDao employerVerificationCodeDao;
	VerificationService verificationService;
	
	
	@Autowired
	public EmployerVerificationCodeManager(EmployerVerificationCodeDao employerVerificationCodeDao,
			VerificationService verificationService) {
		super();
		this.employerVerificationCodeDao = employerVerificationCodeDao;
		this.verificationService = verificationService;
	}



	@Override
	public String createCode() {
		String code = codeGenerator();
		return code;
	}

	

	@Override
	public Result add(Employer employer) {

		String code = createCode();
		EmployerVerificationCode employerVerificationCode = new EmployerVerificationCode();
		employerVerificationCode.setCode(code);
		employerVerificationCode.setEmployerId(employer.getId());
		employerVerificationCodeDao.save(employerVerificationCode);
		return verificationService.send(employer.getEmail(), code);
	}
	
	
	@Override
	public Result verifyEmail(String code, int userId) {

		List<EmployerVerificationCode> codes = employerVerificationCodeDao.findByCode(code);
		if(!codes.isEmpty())
		{
			for (EmployerVerificationCode employerVerificationCode : codes) {
				if(employerVerificationCode.getEmployerId()==userId) {
					if(employerVerificationCode.isVerified()==true) return new ErrorResult("Hesap doğrulama aşamasını geçmiştir.");
					employerVerificationCode.setVerified(true);
					employerVerificationCode.setVerifiedDate(LocalDate.now());
					return new SuccessResult("E-mail is verified.");		
				}
			}
		}
		return new ErrorResult("Hesap doğrulanmamıştır.");
	}

	
	
	
	
	private String codeGenerator() {
		String characters="ABCDEFGHIJKLMNOPRSTUVYZ0123456789";
		String code="";
		int length =6;
		
		Random rand=new Random();
		
		char[] text = new char[length];
		
		for(int i=0; i<=length;i++)
		{
			text[i]=characters.charAt(rand.nextInt(characters.length()));
		}
		
		for(int i=0; i<text.length;i++) {
			code +=text[i];
		}
		return code;
	}

}
