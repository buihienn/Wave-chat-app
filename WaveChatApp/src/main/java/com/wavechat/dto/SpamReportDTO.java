package com.wavechat.dto;

import java.util.Date;

public class SpamReportDTO {
    private int reportID;           
    private String reporterID;      
    private String reportedUserID;   
    private Date timestamp;        

    public SpamReportDTO(int reportID, String reporterID, String reportedUserID, Date timestamp) {
        this.reportID = reportID;
        this.reporterID = reporterID;
        this.reportedUserID = reportedUserID;
        this.timestamp = timestamp;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReporterID() {
        return reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    public String getReportedUserID() {
        return reportedUserID;
    }

    public void setReportedUserID(String reportedUserID) {
        this.reportedUserID = reportedUserID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}