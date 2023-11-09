package com.example.grimpeurscyclingclubtest;

import static java.lang.Integer.parseInt;

public class Event { // This object will be related to (generalized from?) specifically scheduled events that have info on route and datetime in the future.

    private Integer ageReq;
    private Double paceReq;
    private Integer levelReq;
    private Boolean isOffroad;

    public Event() {}

    public Event(int ageReq, double paceReq, int levelReq, boolean isOffroad) {
        this.ageReq = ageReq;
        this.paceReq = paceReq;
        this.levelReq = levelReq;
        this.isOffroad = isOffroad;
    }

    public Event(String ageReq, String paceReq, String levelReq, String isOffroad) {
        if (ageReq.equals("") || paceReq.equals("") || levelReq.equals("") || isOffroad.equals("")) {
            return;
        }
        this.ageReq = parseInt(ageReq);
        this.paceReq = Double.parseDouble(paceReq);
        this.levelReq = parseInt(levelReq);

        if (isOffroad.equals("offroad")) {
            this.isOffroad = true;
        } else if(isOffroad.equals("road")) {
            this.isOffroad = false;
        }

    }

    public boolean isEmpty() {
        return ageReq == null && paceReq == null && levelReq == null  && isOffroad == null;
    }

    public double getPaceReq() {
        return paceReq;
    }

    public boolean getIsOffroad() {
        return isOffroad;
    }

    public int getAgeReq() {
        return ageReq;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public void setAgeReq(int ageReq) {
        this.ageReq = ageReq;
    }

    public void setLevelReq(int levelReq) {
        this.levelReq = levelReq;
    }

    public void setOffroad(boolean offroad) {
        isOffroad = offroad;
    }

    public void setPaceReq(double paceReq) {
        this.paceReq = paceReq;
    }

}
