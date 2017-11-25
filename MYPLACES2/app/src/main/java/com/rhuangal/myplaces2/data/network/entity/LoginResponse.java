package com.rhuangal.myplaces2.data.network.entity;

/**
 * Created by rober on 22-Nov-17.
 */

public class LoginResponse {

    private Long lastLogin;
    private Long created;
    private String name;
    private String objectId;
    private String email;
    private String ___class;

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get___class() {
        return ___class;
    }

    public void set___class(String ___class) {
        this.___class = ___class;
    }
}
