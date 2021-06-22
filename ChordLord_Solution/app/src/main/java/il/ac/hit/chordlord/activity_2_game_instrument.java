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
import android.widget.TextView;

import java.util.Locale;

public class activity_2_game_instrument extends AppCompatActivity {

    // Class Members
    private MediaPlayer mPlayer; // Audio Object
    private ImageButton piano_btn;
    private ImageButton guitar_btn;
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_game_instrument);

        // *************** Variables ***************

        //Background animation
        TextView textView= findViewById(R.id.top_2);
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
        GameInfo game = (GameInfo) getIntent().getSerializableExtra("GameInfo");

        // -------- Intents --------
        Intent activity_3_intent = new Intent(this, activity_3_game_difficulty.class);

        // -------- TextView --------
        TextView chooseInstrument = findViewById(R.id.chooseInstrument);
        chooseInstrument.setTypeface(font);

        // -------- Buttons --------
        piano_btn = findViewById(R.id.piano_btn);
        guitar_btn = findViewById(R.id.guitar_btn);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setTypeface(font);

        // *************** OnClickListeners ***************
        // Piano Button
        piano_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setInstrument("piano");
                activity_3_intent.putExtra("GameInfo",game);
                startActivity(activity_3_intent);
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_piano); // audio
                mPlayer.start();
                finish();
            }
        });

        // Guitar Button
        guitar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setInstrument("guitar");
                activity_3_intent.putExtra("GameInfo",game);
                startActivity(activity_3_intent);
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next_guitar); // audio
                mPlayer.start();
                finish();
            }
        });

        //Back Button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.next); // audio
                mPlayer.start();
                finish();
            }
        });

        animatePage();
    }

    // Animation
    public void animatePage() {

        piano_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                piano_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animatePage();
                    }
                });
                ;
            }
        });

        guitar_btn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                guitar_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700);
            }
        });

        back_btn.animate().scaleX(1.07f).scaleY(1.07f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                back_btn.animate().scaleX(1.00f).scaleY(1.00f).setDuration(700);
            }
        });
    }
}