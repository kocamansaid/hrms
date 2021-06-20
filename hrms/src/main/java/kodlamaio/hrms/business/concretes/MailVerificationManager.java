package kodlamaio.hrms.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;

import kodlamaio.hrms.business.abstracts.VerificationService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.utilities.senders.MailSender;

public class MailVerificationManager implements VerificationService {

	MailSender mailSender;
	
	@Autowired
	public MailVerificationManager(MailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}


	@Override
	public Result send(String email, String code) {
		
		boolean sendEmail = mailSender.sendVerificationCode(email, code);
		if(sendEmail == false)
		{
			return new ErrorResult();

		}else {
			return new SuccessResult();
		}
	}

}
