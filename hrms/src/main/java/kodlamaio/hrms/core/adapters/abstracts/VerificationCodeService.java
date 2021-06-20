package kodlamaio.hrms.core.adapters.abstracts;

public interface VerificationCodeService {
	boolean sendVerificationCode(String email);
	//Result sendSimpleMessage(String to, String subject, String text);
}
