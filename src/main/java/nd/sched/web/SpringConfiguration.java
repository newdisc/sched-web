package nd.sched.web;

import java.util.concurrent.LinkedBlockingQueue;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nd.sched.job.factory.JobFactory;
import nd.sched.job.service.AsyncExecutorFacade;
import nd.sched.job.service.ExecutorService;
import nd.sched.job.service.JobTriggerService;
import nd.sched.job.service.JobTriggerWorker;
import nd.sched.job.service.QuartzCronService;

@Configuration
public class SpringConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SpringConfiguration.class);
    @Bean
    public JobFactory jobFactory() {
        logger.debug("Creating JobFactory");
        return new JobFactory();
    }
    @Bean
    public ExecutorService executorService(){
        logger.debug("Creating ExecutorService");
        ExecutorService esvc = new ExecutorService();
        esvc.setJobFactory(jobFactory());
        esvc.load();
        return esvc;
    }
    @Bean
    public AsyncExecutorFacade asyncExecutorFacade(){
        logger.debug("Creating AsyncExecutorFacade");
        AsyncExecutorFacade asyncExecutorFacade = new AsyncExecutorFacade();
        asyncExecutorFacade.setService(executorService());
        return asyncExecutorFacade;
    }
    @Bean
    public JobTriggerService jobTriggerService(){
        logger.debug("Creating JobTriggerService");
        final LinkedBlockingQueue<String> jobQueue = new LinkedBlockingQueue<>();
        JobTriggerService jts = new JobTriggerService();
        jts.setJobTriggerQueue(jobQueue);
        jts.setExecutorFacade(asyncExecutorFacade());
        QuartzCronService quartzCronService = quartzCronService();
        quartzCronService.setJobTriggerService(jts);
        jts.loadTriggers(quartzCronService);
        return jts;
    }
    @Bean
    public QuartzCronService quartzCronService(){
        logger.debug("Creating QuartzCronService");
        QuartzCronService quartzCronService;
        try {
            quartzCronService = new QuartzCronService();
            return quartzCronService;
        } catch (SchedulerException e) {
            final String msg = "Issue starting the cron service";    
            logger.error(msg, e);
            return null;
        }
    }
    @Bean
    public JobTriggerWorker jobTriggerWorker() {
        logger.info("Creating Job Trigger Worker");
        JobTriggerWorker jtw = new JobTriggerWorker();
        jtw.setJobTriggerService(jobTriggerService());
        return jtw;
    }
    @Bean
    public WebMvcConfigurer logsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/logs/**")
                    .addResourceLocations("file:./logs/");
            }            
        };
    } 
}
