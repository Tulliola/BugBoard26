package com.bug_board.session_manager;

public final class SessionManager {

    private static SessionManager instance;

    private static volatile String jwtToken;

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

    public String getJwtToken() {
        return jwtToken;
    }

    public void createSession(String token) {
        if(token == null)
            throw new NullPointerException("token is null");

        jwtToken = token;
    }

    public void clearSession() {
        this.jwtToken = null;
    }

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
