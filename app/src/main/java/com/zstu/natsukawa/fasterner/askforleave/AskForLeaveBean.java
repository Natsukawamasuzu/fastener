package com.zstu.natsukawa.fasterner.askforleave;

public class AskForLeaveBean {

    private String studentName;
    private String studentNumber;
    private String askForLeaveDate;
    private String askForLeaveType;
    private String askForLeaveReason;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getAskForLeaveDate() {
        return askForLeaveDate;
    }

    public void setAskForLeaveDate(String askForLeaveDate) {
        this.askForLeaveDate = askForLeaveDate;
    }

    public String getAskForLeaveReason() {
        return askForLeaveReason;
    }

    public void setAskForLeaveReason(String askForLeaveReason) {
        this.askForLeaveReason = askForLeaveReason;
    }

    public String getAskForLeaveType() {
        return askForLeaveType;
    }

    public void setAskForLeaveType(String askForLeaveType) {
        this.askForLeaveType = askForLeaveType;
    }
}
