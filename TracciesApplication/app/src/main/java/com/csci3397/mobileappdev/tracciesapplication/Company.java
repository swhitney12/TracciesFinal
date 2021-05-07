package com.csci3397.mobileappdev.tracciesapplication;

import android.media.Image;
import android.widget.ImageView;

public class Company {
    String companyTitle, description, size, type, industry, benefits, interviews, employeeRating, location;
    int image;

    public Company(String companyTitle, String description, String size, String type, String industry, String benefits, String interviews, String employeeRating, String location, int image) {
        this.companyTitle = companyTitle;
        this.description = description;
        this.size = size;
        this.type = type;
        this.industry = industry;
        this.benefits = benefits;
        this.interviews = interviews;
        this.employeeRating = employeeRating;
        this.location = location;
        this.image = image;
    }


    public String getCompanyTitle() {
        return companyTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getIndustry() {
        return industry;
    }

    public String getBenefits() {
        return benefits;
    }

    public String getInterviews() {
        return interviews;
    }

    public String getEmployeeRating() {
        return employeeRating;
    }

    public String getLocation() {
        return location;
    }

    public int getImage() {
        return image;
    }
}
