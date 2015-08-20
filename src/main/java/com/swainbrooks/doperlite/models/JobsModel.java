package com.swainbrooks.doperlite.models;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.swainbrooks.doperlite.pojos.Job;
import com.swainbrooks.doperlite.pojos.NewJob;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created by swain on 8/19/15.
 */

public enum JobsModel {
    INSTANCE;

    private final TimeUnit EXPIRE_AFTER_ACCESS_TIME_UNIT = TimeUnit.MINUTES;
    private final int EXPIRE_AFTER_ACCESS = 1;
    private final Cache<Long, Job> cache;
    private long currentIdentifier = 0;

    JobsModel() {
        cache = CacheBuilder.newBuilder()
            .expireAfterAccess(EXPIRE_AFTER_ACCESS, EXPIRE_AFTER_ACCESS_TIME_UNIT)
            .build();
    }

    // Returns null if not present
    public Optional<Job> getJob(long id) {
        return Optional.of(cache.getIfPresent(id));
    }

    public Collection<Job> getAllJobs() {
        return cache.asMap().values();
    }

    public Job registerJob(NewJob job) {
        currentIdentifier ++;
        cache.put(currentIdentifier, new Job(currentIdentifier, job));
        return cache.getIfPresent(currentIdentifier);
    }

    public Optional<Job> updateProgress(BiConsumer<Job, Integer> updateFunction, long id, int progressUpdate) {
        Optional<Job> jobOption = Optional.of(cache.getIfPresent(id));

            if (jobOption.isPresent()) {
                Job job = jobOption.get();
                updateFunction.accept(job, progressUpdate);
                cache.put(job.getId().getId(), job);
            }
        return Optional.of(cache.getIfPresent(id));
    }

    public BiConsumer<Job, Integer> getUpdateProgressIncrementFunction() {
        return (job, progressIncrement) -> job.incrementProgress(progressIncrement);
    }

    public BiConsumer<Job, Integer> getUpdateProgressAbsoluteFunction() {
        return (job, progress) -> job.setProgress(progress);
    }

}
