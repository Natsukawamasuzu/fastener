package com.zstu.natsukawa.fasterner.fragments.askforleave;

@SuppressWarnings("unused")
public class AskForLeaveBean {

/*    //auditingState = 0 --> auditing;
    //auditingState = 1 --> passed;
    //auditingState = 2 --> rejected;
    enum auditionStatus{
        auditing,passed,rejected;
    };*/

    private String askForLeaveStartTime;
    private String askForLeaveEndTime;
    private String askForLeaveReason;
    private String contactNumber;
    private int auditingState;



    public String getAskForLeaveStartTime() {
        return askForLeaveStartTime;
    }

    public void setAskForLeaveStartTime(String askForLeaveStartTime) {
        this.askForLeaveStartTime = askForLeaveStartTime;
    }

    public String getAskForLeaveEndTime() {
        return askForLeaveEndTime;
    }

    public void setAskForLeaveEndTime(String askForLeaveEndTime) {
        this.askForLeaveEndTime = askForLeaveEndTime;
    }

    public String getAskForLeaveReason() {
        return askForLeaveReason;
    }

    public void setAskForLeaveReason(String askForLeaveReason) {
        this.askForLeaveReason = askForLeaveReason;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAuditingState() {
        return auditingState;
    }

    public void setAuditingState(int auditingState) {
        this.auditingState = auditingState;
    }
}
