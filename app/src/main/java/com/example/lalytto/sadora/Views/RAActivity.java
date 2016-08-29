package com.example.lalytto.sadora.Views;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.lalytto.sadora.R;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RAActivity extends AppCompatActivity {

    private ArchitectView architectView;
    private final String KEY_WIKITUDE = "s5/nJeCKdrSDdjpI3GYjzidjKenLmUO+OD7AFQe/0j0CmsSze3htSa+GL8K4WdF/8hRzu9yCq1uGNvSUKe1f/voI9WZ2gUEwkzpZLe85mZqefskMsSlkd1akJ/kcIfsFIPyHa14QtQN1E1uxvjfwz/e60TZ01gWchCd3KWn1N0JTYWx0ZWRfXw7tP2zg461jUAnAqI6OAWXZ6MIO+OiugEYuvBSxop0SOv8MljmtJmdEPpZrVSAK/cn+mpqHlpY2y94pPNZPJ88X3gcaT+IKHb5Aq4edOG5ksnmXo60QXCOHGwL8ICitDrVTPxQ73YyX11gJY9Y6g1ca6nP8Sz9+gqnZDQ9c+J0vBfb7idRAjUPeveZHcVutmFAzIPrufbtFitSCI6YHPzScgcsSK2PRJzB7vXLyqutUMDLVwuR6nkVUAmzGLt5o4zbc7QIwwCvyWb1/KKmrbjZNeBPEsah/WyDSmP8agB1DCE0JzLR4F8yXLE2s5/kf7ey5SRy6D/AuFQ/ZQlaA04+FVyy68IuVF4/K5rbhDVgCbaegbnggrsIooNrke555kPrOAYihPgAnCDUWDQWNnC4k20zOmAo+X+V7Hd0fo/OvG5wABHqQFg/KsV4J37yr86kMtqdvf4x6EvtgiyuQkjKhuXbNSMbetEgpiAiiJ4UTmDLIbT3uNIA=";

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ra);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        this.architectView = (ArchitectView) this.findViewById( R.id.architectView );
        final StartupConfiguration config = new StartupConfiguration(KEY_WIKITUDE);
        this.architectView.onCreate( config );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.architectView.onPostCreate();
        try {
            this.architectView.load("http://sadora.lalytto.com/wt/index.html");
        } catch (Exception e){

        }
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.architectView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.architectView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.architectView.onPause();
    }

}