package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iid;

    private String url;
    private String sessionID;
    private String userAgent;
    private String time;
    private String email;
    private long cid;
    private String ip;

    public Info() {}

    public Info(String url, String sessionID, String userAgent, String time, String email, long cid, String ip) {
        this.url = url;
        this.sessionID = sessionID;
        this.userAgent = userAgent;
        this.time = time;
        this.email = email;
        this.cid = cid;
        this.ip = ip;
    }
}
