package com.bug_board.utilities.jokes;

import com.simtechdata.jokes.Jokes;
import com.simtechdata.jokes.enums.Category;
import com.simtechdata.jokes.enums.Language;
import com.simtechdata.jokes.enums.Type;

import java.util.ArrayList;

public class JokesService {

    private int numOfJokes;

    public JokesService(int numOfJokes) {
        this.numOfJokes = numOfJokes;
    }

    public ArrayList<String> getJokes() {
        Jokes jokes = new Jokes.Builder()
                .type(Type.SINGLE)
                .addCategory(Category.PROGRAMMING)
                .setLanguage(Language.ENGLISH)
                .safeMode()
                .build();

        ArrayList<String> jokesList = new ArrayList<>();
        for(int i = 0; i < numOfJokes; i++)
            jokesList.add(jokes.getAny().replaceAll("\n", " ").replaceAll("\r", " "));

        return jokesList;
    }

}
