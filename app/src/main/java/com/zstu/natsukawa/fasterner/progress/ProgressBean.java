package com.zstu.natsukawa.fasterner.progress;

public class ProgressBean {
    private String time;
    private String result;
    private String progressType;

    public ProgressBean(String time, String result,String progressType) {
        this.time = time;
        this.result = result;
        this.progressType = progressType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getProgressType() {
        return progressType;
    }

    public void setProgressType(String progressType) {
        this.progressType = progressType;
    }
}
