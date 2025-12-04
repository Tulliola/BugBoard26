package com.bug_board.session_manager;

import com.bug_board.enum_classes.UserRole;

public final class SessionManager {

    private static SessionManager instance;

    private static volatile String jwtToken;
    private static String username;
    private static UserRole role;

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

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public void createSession(String token, String loggedUser, UserRole loggedUserRole) {
        if(token == null)
            throw new NullPointerException("token is null");

        jwtToken = token;
        username = loggedUser;
        role = loggedUserRole;
    }

    public void clearSession() {
        this.jwtToken = null;
    }

    public boolean isLoggedIn() {
        return jwtToken != null;
    }
}
