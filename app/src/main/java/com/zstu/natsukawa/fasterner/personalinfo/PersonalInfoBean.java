package com.zstu.natsukawa.fasterner.personalinfo;

import java.io.Serializable;

public class PersonalInfoBean implements Serializable {

    private String avatarUrl;
    private String studentName;
    private String studentNumber;
    private String studentMemberLevel;
    private String studentRoleInTeam;
    private float technologyLevel;
    private float characteristicLevel;
    private float otherAbilityLevel;
    private float professionalKnowledgeLevel;
    private float learningAbilityLevel;

    public float getTechnologyLevel() {
        return technologyLevel;
    }

    public void setTechnologyLevel(float technologyLevel) {
        this.technologyLevel = technologyLevel;
    }

    public float getCharacteristicLevel() {
        return characteristicLevel;
    }

    public void setCharacteristicLevel(float characteristicLevel) {
        this.characteristicLevel = characteristicLevel;
    }

    public float getOtherAbilityLevel() {
        return otherAbilityLevel;
    }

    public void setOtherAbilityLevel(float otherAbilityLevel) {
        this.otherAbilityLevel = otherAbilityLevel;
    }

    public float getProfessionalKnowledgeLevel() {
        return professionalKnowledgeLevel;
    }

    public void setProfessionalKnowledgeLevel(float professionalKnowledgeLevel) {
        this.professionalKnowledgeLevel = professionalKnowledgeLevel;
    }

    public float getLearningAbilityLevel() {
        return learningAbilityLevel;
    }

    public void setLearningAbilityLevel(float learningAbilityLevel) {
        this.learningAbilityLevel = learningAbilityLevel;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

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

    public String getStudentMemberLevel() {
        return studentMemberLevel;
    }

    public void setStudentMemberLevel(String studentMemberLevel) {
        this.studentMemberLevel = studentMemberLevel;
    }

    public String getStudentRoleInTeam() {
        return studentRoleInTeam;
    }

    public void setStudentRoleInTeam(String studentRoleInTeam) {
        this.studentRoleInTeam = studentRoleInTeam;
    }
}
