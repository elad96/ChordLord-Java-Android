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
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class activity_10_learn_chords_piano extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private Button main_menu_btn;
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_learn_chords_piano);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_10);
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
        TextView chords_textview = findViewById(R.id.chords_textview);
        chords_textview.setTypeface(font);

        // -------- Buttons --------
        main_menu_btn = findViewById(R.id.main_menu_btn);
        main_menu_btn.setTypeface(font);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setTypeface(font);

        // -------- Grid --------
        final GridLayout gridLayout = findViewById(R.id.piano_grid);

        // -------- Intents --------
        Intent activity_11_intent = new Intent(this, activity_11_chord_screen.class);
        Intent activity_8_intent = new Intent(this,activity_8_learn_instrument.class);

        // *************** OnClickListeners ***************
        // Main Menu Button
        main_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_piano); // audio
                mPlayer.start();
                finish();
            }
        });

        animatePage();

        //Array = // Chords
        String[] fullChordList = new String[]{"C major", "C minor", "C diminished", "Db major", "D major", "D minor", "D diminished", "Eb major", "Eb minor", "E minor", "E diminished", "F major", "F minor", "Gb major", "Gb diminished", "G major", "G minor", "G diminished", "Ab major", "A minor", "A diminished", "Bb major", "Bb minor", "B minor", "B diminished"};

        for (int i=0 ; i<25; i++){

            // Create Button
            Button chord_btn = new Button(activity_10_learn_chords_piano.this);

            // Button set name to chord
            String chordName = fullChordList[i];
            chord_btn.setText(chordName.replace("diminished","dim"));

            // Button set attributes
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(220,170); // size
            layoutParams.setMargins(5,5,5,5); // margin
            chord_btn.setLayoutParams(layoutParams);
            chord_btn.setBackgroundColor(getResources().getColor(R.color.button_3)); // background color
            chord_btn.setAllCaps(false);
            chord_btn.setTextColor(getResources().getColor(R.color.font_1)); // font color
            chord_btn.setTextSize(14); // text size

            // Button onClickListener
            chord_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity_11_intent.putExtra("chordName",chordName);
                    activity_11_intent.putExtra("instrument","piano");
                    startActivity(activity_11_intent);
                    finish();
                }
            });

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                    mPlayer.start();
                    startActivity(activity_8_intent);
                    finish();
                }
            });

            // Grid set attributes
            gridLayout.setRowCount(6);
            gridLayout.setColumnCount(4);

            // add button to grid
            gridLayout.addView(chord_btn);

        }
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

        back_btn.animate().scaleX(1.00f).scaleY(1.00f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                back_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700);
            }
        });

    }

}
