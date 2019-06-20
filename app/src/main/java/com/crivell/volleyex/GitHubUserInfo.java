package com.crivell.volleyex;

import com.google.gson.annotations.SerializedName;

public class GitHubUserInfo {
    private String login;
    private int id;
    private String location;
    @SerializedName("public_repos")
    private int publicRepos;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getPublicRepos() {
        return publicRepos;
    }
}
