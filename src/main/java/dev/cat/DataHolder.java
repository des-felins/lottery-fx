package dev.cat;

public class DataHolder {

    private static final DataHolder instance = new DataHolder();

    private String participants;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return instance;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
