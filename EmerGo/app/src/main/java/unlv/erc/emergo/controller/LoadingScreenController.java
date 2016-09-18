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
import android.view.View;
import android.widget.ProgressBar;

import unlv.erc.emergo.R;

public class LoadingScreenController extends Activity {

  private static int WAITING_TIME = 9000;
  private ProgressBar spinner ;

  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.loading_screen);

    spinner = (ProgressBar)findViewById(R.id.progressBar);
    spinner.setVisibility(View.VISIBLE);

    new Handler().postDelayed(new Runnable() {

      @Override
            public void run() {

            Intent timeCounter = new Intent(LoadingScreenController.this,
                        MainScreenController.class);
            startActivity(timeCounter);
            finish();
          }

        }, WAITING_TIME);

  }
}