package com.example.mg.noteappfirebase.Models;

public class noteModel {

    String noteText;
    String noteTittle;
    Long   timeStamp;
    String nodeAddress;

    public noteModel(String noteTittle, String noteText , String nodeAddress) {
        this.noteText = noteText;
        this.noteTittle = noteTittle;
        this.timeStamp = System.currentTimeMillis();
        this.nodeAddress = nodeAddress;
    }

    public noteModel() {
    }

    public String getNoteText() {
        return noteText;
    }

    public String getNoteTitle() { return noteTittle; }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }


}
