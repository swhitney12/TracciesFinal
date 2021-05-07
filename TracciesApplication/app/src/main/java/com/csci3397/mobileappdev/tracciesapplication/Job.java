package com.csci3397.mobileappdev.tracciesapplication;

import android.widget.ImageView;

public class Job {
    String id, type, url, timePosted, companyTitle, companySite, location, jobTitle, jobDescription;
    String companyLogo;

    public Job(String id, String type, String url, String timePosted, String companyTitle, String companySite, String location, String jobTitle, String jobDescription, String companyLogo) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.timePosted = timePosted;
        this.companyTitle = companyTitle;
        this.companySite = companySite;
        this.location = location;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.companyLogo = companyLogo;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public String getCompanySite() {
        return companySite;
    }

    public String getLocation() {
        return location;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }
}
