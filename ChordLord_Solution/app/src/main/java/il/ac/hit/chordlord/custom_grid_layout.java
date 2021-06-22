package il.ac.hit.chordlord;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class custom_grid_layout extends GridLayout {
    public custom_grid_layout(Context context) {
        super(context);
        setAttributes();
    }

    public custom_grid_layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes();
    }

    public custom_grid_layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes();
    }

    public void setAttributes(){
        this.setPadding(5,5,5,5);
        this.setColumnCount(2);
        this.setUseDefaultMargins(true);
        this.setBackgroundResource(R.drawable.answers_background);
    }
}
