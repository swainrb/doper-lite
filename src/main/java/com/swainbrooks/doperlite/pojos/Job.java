package com.swainbrooks.doperlite.pojos;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Created by swain on 8/19/15.
 */
public class Job {
    @JsonUnwrapped
    private Id id;

    @JsonUnwrapped
    private Total total;

    @JsonUnwrapped
    private Progress progress;

    public Job() {
    }

    public Job(long id, int total, int progress) {
        this.id = new Id(id);
        this.total = new Total(total);
        this.progress = new Progress(progress);
    }

    public Job(long id, NewJob job) {
        this.id = new Id(id);
        this.total = new Total(job.total);
        this.progress = new Progress(job.progress);
    }

    public Id getId() {
        return id;
    }

    public Total getTotal() {
        return total;
    }

    public Progress getProgress() {
        return progress;
    }

    public static class Id {
        private long id;

        public Id() {}

        public Id(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

    public static class Total {
        private int total;

        public Total() {}

        public Total(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }
    }

    public static class Progress {
        private int progress;

        public Progress() {}

        public Progress(int progress) {
            this.progress = progress;
        }

        public int getProgress() {
            return progress;
        }
    }

    public void setProgress(int progress) {
        this.progress = new Progress(progress);
    }

    public void incrementProgress(int amountToIncrement) {
        int newProgress = this.getProgress().progress + amountToIncrement;

        if(newProgress > this.getTotal().total) {
            this.progress = new Progress(this.getTotal().total);
        }
        else {
            this.progress = new Progress(newProgress);
        }
    }

    @Override
    public boolean equals(Object o) {

        if(o == this) {
            return true;
        }

        if(!(o instanceof Job)) {
            return false;
        }

        Job job = (Job) o;

        return this.getId().getId() == job.getId().getId()
                && this.getProgress().getProgress() == job.getProgress().getProgress()
                && this.getTotal().getTotal() == job.getTotal().getTotal();
    }
}
