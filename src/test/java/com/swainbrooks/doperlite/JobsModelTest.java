package com.swainbrooks.doperlite;

import com.swainbrooks.doperlite.models.JobsModel;
import com.swainbrooks.doperlite.pojos.Job;
import com.swainbrooks.doperlite.pojos.NewJob;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Optional;

/**
 * Created by swain on 8/19/15.
 */
public class JobsModelTest {

    @Test
    public void registerJobTest() {
        NewJob newJob = new NewJob(1, 10);

        Job job = JobsModel.INSTANCE.registerJob(newJob);

        Job testJob = new Job(job.getId().getId(), newJob);

        assertEquals(testJob, job);
    }

    @Test
    public void getAllJobsTest() {
        NewJob newJob1 = new NewJob(1, 10);
        NewJob newJob2 = new NewJob(3, 12);
        NewJob newJob3 = new NewJob(2, 15);

        Job job1 = JobsModel.INSTANCE.registerJob(newJob1);
        Job job2 = JobsModel.INSTANCE.registerJob(newJob2);
        Job job3 = JobsModel.INSTANCE.registerJob(newJob3);

        Job testJob1 = new Job(job1.getId().getId(), newJob1);
        Job testJob2 = new Job(job2.getId().getId(), newJob2);
        Job testJob3 = new Job(job3.getId().getId(), newJob3);

        assertTrue(JobsModel.INSTANCE.getAllJobs().contains(testJob1));
        assertTrue(JobsModel.INSTANCE.getAllJobs().contains(testJob2));
        assertTrue(JobsModel.INSTANCE.getAllJobs().contains(testJob3));
    }

    @Test
    public void updateProgressAbsoluteTest() {
        NewJob newJob = new NewJob(1, 10);

        Job job = JobsModel.INSTANCE.registerJob(newJob);

        Optional<Job> testJob = JobsModel.INSTANCE.updateProgress(JobsModel.INSTANCE.getUpdateProgressAbsoluteFunction(), job.getId().getId(), 3);

        assertEquals(testJob.get().getProgress().getProgress(), 3);
    }
}
