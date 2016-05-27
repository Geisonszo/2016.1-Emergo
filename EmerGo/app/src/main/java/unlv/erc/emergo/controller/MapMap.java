package unlv.erc.emergo.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import unlv.erc.emergo.R;

public class MapMap extends FragmentActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapmap);

    }

    public void goClicked(View map_screen) {
        Toast.makeText(this, "Função não habilitada!", Toast.LENGTH_SHORT).show();
    }

    public void listMapsImageClicked(View map_screen){
        Intent listOfHealth = new Intent();
        listOfHealth.setClass(this , ListOfHealthUnitsController.class);
        startActivity(listOfHealth);
    }

    public void openMap(View mapScreen){
        Intent mapActivity = new Intent();
        mapActivity.setClass(this, MapMap.class);
        startActivity(mapActivity);
        finish();
    }


}