package kodlamaio.hrms.core.adapters.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.adapters.abstracts.VerificationCodeService;
@Service
public class VerificationCodeManager implements VerificationCodeService{

	@Override
	public boolean sendVerificationCode(String email) {
		UUID uuid = UUID.randomUUID();
        String code = uuid.toString();
        System.out.println(email+"Adrese doğrulama kodu gönderildi.");
        System.out.println("Doğrulama kodu:"+ code);
        return true;
	}

}
