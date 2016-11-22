/********************
 * Class name: SearchHealthUnitActivity (.java)
 *
 * Purpose: The purpose of this class is to search the closest health units.
 ********************/

package unlv.erc.emergo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

import java.util.ArrayList;
import java.util.List;



public class SearchHealthUnitActivity extends AppCompatActivity implements SearchView
    .OnQueryTextListener, View.OnClickListener {

  private SearchView mapSearchView;
  private int numberOfHealthUnitClicked = 0; //Index responsable for future searching methods
  private ListView healthUnitsList;
  ArrayList<String> closestHealthUnit;
  ArrayList<HealthUnit> healthUnit;

  protected void onCreate(Bundle savedInstanceState) {

    Log.i("SearchHealth Requested!","Sucessfull intent creation");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_screen);

    Log.i("SearchHealth Created!","request sucessfull");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    super.onCreateOptionsMenu(menu);

    ImageView goButton = (ImageView) findViewById(R.id.buttonGo);
    goButton.setOnClickListener(this);

    ImageView healthUnitList = (ImageView) findViewById(R.id.iconList);
    healthUnitList.setOnClickListener(this);

    ImageView map = (ImageView) findViewById(R.id.iconMap);
    map.setOnClickListener(this);

    MenuInflater inflater = getMenuInflater();

    inflater.inflate(R.menu.menu_search, menu);
    mapSearchView = (SearchView) menu.findItem(R.id.search)
            .getActionView();
    mapSearchView.setQueryHint("Busca");
    mapSearchView.setOnQueryTextListener(this);

    return true;
  }



  @Override
  public void onClick(View searchView) {
    if (searchView.getId() == R.id.buttonGo) {

      Log.i("Go clicked sucessfully","create an RouteActivity intent");
      Intent route = new Intent();

      route.setClass(SearchHealthUnitActivity.this, RouteActivity.class);
      startActivity(route);
      finish();
    }
    if (searchView.getId() == R.id.iconMap) {


      Log.i("Map clicked sucessfully","create an MapScreen intent");
      Intent map = new Intent();

      map.setClass(SearchHealthUnitActivity.this, MapScreenController.class);
      map.putExtra("numeroUs" , -1);
      startActivity(map);
      finish();
    }
    if (searchView.getId() == R.id.iconList) {


      Log.i("List click sucessfull","create an ListOfHealthUnit intent");
      Intent list = new Intent();

      list.setClass(SearchHealthUnitActivity.this, ListOfHealthUnitsController.class);
      startActivity(list);
      finish();
    }
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {

    assert newText != null : "Error: newText is null";

    List<String> searchHealthUnit = getSearchHealthUnit(HealthUnitController.getClosestHealthUnit());

    setHealthUnitsList((ListView) findViewById(R.id.list_of_search_us));
    healthUnitsList.setAdapter(new ArrayAdapter<>(this, R.layout.item,
            R.id.hospitalUnitText, searchHealthUnit));
    healthUnitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        numberOfHealthUnitClicked = parent.getPositionForView(view);
        openInformationHealthUnitScreen();
      }
    });
    return false;
  }

  /**
   * This method open the screen with inforamtion about the health unit.
   */
  public void openInformationHealthUnitScreen() {

    Intent informationScreen = new Intent();

    informationScreen.putExtra("position", numberOfHealthUnitClicked);
    informationScreen.setClass(SearchHealthUnitActivity.this, InformationSearchScreenController.class);
    startActivity(informationScreen);
    finish();
  }

  /*
   * This method search the health unit on map.
   * @param closest the closest health unit
   * @return the closest health unit
   */

  private ArrayList<String> getSearchHealthUnit(ArrayList<HealthUnit> closest) {

    assert closest != null : "Error: closest is null";

    ArrayList<String> searchArray = new ArrayList<>();

    try {

      CharSequence search = mapSearchView.getQuery();
      closestHealthUnit = new ArrayList<>();
      healthUnit = new ArrayList<>();

      Log.i("Starting US search","getSearchHealthUnit method");

      findSimilarHealthUnitDueToUserInput(closest, search);

      return closestHealthUnit;
    }catch (NullPointerException exception){
      exception.printStackTrace();

      Log.i("Search failed","getSearchHealthUnit method");
    }

    Log.i("SearchHealthUnit","searchArray nullity"+searchArray.isEmpty());

    assert !searchArray.isEmpty() : "Error: method to find closest HU find nothing";
    return searchArray;
  }

  private void findSimilarHealthUnitDueToUserInput(ArrayList<HealthUnit> closest, CharSequence search) {
    int numberOfUs;
    for (numberOfUs = 0; numberOfUs < HealthUnitController.getClosestHealthUnit().size();
       numberOfUs++) {
      if (closest.get(numberOfUs).getNameHospital().toLowerCase().contains(search)) {
        closestHealthUnit.add(closest.get(numberOfUs).getNameHospital());
        healthUnit.add(HealthUnitController.getClosestHealthUnit().get(numberOfUs));
      }
    }
  }

  public void setHealthUnitsList(ListView healthUnitsList) {
    assert healthUnitsList != null: "Error: healtUnit List is null";

    this.healthUnitsList = healthUnitsList;
    Log.i("Health Unit setted","setHealthUnitListView method");
  }
}


