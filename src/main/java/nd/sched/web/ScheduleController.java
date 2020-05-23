package nd.sched.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nd.sched.job.IJobTrigger;
import nd.sched.job.JobTriggerStatus;
import nd.sched.job.service.JobTriggerService;

@RestController
@RequestMapping("api/trigger")
public class ScheduleController {
    public static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    private JobTriggerService jobTriggerService;

    @GetMapping("list")
    public List<? extends IJobTrigger> listTriggers(){
        List<? extends IJobTrigger> jt = jobTriggerService.getJobList();
        logger.debug("List of Job Triggers: {}", jt);
        return jt;
    }

    @GetMapping("run")
    public JobTriggerStatus runJob(@RequestParam final String triggerName) {
        logger.info("Run Trigger: {}", triggerName);
        JobTriggerStatus ret = jobTriggerService.runJob(triggerName);
        jobTriggerService.signalJob(triggerName);
        return ret;
    }
}
