package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobTitleService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Job;


@RestController
@RequestMapping("/api/jobtitles")
public class JobsController {
	
	private JobTitleService jobTitleService;
	
	@Autowired
	public JobsController(JobTitleService jobTitleService) {
		super();
		this.jobTitleService = jobTitleService;
	}


	@GetMapping("/getall")
	public DataResult<List<Job>> getAll(){
		return jobTitleService.getAll();
	}
	@PostMapping("/add")
	public Result add(@RequestBody Job job){
		return this.jobTitleService.add(job);
	}
	
}
