package com.example.jokeapplication;

public class ListItem {

    private String type;
    private String setup;
    private String punchline;


    public ListItem(String type, String setup, String punchline) {

        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }


    public String getType() {
        return type;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }
}
