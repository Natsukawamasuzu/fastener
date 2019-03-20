package com.zstu.natsukawa.fasterner.register;

public class RegisterBean {

    private String studentNumber;
    private String password;
    private String phoneNumber;
    private String studentName;
    private String studentMemberLevel;

    public RegisterBean(String studentNumber, String password, String phoneNumber, String studentName) {
        this.studentNumber = studentNumber;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.studentName = studentName;
    }

    String getStudentMemberLevel() {
        return studentMemberLevel;
    }

    public void setStudentMemberLevel(String studentMemberLevel) {
        this.studentMemberLevel = studentMemberLevel;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "studentNumber='" + studentNumber + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
