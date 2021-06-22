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

public class activity_3_game_difficulty extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private GameInfo game; // GameInfo
    private Button easy_btn;
    private Button medium_btn;
    private Button hard_btn;
    private Button back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_game_difficulty);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_3);
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

        // -------- GameInfo --------
        game = (GameInfo) getIntent().getSerializableExtra("GameInfo");

        // -------- Intents --------
        Intent activity_2_intent = new Intent(this, activity_2_game_instrument.class);
        Intent activity_4_intent = new Intent(this, activity_4_game_scale.class);

        // -------- TextView --------
        TextView difficulty = findViewById(R.id.difficulty);
        difficulty.setTypeface(font);

        // -------- Buttons --------
        easy_btn = findViewById(R.id.easy_btn);
        medium_btn = findViewById(R.id.medium_btn);
        hard_btn = findViewById(R.id.hard_btn);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setTypeface(font);

        // *************** OnClickListeners ***************
        // Easy Button
        easy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("Beginner");
                activity_4_intent.putExtra("GameInfo",game);
                startActivity(activity_4_intent);
                nextScreenAudioPlay();
                finish();
            }
        });

        // Medium Button
        medium_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("Intermediate");
                activity_4_intent.putExtra("GameInfo",game);
                startActivity(activity_4_intent);
                nextScreenAudioPlay();
                finish();
            }
        });

        // Hard Button
        hard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("Professional");
                activity_4_intent.putExtra("GameInfo",game);
                startActivity(activity_4_intent);
                nextScreenAudioPlay();
                finish();
            }
        });

        //Back Button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                activity_2_intent.putExtra("GameInfo",game);
                startActivity(activity_2_intent);
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

        easy_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                easy_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });
                ;
            }
        });

        medium_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                medium_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700);
            }
        });

        hard_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                hard_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });

        back_btn.animate().scaleX(1.00f).scaleY(1.00f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                back_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700);
            }
        });
    }

}