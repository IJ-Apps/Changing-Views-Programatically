package app.ij.changingviewsprogramatically;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.ij.changingviewsprogramatically.R;

public class MainActivity extends AppCompatActivity {

    Button change, grow, shrink, up, down;
    TextView text;
    boolean above;
    CountDownTimer marginTimer, sizeTimer;
    int counter, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        change = findViewById(R.id.change);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        grow = findViewById(R.id.grow);
        shrink = findViewById(R.id.shrink);
        text = findViewById(R.id.text);
        above = true;

        Toast.makeText(getApplicationContext(), "Tap the buttons to change layout attributes!", Toast.LENGTH_LONG).show();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text.getLayoutParams();
                if (above) {
                    params.removeRule(RelativeLayout.ABOVE);
                    params.addRule(RelativeLayout.BELOW, change.getId());
                } else {
                    params.removeRule(RelativeLayout.BELOW);
                    params.addRule(RelativeLayout.ABOVE, change.getId());
                }
                counter = 1;
                //Resetting original margin Bottom
                params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottom);
                text.setLayoutParams(params);
                above = !above;
            }
        });

        counter = 1;
        //Getting the initial margin Bottom
        bottom = ((RelativeLayout.LayoutParams) text.getLayoutParams()).bottomMargin;
        View.OnClickListener marginListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (marginTimer != null)
                    marginTimer.cancel();
                marginTimer = new CountDownTimer(800, 50) {
                    @Override
                    public void onTick(long l) {
                        if (view.getId() == up.getId()) {
                            counter++;
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text.getLayoutParams();
                            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottom * counter);
                            //Resetting the TextView to above the button
                            params.removeRule(RelativeLayout.BELOW);
                            params.addRule(RelativeLayout.ABOVE, change.getId());
                            text.setLayoutParams(params);
                        } else {
                            counter--;
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text.getLayoutParams();
                            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottom * counter);
                            //Resetting the TextView to above the button
                            params.removeRule(RelativeLayout.BELOW);
                            params.addRule(RelativeLayout.ABOVE, change.getId());
                            text.setLayoutParams(params);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        };
        up.setOnClickListener(marginListener);
        down.setOnClickListener(marginListener);


        View.OnClickListener sizeListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (sizeTimer != null)
                    sizeTimer.cancel();
                sizeTimer = new CountDownTimer(1800, 100) {
                    @Override
                    public void onTick(long l) {
                        ViewGroup.LayoutParams params = text.getLayoutParams();
                        if (params.width <= 0) {
                            params.width = 400;
                            params.height = 200;
                        } else {
                            if (view.getId() == R.id.grow) {
                                params.width *= 1.1;
                                params.height *= 1.1;
                            } else {
                                params.width /= 1.1;
                                params.height /= 1.1;
                            }
                        }
                        text.setLayoutParams(params);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        };

        grow.setOnClickListener(sizeListener);
        shrink.setOnClickListener(sizeListener);
    }


}