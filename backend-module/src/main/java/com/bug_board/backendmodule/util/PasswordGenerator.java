package com.bug_board.backendmodule.util;
<<<<<<< HEAD
=======

import com.bug_board.backendmodule.REST_controllers.AuthenticationController;
>>>>>>> 321cea5 (iniziato sviluppo del form per il report di un'issue)

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "._%+-";

    public static String generatePassword(int length) {
        String allSets = LOWERCASE + UPPERCASE + DIGITS + SPECIAL;
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allSets.length());
            password.append(allSets.charAt(randomIndex));
        }

        return password.toString();
    }
}
