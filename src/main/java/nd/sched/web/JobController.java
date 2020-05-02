package nd.sched.web;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nd.sched.job.IJobExecutor;
import nd.sched.job.factory.JobFactory;

@RestController
@CrossOrigin
@RequestMapping("job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    JobFactory jobFactory;

    @GetMapping("list")
    public List<? extends IJobExecutor> getJobExecutors(){
        logger.info("Collecting list of Jobs");
        return jobFactory
            .getRegistry()
            .entrySet()
            .stream()
            .map(e -> e.getValue())
            .collect(Collectors.toList());
    }
    @GetMapping("details/{job}")
    public IJobExecutor getJob(@PathVariable String job){
        return jobFactory.getJobExecutor(job);
    }
}