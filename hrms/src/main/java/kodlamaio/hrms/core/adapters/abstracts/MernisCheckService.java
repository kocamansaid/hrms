package kodlamaio.hrms.core.adapters.abstracts;

import kodlamaio.hrms.entities.concretes.Candidate;

public interface MernisCheckService {
	boolean checkIfRealPerson(Candidate candidate);
}
