package com.swainbrooks.doperlite.resources;

import com.swainbrooks.doperlite.exeptions.CustomBadRequestResponse;
import com.swainbrooks.doperlite.models.JobsModel;
import com.swainbrooks.doperlite.pojos.Job;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Created by swain on 8/19/15.
 */
public class JobResource {

    public static final String COULD_NOT_FIND_JOB = "Could not find job for given id.  The job may have expired";
    public static final String NO_UPDATE_TYPE = "Must have an query parameter updatetype value of either \"absolute\" or \"increment\".";

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Job getJob(@PathParam("id") long id) {

        return JobsModel.INSTANCE.getJob(id)
                .orElseThrow(() -> new CustomBadRequestResponse(COULD_NOT_FIND_JOB));
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Job.Progress updateProgress(Job.Progress progress, @PathParam("id") long id, @DefaultValue("") @QueryParam("updatetype") String updateType) {

        try {
            return ProgressUpdateType.valueOf(updateType.toUpperCase())
                .updateProgress(id, progress.getProgress())
                .orElseThrow(() -> new CustomBadRequestResponse(COULD_NOT_FIND_JOB))
                .getProgress();
        } catch (IllegalArgumentException e) {
            throw new CustomBadRequestResponse(NO_UPDATE_TYPE);
        }
    }

    private enum ProgressUpdateType {
        INCREMENT(JobsModel.INSTANCE.getUpdateProgressIncrementFunction()),
        ABSOLUTE(JobsModel.INSTANCE.getUpdateProgressAbsoluteFunction());

        BiConsumer<Job, Integer> updateProgress;

        ProgressUpdateType(BiConsumer<Job, Integer> updateProgress) {
            this.updateProgress = updateProgress;
        }

        public Optional<Job> updateProgress(Long id, int progressUpdate) {
            return JobsModel.INSTANCE.updateProgress(updateProgress, id, progressUpdate);
        }
    }
}
