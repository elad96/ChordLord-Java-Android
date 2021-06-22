package il.ac.hit.chordlord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class activity_6_game_score extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private GameInfo game; // GameInfo Object
    private TextView score_view;
    private TextView vocabulary_view;
    private Button main_menu_btn;
    private Button leaderboards_btn;
    private Button play_again_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_game_score);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_6);
        AnimationDrawable animationDrawable = (AnimationDrawable) textView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Font Selector
        String loc = Locale.getDefault().getISO3Language();
        Typeface font;
        if (loc.equals("eng")) {
            font = ResourcesCompat.getFont(this, R.font.eng);
        } else {
            font = ResourcesCompat.getFont(this, R.font.heb);
        }

        // -------- TextViews --------
        score_view = findViewById(R.id.score);
        score_view.setTypeface(font);
        vocabulary_view= findViewById(R.id.vocabulary);
        vocabulary_view.setTypeface(font);
        TextView cong = findViewById(R.id.cong);
        cong.setTypeface(font);
        TextView your_score= findViewById(R.id.your_score);
        your_score.setTypeface(font);
        TextView your_vocabulary = findViewById(R.id.your_vocabulary);
        your_vocabulary.setTypeface(font);


        // -------- GameInfo --------
        game = (GameInfo) getIntent().getSerializableExtra("GameInfo");

        // -------- Intents --------
        Intent activity_5_intent = new Intent(this, activity_5_game_quiz.class);
        Intent activity_7_intent = new Intent(this, activity_7_leaderboards.class);

        // -------- Buttons --------
        main_menu_btn = findViewById(R.id.main_menu_btn);
        main_menu_btn.setTypeface(font);
        leaderboards_btn = findViewById(R.id.leaderboards_btn);
        leaderboards_btn.setTypeface(font);
        play_again_btn = findViewById(R.id.play_again_btn);
        play_again_btn.setTypeface(font);

        // *************** OnClickListeners ***************
        // Main Menu Button
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Leader-boards Button
        leaderboards_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activity_7_intent);
                finish();
            }
        });

        play_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.resetGame();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        this.loadScore(); // Load score to TextView
        this.saveScore(); // save score

        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.gameover); // audio
        mPlayer.start();

        animatePage();
    }

    public void loadScore(){
        int score = game.getPoints();
        int vocabulary = game.getVocabulary();
        String score_txt = score + " " + getResources().getString(R.string.s6t3);
        String vocabulary_txt = vocabulary + " " + getResources().getString(R.string.s6t5);
        this.score_view.setText(score_txt);
        this.vocabulary_view.setText(vocabulary_txt);
    }

    public void saveScore(){
        String name = game.getPlayerName(); // Player name
        int points = game.getPoints(); // Point amount
        int vocabulary = game.getVocabulary(); // Chords amount
        ScoreItem score = new ScoreItem(name,points,vocabulary); // ScoreItem object
        ArrayList <ScoreItem> scoreList = this.loadScoreList(); // load score list
        this.scoreToArrayList(scoreList, score); // update score list
        this.saveScoreList(scoreList); // save score list to shared preferences
    }

    public void scoreToArrayList(ArrayList <ScoreItem> arrayList, ScoreItem score){
        if (arrayList.size()==0){ // array list empty - just add
            arrayList.add(score);
        }
        else {
            for (int i=0; i<10; i++){
                if (score.getPoints()>=arrayList.get(i).getPoints()) {
                    arrayList.add(i,score);
                    if (arrayList.size()==11){
                        arrayList.remove(10);
                    }
                    return;
                }
            }
        }
    }

    public ArrayList <ScoreItem> loadScoreList(){
        ArrayList <ScoreItem> arraylist;
        SharedPreferences sharedPreferences = getSharedPreferences("leaderboard",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores",null);
        Type type = new TypeToken<ArrayList<ScoreItem>>(){}.getType();
        arraylist = gson.fromJson(json, type);

        if (arraylist==null){
            arraylist = new ArrayList <ScoreItem>();
        }
        return arraylist;
    }

    public void saveScoreList(ArrayList <ScoreItem> arraylist){
        SharedPreferences sharedPreferences = getSharedPreferences("leaderboard",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraylist);
        editor.putString("scores",json);
        editor.apply();
    }

    // Animation
    public void animatePage() {

        score_view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                score_view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });
                ;
            }
        });

        vocabulary_view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                vocabulary_view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(700);
            }
        });

        main_menu_btn.animate().scaleX(1.06f).scaleY(1.06f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                main_menu_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });

        leaderboards_btn.animate().scaleX(1.06f).scaleY(1.06f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                leaderboards_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });

        play_again_btn.animate().scaleX(1.06f).scaleY(1.06f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                play_again_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });
    }
}