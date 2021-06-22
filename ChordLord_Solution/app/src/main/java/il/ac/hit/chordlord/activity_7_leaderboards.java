package il.ac.hit.chordlord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class activity_7_leaderboards extends AppCompatActivity {

    // Class Members
    private ArrayList<ScoreItem> scoreList;
    private Button main_menu_btn;
    private ImageView thropy1;
    private ImageView thropy2;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_leaderboards);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_7);
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

        // -------- TextView --------
        TextView leaderboard_textview = findViewById(R.id.leaderboard_textview);
        leaderboard_textview.setTypeface(font);

        // -------- ListView --------
        ListView listView = (ListView) findViewById(R.id.leaderboards_listview);

        // -------- Buttons --------
        main_menu_btn = findViewById(R.id.main_menu_btn);
        main_menu_btn.setTypeface(font);

        // -------- ArrayList --------
        scoreList = loadScoreList();
        ArrayList<String> scoreListString = scoreArrayToStringArray(scoreList);

        // -------- ArrayList --------
        thropy1= findViewById(R.id.thropy1);
        thropy2= findViewById(R.id.thropy2);

        // -------- Adapter --------
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreListString);
        listView.setAdapter(adapter);

        // *************** OnClickListeners ***************
        // Main Menu Button
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                finish();
            }
        });
        animatePage();
        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.leaderboard); // audio
        mPlayer.start();
    }

    public ArrayList<ScoreItem> loadScoreList() {
        ArrayList<ScoreItem> arraylist;
        SharedPreferences sharedPreferences = getSharedPreferences("leaderboard", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores", null);
        Type type = new TypeToken<ArrayList<ScoreItem>>() {
        }.getType();
        arraylist = gson.fromJson(json, type);

        if (arraylist == null) {
            arraylist = new ArrayList<ScoreItem>();
        }
        return arraylist;
    }

    public ArrayList<String> scoreArrayToStringArray(ArrayList<ScoreItem> scoreList) {
        ArrayList<String> scoreListString = new ArrayList<String>();
        for (int i = 0; i < scoreList.size(); i++) {
            String name = scoreList.get(i).getPlayerName();
            int points = scoreList.get(i).getPoints();
            int vocabulary = scoreList.get(i).getVocabulary();
            String str = (i + 1) + "     " + name + " - " + points + " " + getResources().getString(R.string.s6t3)
                    + " - " + vocabulary + " " + getResources().getString(R.string.s6t5);
            scoreListString.add(str);
        }
        return scoreListString;
    }

    // Animation
    public void animatePage() {

        main_menu_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                main_menu_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });
            }
        });

        thropy1.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                thropy1.animate().scaleX(1.3f).scaleY(1.3f).setDuration(700);
            }
        });

        thropy2.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                thropy2.animate().scaleX(1.3f).scaleY(1.3f).setDuration(700);
            }
        });

    }
}