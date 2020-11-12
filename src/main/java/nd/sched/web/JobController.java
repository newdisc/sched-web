package nd.sched.web;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nd.sched.job.IJobExecutor;
import nd.sched.job.IJobExecutor.JobReturn;
import nd.sched.job.factory.JobFactory;
import nd.sched.job.service.AsyncExecutorFacade;

@RestController
@RequestMapping("api/job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    JobFactory jobFactory;
    @Autowired
    private AsyncExecutorFacade executorFacade;

    @GetMapping("list")
    public List<? extends IJobExecutor> getJobExecutors() {
        logger.info("Collecting list of Jobs");
        return jobFactory.getRegistry().entrySet().stream()
            .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @GetMapping("details/{job}")
    public IJobExecutor getJob(@PathVariable String job) {
        return jobFactory.getJobExecutor(job);
    }

    @GetMapping("logs/{job}")
    public List<String> getJobLogs(@PathVariable String job) {
        return jobFactory.getJobLogs(job);
    }
    
    @GetMapping("run")
    public JobReturn runJob(@RequestParam final String jobName, 
    		@RequestParam(required = false) String arguments) 
    		throws InterruptedException, ExecutionException {
    	if (null == arguments) {
    		arguments = "";
    	}
        logger.info("Run Job: {}", jobName);
    	CompletableFuture<JobReturn> execJob = executorFacade.execute("ManualRun", jobName, arguments);
    	return  execJob.get();
    }
}