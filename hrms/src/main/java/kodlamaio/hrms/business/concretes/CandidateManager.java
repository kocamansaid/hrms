package kodlamaio.hrms.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.CandidateVerificationService;
import kodlamaio.hrms.core.adapters.abstracts.MernisCheckService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.concretes.Candidate;


@Service

public class CandidateManager extends UserManager implements CandidateService{

	private CandidateDao candidateDao;
	private MernisCheckService mernisCheckService;
	private CandidateVerificationService candidateVerificationService;
	
	
	@Autowired
	public CandidateManager(UserDao userDao, CandidateDao candidateDao, MernisCheckService mernisCheckService,
			CandidateVerificationService candidateVerificationService) {
		super(userDao);
		this.candidateDao = candidateDao;
		this.mernisCheckService = mernisCheckService;
		this.candidateVerificationService = candidateVerificationService;
	}

	
	
	
	@Override
	public DataResult<List<Candidate>> getAll() {
		
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(),"Adaylar listelenmiştir.");
	}

	@Override
	public Result add(Candidate candidate) {
		List<Result> results = new ArrayList<Result>();
		boolean isFail = false;
		
		Result nullControl = nullControlForAdd(candidate);
		Result emailControl = emailControl(candidate);
		Result identitynumberControl = identitynumberControl(candidate);
		Result mernisVerify = verifyWithMernis(candidate);
		Result emailRegexControl = emailRegexControl(candidate);
		
		results.add(nullControl);
		results.add(emailRegexControl);
		results.add(emailControl);
		results.add(identitynumberControl);
		results.add(mernisVerify);
		
		for (var result : results) {
			if(!result.isSuccess())
			{
				isFail = true;
				return result;
			}
		}
		
		if(isFail == false)
		{
			this.candidateDao.save(candidate);
			
			Result isCodeSaved = candidateVerificationService.add(candidate);
			if(!isCodeSaved.isSuccess()) {
				
			return new ErrorResult("Candidate added. Verification mail can't send.");
			}else {
				return new SuccessResult("Candidate added. Verification mail sent.");
			}
			
		}else {
			return new ErrorResult("Candidate can't added");
		}
	}
	
	public Result nullControlForAdd(Candidate candidate) {
        if (		candidate.getIdentityNumber() == ""
                || candidate.getFirstName() == ""
                || candidate.getLastName() == ""
                || candidate.getEmail() == ""
                || candidate.getPassword() == ""
                || candidate.getBirthYear() == 0 )
        {
            return new ErrorResult("Fill the all required fields.");
        }
        return new SuccessResult();
	}
	
	public Result verifyWithMernis(Candidate candidate) {
		if(!mernisCheckService.checkIfRealPerson(candidate)) {
			return new ErrorResult("Identity verification is not succeed.");
		}
		return new SuccessResult();
	}
	
	public Result emailRegexControl(Candidate candidate) {	
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(candidate.getEmail());
		if(!(matcher.find())) {
			return new ErrorResult("Please enter a valid e-mail address.");
		}
		else {
			return new SuccessResult();
		}	
}
	
	public Result identitynumberControl(Candidate candidate) {
		List<Candidate> users = this.candidateDao.getByIdentityNumber(candidate.getIdentityNumber()); 
		if(!(users.isEmpty()))
		{
			return new ErrorResult("This identity number is already registered.");
		}
		return new SuccessResult();
	}

}
