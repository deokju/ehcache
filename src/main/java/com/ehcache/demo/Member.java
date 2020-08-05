package com.ehcache.demo;

import java.io.Serializable;

public class Member implements Serializable {
    private String sessionId;
    private String userId;
    private String phoneNumber;
    public Member(String sessionId, String id1, String phoneNumber) {
        this.sessionId = sessionId;
        this.userId = id1;
        this.phoneNumber = phoneNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Member{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
