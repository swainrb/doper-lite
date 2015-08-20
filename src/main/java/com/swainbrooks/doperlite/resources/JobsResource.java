package com.swainbrooks.doperlite.resources;

import com.swainbrooks.doperlite.models.JobsModel;
import com.swainbrooks.doperlite.pojos.Job;
import com.swainbrooks.doperlite.pojos.NewJob;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


/**
 * Created by swain on 8/19/15.
 */

@Path("jobs")
public class JobsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Job> getAllJobs() {

        return JobsModel.INSTANCE.getAllJobs();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Job.Id registerJob(NewJob newJob) {

        return JobsModel.INSTANCE.registerJob(newJob).getId();
    }

    @Path("ids")
    public JobResource getJobResource() {
        return new JobResource();
    }
}
