package kodlamaio.hrms.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateVerificationService;
import kodlamaio.hrms.business.abstracts.VerificationService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateVerificationCodeDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.CandidateVerificationCode;


@Service
public class CandidateVerificationCodeManager implements CandidateVerificationService{

	
	CandidateVerificationCodeDao candidateVerificationCodeDao ;
	VerificationService verificationService;
	
	
	@Autowired
	public CandidateVerificationCodeManager(CandidateVerificationCodeDao candidateVerificationCodeDao,
			VerificationService verificationService) {
		
		this.candidateVerificationCodeDao = candidateVerificationCodeDao;
		this.verificationService = verificationService;
	}

	@Override
	public String createCode() {
		String code = codeGenerator();
		return code;
	}

	
	

	@Override
	public Result add(Candidate candidate) {
		String code = createCode();
		CandidateVerificationCode candidateVerificationCode = new CandidateVerificationCode();
		candidateVerificationCode.setCode(code);
		candidateVerificationCode.setCandidateId(candidate.getId());
		candidateVerificationCodeDao.save(candidateVerificationCode);
		return verificationService.send(candidate.getEmail(), code);
	}
	@Override
	public Result verifyEmail(String code, int userId) {
		List<CandidateVerificationCode> codes = candidateVerificationCodeDao.findByCode(code);
		if(!codes.isEmpty())
		{
			for (CandidateVerificationCode candidateVerificationCode : codes) {
				if(candidateVerificationCode.getCandidateId()==userId) {
					if(candidateVerificationCode.isVerified()==true) return new ErrorResult("Hesap doğrulama aşamasını geçmiştir.");
					candidateVerificationCode.setVerified(true);
					candidateVerificationCode.setVerifiedDate(LocalDate.now());
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
