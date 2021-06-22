package il.ac.hit.chordlord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class activity_4_game_scale extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private GameInfo game; // GameInfo
    private Button c_lonian_btn;
    private Button c_dorian_btn;
    private Button c_phrygian_btn;
    private Button c_lydian_btn;
    private Button c_mixolydian_btn;
    private Button c_aeolian_btn;
    private Button c_locrian_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_game_scale);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_4);
        AnimationDrawable animationDrawable = (AnimationDrawable) textView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Font Selector
        String loc = Locale.getDefault().getISO3Language();
        Typeface font;
        Typeface font_eng = ResourcesCompat.getFont(this, R.font.eng);
        if (loc.equals("eng")) {
            font = ResourcesCompat.getFont(this, R.font.eng);
        } else {
            font = ResourcesCompat.getFont(this, R.font.heb);
        }

        // -------- TextView --------
        TextView chooseScale = findViewById(R.id.chooseScale);
        chooseScale.setTypeface(font);

        // -------- GameInfo --------
        game = (GameInfo) getIntent().getSerializableExtra("GameInfo");

        // -------- Intents --------
        Intent activity_3_intent = new Intent(this, activity_3_game_difficulty.class);
        Intent activity_5_intent = new Intent(this, activity_5_game_quiz.class);

        // -------- Buttons --------
       c_lonian_btn = findViewById(R.id.c_lonian_btn);
       c_dorian_btn = findViewById(R.id.c_dorian_btn);
       c_phrygian_btn = findViewById(R.id.c_phrygian_btn);
       c_lydian_btn = findViewById(R.id.c_lydian_btn);
       c_mixolydian_btn = findViewById(R.id.c_mixolydian_btn);
       c_aeolian_btn = findViewById(R.id.c_aeolian_btn);
       c_locrian_btn = findViewById(R.id.c_locrian_btn);
        final Button back_btn = findViewById(R.id.back_btn);

        // Set Font
        c_lonian_btn.setTypeface(font_eng);
        c_dorian_btn.setTypeface(font_eng);
        c_phrygian_btn.setTypeface(font_eng);
        c_lydian_btn.setTypeface(font_eng);
        c_mixolydian_btn.setTypeface(font_eng);
        c_aeolian_btn.setTypeface(font_eng);
        c_locrian_btn.setTypeface(font_eng);
        back_btn.setTypeface(font);

        // *************** OnClickListeners ***************
        // c_lonian_btn
        c_lonian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Lonian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_dorian_btn
        c_dorian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Dorian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_phrygian_btn
        c_phrygian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Phrygian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_lydian_btn
        c_lydian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Lydian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_mixolydian_btn
        c_mixolydian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Mixolydian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_aeolian_btn
        c_aeolian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Aeolian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        // c_locrian_btn
        c_locrian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setScale("C Locrian");
                game.initializeChordsList();
                nextScreenAudioPlay();
                activity_5_intent.putExtra("GameInfo",game);
                startActivity(activity_5_intent);
                finish();
            }
        });

        //Back Button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_3_intent.putExtra("GameInfo",game);
                startActivity(activity_3_intent);
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                finish();
            }
        });

        animatePage();
    }

    // *************** Functions ***************
    public void nextScreenAudioPlay(){
        if (game.getInstrument().equals("guitar")){
            mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_guitar); // audio
            mPlayer.start();
        }
        else if (game.getInstrument().equals("piano")){
            mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_piano); // audio
            mPlayer.start();
        }
    }

    // Animation
    public void animatePage() {

        c_lonian_btn.animate().scaleX(1.05f).scaleY(1.05f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_lonian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });
                ;
            }
        });

        c_dorian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_dorian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });

        c_phrygian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_phrygian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });

        c_lydian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_lydian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });

        c_mixolydian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_mixolydian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });

        c_aeolian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_aeolian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });

        c_locrian_btn.animate().scaleX(1.05f).scaleY(1.01f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                c_locrian_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000);
            }
        });
    }


}