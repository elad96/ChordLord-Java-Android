package il.ac.hit.chordlord;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

@SuppressLint("AppCompatCustomView")
public class AnswerButton extends Button {

    private GameInfo game;

    public AnswerButton(Context context,String chord,GameInfo game) {
        super(context);
        this.game = game;
        this.setBackgroundColor(this.getContext().getResources().getColor(R.color.button_1));
        this.setText(chord);
        this.setTextSize(22);
        this.setPadding(5,5,5,5);
        this.setAllCaps(false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(420,180);
        layoutParams.setMargins(10,5,10,5);
        this.setLayoutParams(layoutParams);
        this.setTextColor(getResources().getColor(R.color.font_1));
    }

    public void sizeAnimate(boolean isRight){
        if ((isRight)){
            AnswerButton.this.animate().scaleX(1.1f).scaleY(1.3f).setDuration(300).withEndAction(new Runnable() {
                @Override
                public void run() {
                    AnswerButton.this.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300);
                }
            });
        } else {
            AnswerButton.this.animate().scaleX(0.70f).scaleY(0.70f).setDuration(300).withEndAction(new Runnable() {
                @Override
                public void run() {
                    AnswerButton.this.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300);
                }
            });
        }

    }

    public void flash(boolean isRight){
        int colorFrom;
        int colorTo;
        if (isRight){
            colorFrom = getResources().getColor(R.color.PGreen);
            colorTo= getResources().getColor(R.color.button_1);
        }
        else{
             colorFrom = getResources().getColor(R.color.PRed);
             colorTo = getResources().getColor(R.color.button_1);
        }

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(300); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                AnswerButton.this.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

   public void answerFlash(){
        String thisChord = this.getText().toString();
        String rightChord = game.getLastCorrectChord();
        if (thisChord.equals(rightChord)){
            this.flash(true);
            this.sizeAnimate(true);}
        else{
            this.flash(false);
            this.sizeAnimate(false);
        }
   }
}
