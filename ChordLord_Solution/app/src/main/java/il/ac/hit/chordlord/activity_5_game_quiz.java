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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class activity_5_game_quiz extends AppCompatActivity {

    // Class Members
    private GameInfo game; // GameInfo Object
    private MediaPlayer mPlayer; // Audio Object
    private custom_grid_layout answersLayout; // Layout for adding the answer buttons
    private Intent activity_6_intent; // Intent of next score window
    private Typeface font_eng;

    // Top TextViews
    private TextView question;
    private TextView points;
    private TextView vocabulary;
    private TextView right;
    private TextView wrong;

    // GridLayout
    private GridLayout rightGrid;
    private GridLayout wrongGrid;

    // KonfetiLayout
    private LinearLayout right_layout;
    private LinearLayout wrong_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_game_quiz);

        // *************** Variables ***************

        //background animation
        RelativeLayout layout= findViewById(R.id.game_background);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Font Selector
        String loc = Locale.getDefault().getISO3Language();
        Typeface font;
        font_eng = ResourcesCompat.getFont(this, R.font.eng);
        if (loc.equals("eng")) {
            font = ResourcesCompat.getFont(this, R.font.eng);
        } else {
            font = ResourcesCompat.getFont(this, R.font.heb);
        }

        // -------- Layout --------
        answersLayout = findViewById(R.id.answersLayout);
        right_layout = findViewById(R.id.right_layout);
        wrong_layout = findViewById(R.id.wrong_layout);

        // -------- TextViews --------
        question = findViewById(R.id.question);
        question.setTypeface(font);
        points= findViewById(R.id.points);
        points.setTypeface(font);
        vocabulary= findViewById(R.id.vocabulary);
        vocabulary.setTypeface(font);
        right= findViewById(R.id.right);
        right.setTypeface(font);
        wrong= findViewById(R.id.wrong);
        wrong.setTypeface(font);
        TextView press_to_play= findViewById(R.id.press_to_play);
        press_to_play.setTypeface(font);
        TextView select_chord= findViewById(R.id.select_chord);
        select_chord.setTypeface(font);

        // -------- GameInfo --------
        game = (GameInfo) getIntent().getSerializableExtra("GameInfo");

        // -------- Intents --------
        activity_6_intent = new Intent(this, activity_6_game_score.class);

        // -------- Buttons --------
        final ImageButton audio_btn = findViewById(R.id.audio_btn);

        // *************** OnClickListeners ***************

        // Audio Button
        audio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAudio();
            mPlayer.start();
            }
        });
        showAnswerOptions();
        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.start_game); // audio
        mPlayer.start();

        // -------- GridLayout --------
        rightGrid = findViewById(R.id.rightGrid);
        wrongGrid= findViewById(R.id.wrongGrid);
    }

    public void setTopScoreValues(){
        if (game.getQuestion()!=13){
            // Set Strings
            String question_str=getResources().getString(R.string.s5t0)+" "+game.getQuestion()+"/12";
            String points_str=getResources().getString(R.string.s5t1)+" "+game.getPoints();
            String vocabulary_str=getResources().getString(R.string.s5t2)+" "+game.getVocabulary();

            // Set TextViews
            question.setText(question_str);
            points.setText(points_str);
            vocabulary.setText(vocabulary_str);
        }
    }

    public void setAudio(){
        String chordFileName = game.getInstrument() + "_" + game.getCurrentChord().replace(" ","_").toLowerCase();
        int resID=getResources().getIdentifier(chordFileName, "raw", getPackageName());
        mPlayer = MediaPlayer.create(this,resID);
    }

    public void playMove(String answer){
        if (game.getQuestion()==12){
            if(game.playMove(answer)){
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.right_answer); // right last
                mPlayer.start();
            }
            else {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong); // wrong last
                mPlayer.start();
            }
            activity_6_intent.putExtra("GameInfo",game);
            startActivity(activity_6_intent);
            finish();
        }
        else {
            if(game.playMove(answer)){
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.right_answer); // right
                mPlayer.start();
                onCorrectAnswer(answer);
            }
            else {
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong); // wrong
                mPlayer.start();
                onInCorrectAnswer(answer);
            }
        }
    }

    public void onCorrectAnswer(String answer){
        addRightPic();
        rightAnimate();
    }

    public void onInCorrectAnswer(String answer){
        addWrongPic();
        wrongAnimate();
    }

    public void addRightPic(){
        ImageView rightImg = new ImageView(activity_5_game_quiz.this);
        rightImg.setImageResource(R.drawable.right_pic);
        rightGrid.addView(rightImg);
    }

    public void addWrongPic(){
        ImageView wrongImg = new ImageView(activity_5_game_quiz.this);
        wrongImg.setImageResource(R.drawable.wrong_pic);
        wrongGrid.addView(wrongImg);
    }

    public void showAnswerOptions(){
        setTopScoreValues();
        setAudio();

        // Create Answer Buttons
        if (game.getChordsButtonsList().size()>=1){
            for (String chord : game.getChordsButtonsList())
            {
                AnswerButton answer_btn = new AnswerButton(activity_5_game_quiz.this,chord,game);
                answer_btn.setTypeface(font_eng);

                answer_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Pressed Chord Name
                        Button b = (AnswerButton)v;
                        String buttonText = b.getText().toString();
                        // Send Button as answer
                        playMove(buttonText);
                        answer_btn.answerFlash();
                        showAnswerOptions();
                    }
                });

                // Add Button to Layout
                answersLayout.addView(answer_btn);
            }
            game.getChordsButtonsList().clear();
        }
    }

    public void rightAnimate(){
        right_layout.animate().scaleX(1.5f).scaleY(1.5f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                right_layout.animate().scaleX(0f).scaleY(0f).setDuration(700);
            }
        });
    }

    public void wrongAnimate(){
        wrong_layout.animate().scaleX(1.5f).scaleY(1.5f).setDuration(700).withEndAction(new Runnable() {
            @Override
            public void run() {
                wrong_layout.animate().scaleX(0f).scaleY(0f).setDuration(700);
            }
        });
    }

}