package il.ac.hit.chordlord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameInfo implements Serializable {

    // Game General Info
    private String playerName; // Player's Full Name
    private String instrument; // Chosen Instrument (Piano / Guitar)
    private String difficulty; // Chosen Difficulty (Easy / Medium / Hard)
    private String scale; // Chosen Scale (Major / Minor)

    // Game Score Info
    private int points; // sum of points
    private int vocabulary; // amount of chords to choose from
    private int right; // amount of right answers
    private int wrong; // amount of wrong answers
    private int question; // current question (out of 12)
    private boolean isPreviousRight = false; // if the previous question answered correctly = true

    // Game Chords Info
    private String currentChord;
    private String lastCorrectChord;

    // Chords to be added in that order
    private String []    C_Lonian = {"C major","D minor","E minor","F major","G major","A minor","B diminished"};
    private String []    C_Dorian = {"C minor","D minor","Eb major","F major","G minor","A diminished","Bb major"};
    private String []    C_Phrygian = {"C minor","Db major","Eb major","F minor","G diminished","Ab major","Bb minor"};
    private String []    C_Lydian = {"C major","D major","E minor","Gb diminished","G major","G major","B minor"};
    private String []    C_Mixolydian = {"C major","D minor","E diminished","F major","G minor","A minor","Bb major"};
    private String []    C_Aeolian = {"C minor","D diminished","Eb major","F minor","G minor","Ab major","Bb major"};
    private String []    C_Locrian = {"C diminished","Db major","Eb minor","F minor","Gb major","Ab major","Bb minor"};

    // Current Chords List
    private ArrayList<String> chordsList;
    private ArrayList<String> chordsButtonsList;

    // Random Object for chord selection
    Random rand = new Random();

    // ------------------------ Set new game (Constructor)
    public GameInfo()
    {
        this.points = 0;
        this.vocabulary = 0;
        this.right = 0;
        this.wrong = 0;
        this.question = 1;
        chordsList=new ArrayList<String>();
        chordsButtonsList=new ArrayList<String>();
    }

    public boolean playMove(String answer)
    {
        if (currentChord.equals(answer)) // Right Answer
        {
            if (isPreviousRight){ // Add Another Chord
                isPreviousRight = false;
                this.points += this.vocabulary;
                this.right++;
                this.question++;
                this.addChord();
                this.chooseRandomChord();

                return true;
            }
            else { // Doesn't Add Another Chord
                isPreviousRight = true;
                this.points += this.vocabulary;
                this.right++;
                this.question++;
                this.chooseRandomChord();
                return true;
            }
        }
        else // Wrong Answer
        {
            isPreviousRight = false;
            this.wrong++;
            this.question++;
            this.setPoints(this.points-2); // reduce 2 points
            this.chooseRandomChord();
            return false;
        }
    }

    // ------------------------ Add Chord to current chords list
    public void addChord()
    {
        if (vocabulary<=7) {
            String[] chordsArray = null;
            switch (scale) {
                case "C Lonian":
                    chordsArray = C_Lonian;
                    break;

                case "C Dorian":
                    chordsArray = C_Dorian;
                    break;

                case "C Phrygian":
                    chordsArray = C_Phrygian;
                    break;

                case "C Lydian":
                    chordsArray = C_Lydian;
                    break;

                case "C Mixolydian":
                    chordsArray = C_Mixolydian;
                    break;

                case "C Aeolian":
                    chordsArray = C_Aeolian;
                    break;

                case "C Locrian":
                    chordsArray = C_Locrian;
                    break;

            }
            chordsList.add(chordsArray[vocabulary]);
            chordsButtonsList.add(chordsArray[vocabulary]);
            vocabulary++;
        }
    }

    // ------------------------ Set starter chords by difficulty
    public void initializeChordsList()
    {
        switch (this.difficulty){
            case "Beginner":
                this.addChord();
                this.addChord();
                break;
            case "Intermediate":
                this.addChord();
                this.addChord();
                this.addChord();
                break;
            case "Professional":
                this.addChord();
                this.addChord();
                this.addChord();
                this.addChord();
                break;
        }
        this.chooseRandomChord();
    }

    // ------------------------ Choose a random chord from chordsList
    public void chooseRandomChord()
    {
        this.lastCorrectChord = this.getCurrentChord();
        if (this.question==1){ // First Chord is the scale root chord
            setCurrentChord(chordsList.get(0));
        }
        else
        {
            int index = rand.nextInt(vocabulary); // Choose random chord
            if (chordsList.get(index) == this.currentChord) // if the chord is the same, choose the next
            {
                index ++;
                index%=this.vocabulary;
            }
            setCurrentChord(chordsList.get(index)); // set the chord
        }
    }
    // ------------------------ Reset Game for play again
    public void resetGame(){
        this.points = 0;
        this.vocabulary = 0;
        this.right = 0;
        this.wrong = 0;
        this.question = 1;
        chordsList=new ArrayList<String>();
        chordsButtonsList=new ArrayList<String>();
        initializeChordsList();
    }

    // ------------------------ Getters Setters
    public String getLastCorrectChord() {
        return lastCorrectChord;
    }

    public void setLastCorrectChord(String lastCorrectChord) {
        this.lastCorrectChord = lastCorrectChord;
    }

    public boolean isPreviousRight() {
        return isPreviousRight;
    }

    public void setPreviousRight(boolean previousRight) {
        isPreviousRight = previousRight;
    }

    public String getCurrentChord() {
        return currentChord;
    }

    public void setCurrentChord(String currentChord) {
        this.currentChord = currentChord;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if ((points >= 0))
            this.points = points;
    }

    public int getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(int vocabulary) {
        this.vocabulary = vocabulary;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public ArrayList<String> getChordsList() {
        return chordsList;
    }

    public void setChordsList(ArrayList<String> chordsList) {
        this.chordsList = chordsList;
    }

    public ArrayList<String> getChordsButtonsList() {
        return chordsButtonsList;
    }

    public void setChordsButtonsList(ArrayList<String> chordsButtonsList) {
        this.chordsButtonsList = chordsButtonsList;
    }
}
