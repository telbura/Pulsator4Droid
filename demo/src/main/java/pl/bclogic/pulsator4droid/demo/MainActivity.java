package pl.bclogic.pulsator4droid.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    private PulsatorLayout mPulsator;
    private TextView mCountText;
    private TextView mDurationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        mCountText = (TextView) findViewById(R.id.text_count);
        mDurationText = (TextView) findViewById(R.id.text_duration);

        // count seek bar
        SeekBar countSeek = (SeekBar) findViewById(R.id.seek_count);
        countSeek.setOnSeekBarChangeListener(mCountChangeListener);
        countSeek.setProgress(mPulsator.getCount() - 1);

        // duration seek bar
        SeekBar durationSeek = (SeekBar) findViewById(R.id.seek_duration);
        durationSeek.setOnSeekBarChangeListener(mDurationChangeListener);
        durationSeek.setProgress(mPulsator.getDuration() / 100);

        // start pulsator
        mPulsator.start();

        Button pause = (Button) findViewById(R.id.pause);
        Button resume = (Button) findViewById(R.id.resume);
        Button start = (Button) findViewById(R.id.start);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPulsator.pause();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPulsator.resume();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPulsator.start();
            }
        });

        mPulsator.setOnAnimationEndListener(new PulsatorLayout.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPulsator.start();
                    }
                }, 2000);
            }
        });

    }

    private final SeekBar.OnSeekBarChangeListener mCountChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mPulsator.setCount(progress + 1);
                    mCountText.setText(String.format(Locale.US, "%d", progress + 1));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

            };

    private final SeekBar.OnSeekBarChangeListener mDurationChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mPulsator.setDuration(progress * 100);
                    mDurationText.setText(String.format(
                            Locale.US, "%.1f", progress * 0.1f));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

            };

}
