package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobTitleService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobTitleDao;
import kodlamaio.hrms.entities.concretes.Job;


@Service
public class JobTitleManager implements JobTitleService{

	private JobTitleDao jobTitleDao;
	
	@Autowired
	
	public JobTitleManager(JobTitleDao jobTitleDao) {
		super();
		this.jobTitleDao = jobTitleDao;
	}
	
	@Override
	public DataResult<List<Job>> getAll() {
		return new SuccessDataResult<List<Job>>(this.jobTitleDao.findAll(), "said");
	}
	@Override
	public Result add(Job job) {
		if(!this.checkIfPositionExist(job.getJobTitle()))
		{
			return new ErrorResult("Bu isimde bir iş alanı zaten mevcuttur");
		}
		this.jobTitleDao.save(job);
		return new SuccessResult("Yeni iş alanı eklendi.");
	}

	private boolean checkIfPositionExist(String jobTitle) {
		if(this.jobTitleDao.getByJobTitle(jobTitle)!=null) {
			return false;
		}
		return true;
	}
	
	
}
