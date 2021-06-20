package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;

public interface CandidateVerificationService extends VerificationCodeService {
	Result add(Candidate candidate);
}
