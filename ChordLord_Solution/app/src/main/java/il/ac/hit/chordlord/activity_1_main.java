package il.ac.hit.chordlord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

@SuppressWarnings("serial")
public class activity_1_main extends AppCompatActivity {

    // Class Members
    private MediaPlayer music; // Audio Object
    private MediaPlayer mPlayer; // Audio Object
    private EditText name_edit_text;
    private TextView nameTextView;
    private Button press_to_begin_btn;
    private Button leaderboards_btn;
    private Button chords_guide_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_main);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_1);
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
        GameInfo game = new GameInfo();

        // -------- Edit Text --------
        name_edit_text = findViewById(R.id.name_edit_text);

        // -------- Intents --------
        Intent activity_2_intent = new Intent(this, activity_2_game_instrument.class);
        Intent activity_7_intent = new Intent(this, activity_7_leaderboards.class);
        Intent activity_8_intent = new Intent(this, activity_8_learn_instrument.class);

        // -------- TextView --------
        nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setTypeface(font);

        // -------- Buttons --------
        press_to_begin_btn = findViewById(R.id.press_to_begin_btn);
        press_to_begin_btn.setTypeface(font);
        leaderboards_btn = findViewById(R.id.leaderboards_btn);
        leaderboards_btn.setTypeface(font);
        chords_guide_btn = findViewById(R.id.chords_guide_btn);
        chords_guide_btn.setTypeface(font);

        // *************** OnClickListeners ***************

        // Press to Begin Button
        press_to_begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_edit_text_string = name_edit_text.getText().toString(); // Get Text
                if (name_edit_text_string.length()>0) // Check Input
                {
                    game.setPlayerName(name_edit_text_string);
                    music.pause();
                    music.stop();
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                    mPlayer.start();
                    activity_2_intent.putExtra("GameInfo",game);
                    startActivity(activity_2_intent);
                }
                else
                { Toast.makeText(getApplicationContext(), getResources().getString(R.string.s1t7),
                        Toast.LENGTH_SHORT).show(); }
            }
        });

        // Leaderboards Button
        leaderboards_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.pause();
                music.stop();
                startActivity(activity_7_intent);
            }
        });

        // Chords Guide Button
        chords_guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.pause();
                music.stop();
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                startActivity(activity_8_intent);
            }
        });
        animatePage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music = MediaPlayer.create(getApplicationContext(), R.raw.music); // audio
        music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    // Animation
    public void animatePage(){

        press_to_begin_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                press_to_begin_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });;
            }
        });

        leaderboards_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                leaderboards_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });

        chords_guide_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                chords_guide_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700);
            }
        });
    }

}