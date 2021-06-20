package kodlamaio.hrms.core.adapters.concretes;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.core.adapters.abstracts.MernisCheckService;
import kodlamaio.hrms.entities.concretes.Candidate;
@Service
public class MernisCheckManagerFake implements MernisCheckService {

	@Override
	public boolean checkIfRealPerson(Candidate candidate) {
		
		return true;
	}

}
