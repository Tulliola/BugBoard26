package com.bug_board.backendmodule.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SessionManager {

    private static SessionManager instance;

    private volatile String jwtToken;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null)
                    instance = new SessionManager();
            }
        }

        return instance;
    }

    public void createSession(String token) {
        this.jwtToken = token;
    }

    public void clearSession() {
        this.jwtToken = null;
    }

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
