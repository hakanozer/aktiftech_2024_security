package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iid;

    private String username;
    private String roles;
    private String url;
    private String time;
    private String userAgent;
    private String ip;
    private String sessionId;

    public Info() {}
    public Info(String username, String roles, String url, String time, String userAgent, String ip, String sessionId) {
        this.username = username;
        this.roles = roles;
        this.url = url;
        this.time = time;
        this.userAgent = userAgent;
        this.ip = ip;
        this.sessionId = sessionId;
    }

}
