package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.Result;

public interface VerificationCodeService {
	String createCode();

	Result verifyEmail(String code,int userId);

}
