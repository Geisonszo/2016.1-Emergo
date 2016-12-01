/********************
 * Class name: LoadingScreenController (.java)
 *
 * Purpose: The purpose of this class is to load some application features.
 ********************/

package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import unlv.erc.emergo.R;

public class LoadingScreenController extends Activity {

  private static final String TAG = "LoadingScreenController";

   // Time in milliseconds the duration of the spinner.
   private static final int WAITING_TIME = 9000;

    protected void onCreate(Bundle savedInstanceState) {

      Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.loading_screen);

    // Set the spinner of bar menu.
    ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
    spinner.setVisibility(View.VISIBLE);

    // Needed to leave the graphic moving to the described time.
    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {

            // Set the intent to bind LoadingScreenController screen to MainScreenController screen.
            Intent timeCounter = new Intent(LoadingScreenController.this,
                                                MainScreenController.class);
              startActivity(timeCounter);
              finish();

      }
    }, WAITING_TIME);

        System.gc(); // Performs garbage collection.
  }
}