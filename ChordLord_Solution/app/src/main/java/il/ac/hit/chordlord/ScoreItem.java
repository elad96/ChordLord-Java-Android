package il.ac.hit.chordlord;

import java.io.Serializable;

public class ScoreItem implements Serializable {
    private String playerName;
    private int points;
    private int vocabulary;

    public ScoreItem(String playerName, int points, int vocabulary) {
        this.playerName = playerName;
        this.points = points;
        this.vocabulary = vocabulary;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int score) {
        this.points = score;
    }

    public int getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(int vocabulary) {
        this.vocabulary = vocabulary;
    }
}