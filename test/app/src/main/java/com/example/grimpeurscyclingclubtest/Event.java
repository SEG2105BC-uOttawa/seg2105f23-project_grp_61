package com.example.grimpeurscyclingclubtest;


import androidx.versionedparcelable.ParcelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event extends EventType{


    private String eventName;

    private String organizer;
    private String eventDate;
    private String eventTime;
    private int participantLimit;
    private String registrationFee;

    private String route;

    private int skillReq;

    //private List<ParticipantAccount> participants;

    public int getSkillReq() {
        return skillReq;
    }



    public Event(String eventType, String organizer, String eventName, String eventDate, String eventTime, int participantLimit, String registrationFee, String route, int skillReq){
        super(eventType);


        this.eventName = eventName;
        this.organizer = organizer;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.participantLimit = participantLimit;
        this.registrationFee = registrationFee;
        this.skillReq = skillReq;

        this.route = route;
        //this.participants = new ArrayList<ParticipantAccount>();
    }

    public String getEventName(){return eventName;}

    public String getEventOrganizer(){return organizer;}
    public String getEventDate(){return eventDate;}

    public String getEventTime(){return eventTime;}

    public int getParticipantLimit(){return participantLimit;}

    public String getRegistrationFee(){return registrationFee;}

    public String  getRouteInfo(){return route;}
}
