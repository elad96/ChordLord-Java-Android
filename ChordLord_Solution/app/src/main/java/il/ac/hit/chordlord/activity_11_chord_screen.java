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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class activity_11_chord_screen extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private ImageView chordImageView;
    private String chordName;
    private String instrument;
    private Button main_menu_btn;
    private Button chords_menu_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11_chord_screen);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_11);
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

        chordName = (String) getIntent().getSerializableExtra("chordName");
        instrument = (String) getIntent().getSerializableExtra("instrument");

        // -------- TextView --------
        TextView chordTextView = findViewById(R.id.chordNameTextView);
        chordTextView.setTypeface(font);
        chordTextView.setText(chordName);
        TextView press_to_play = findViewById(R.id.press_to_play);
        press_to_play.setTypeface(font);

        // -------- Buttons --------
        main_menu_btn = findViewById(R.id.main_menu_btn);
        main_menu_btn.setTypeface(font);
        chords_menu_btn = findViewById(R.id.chords_menu_btn);
        chords_menu_btn.setTypeface(font);
        final ImageButton audio_btn = findViewById(R.id.audio_btn);


        // -------- Intent --------
        Intent activity_9_intent = new Intent(this, activity_9_learn_chords_guitar.class);
        Intent activity_10_intent = new Intent(this, activity_10_learn_chords_piano.class);

        // *************** OnClickListeners ***************

        // Main Menu Button
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                finish();
            }
        });

        // Chords Menu Button
        chords_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instrument.equals("guitar")){
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_guitar); // audio
                    mPlayer.start();
                    startActivity(activity_9_intent);
                }
                else if (instrument.equals("piano")){
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_piano); // audio
                    mPlayer.start();
                    startActivity(activity_10_intent);
                }
                finish();
            }
        });

        // Audio Button
        audio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAudio();
                mPlayer.start();
            }
        });
        setAudio();
        mPlayer.start();
        setPicture();
        animatePage();
    }

    public void setAudio(){
        String chordFileName = instrument + "_" + chordName.replace(" ","_").toLowerCase();
        int resID=getResources().getIdentifier(chordFileName, "raw", getPackageName());
        mPlayer = MediaPlayer.create(this,resID);
    }

    public void setPicture(){
        String chordFileName = instrument + "_" + chordName.replace(" ","_").toLowerCase();
        int resID=getResources().getIdentifier(chordFileName, "drawable", getPackageName());
        chordImageView = findViewById(R.id.chordImageView); // set ImageView
        chordImageView.setImageResource(resID);
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

        chords_menu_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                chords_menu_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });

    }

}