package com.example.grimpeurscyclingclubtest;

import static java.lang.Integer.parseInt;

public class EventType { // This object will be related to (generalized from?) specifically scheduled events that have info on route and datetime in the future.

//    private Integer ageReq;
//    private Double paceReq;
//    private Integer levelReq;
    private String title;
    private String description;

    public EventType() {}

    public EventType(String title, String description) {//int ageReq, double paceReq, int levelReq,
//        this.ageReq = ageReq;
//        this.paceReq = paceReq;
//        this.levelReq = levelReq;
        this.title = title;
        this.description = description;
    }

//    public EventType(String title, String description) {//String ageReq, String paceReq, String levelReq,
//        if ( title.equals("") || description.equals("")) {//ageReq.equals("") || paceReq.equals("") || levelReq.equals("") ||
//            return;
//        }
//        try {
////            this.ageReq = parseInt(ageReq);
////            this.paceReq = Double.parseDouble(paceReq);
////            this.levelReq = parseInt(levelReq);
//            this.title = title;
//            this.description = description;
//        } catch (Exception e) {
//            throw new IllegalArgumentException();
//        }
//
//    }

    public boolean isEmpty() {
        return title == null && description == null;//ageReq == null && paceReq == null && levelReq == null  &&
    }

//    public double getPaceReq() {
//        return paceReq;
//    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return  title;
    }

//    public int getAgeReq() {
//        return ageReq;
//    }

//    public int getLevelReq() {
//        return levelReq;
//    }

//    public void setAgeReq(int ageReq) {
//        this.ageReq = ageReq;
//    }
//
//    public void setLevelReq(int levelReq) {
//        this.levelReq = levelReq;
//    }
//
//    public void setPaceReq(double paceReq) {
//        this.paceReq = paceReq;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
