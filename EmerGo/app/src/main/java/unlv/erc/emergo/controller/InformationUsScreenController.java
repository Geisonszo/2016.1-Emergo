package unlv.erc.emergo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unlv.erc.emergo.R;
import unlv.erc.emergo.model.HealthUnit;

public class InformationUsScreenController extends Activity implements View.OnClickListener {

    private List <String> listOfInformations = new ArrayList<String>();
    private ListView healthUnitInfo;
    private Button buttonRoute;
    private ImageView buttonGo;
    private Intent receive;
    private int numberUsSelected;
    private String padding;
    private String titleHealthUnit;
    private String nameHeatlthUnit;
    private String unitType;
    private String state;
    private String city;
    private String district;
    private String addressNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_us_screen);

        setReceive(getIntent());
        setNumberUsSelected(receive.getIntExtra("position" , 0));
        buttonRoute = (Button) findViewById(R.id.botaoRota);
        buttonRoute.setOnClickListener(this);
        buttonGo = (ImageView) findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(this);

        setHealthUnitInfo((ListView) findViewById(R.id.hospInformation));
        setInformation(HealthUnitController.getClosestsUs().get(numberUsSelected));

        addInformationToList();
    }

    @Override
    public void onClick(View viewOnClick) {

        if(viewOnClick.getId() == R.id.botaoRota) {

            Intent route = new Intent();

            route.setClass(InformationUsScreenController.this , RouteActivity.class);
            route.putExtra("numeroUs" , receive.getIntExtra("position" , 0));
            startActivity(route);
            finish();
        }

        if(viewOnClick.getId() == R.id.buttonGo) {

            final String ROUTETRACED = "Rota mais próxima traçada";
            Intent routeActivity = new Intent();

            Toast.makeText(this, ROUTETRACED , Toast.LENGTH_SHORT).show();
            routeActivity.setClass(InformationUsScreenController.this , RouteActivity.class);
            routeActivity.putExtra("numeroUs" , -1);
            startActivity(routeActivity);
            finish();
        }
    }

    public void setInformation(HealthUnit healthUnit) {

        setPadding("\n");
        setTitleHealthUnit("        Informações da Unidade de Saúde");
        setNameHeatlthUnit("  Nome: " + healthUnit.getNameHospital());
        setUnitType("  Tipo de atendimento: " + healthUnit.getUnitType());
        setState("  UF: " + healthUnit.getState());
        setCity("  Cidade: " + healthUnit.getCity());
        setDistrict("  Bairro: " + healthUnit.getDistrict());
        setAddressNumber("  Cep: " + healthUnit.getAddressNumber());
    }

    public void  addInformationToList() {

        listOfInformations.add(padding);
        listOfInformations.add(titleHealthUnit);
        listOfInformations.add(nameHeatlthUnit);
        listOfInformations.add(unitType);
        listOfInformations.add(state);
        listOfInformations.add(city);
        listOfInformations.add(district);
        listOfInformations.add(addressNumber);
        showInformationOnScreen();
    }

    public void showInformationOnScreen() {

        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this,
                                                                android.R.layout.simple_list_item_1,
                                                                listOfInformations);

        healthUnitInfo.setAdapter(adapter);
    }

    public void open_search(View mapScreen) {

        Intent openSearch = new Intent();

        openSearch.setClass(this,SearchUsController.class);
        startActivity(openSearch);
    }

    public void listMapsImageClicked(View map_screen) {

        Intent listOfHealth = new Intent();

        listOfHealth.setClass(this,ListOfHealthUnitsController.class);
        startActivity(listOfHealth);
        finish();
    }

    public void openMap(View mapScreen) {

        Intent mapActivity = new Intent();

        mapActivity.setClass(this,MapScreenController.class);
        startActivity(mapActivity);
        finish();
    }

    public void openConfig(View map_screen) {

        Intent config = new Intent();

        config.setClass(this , ConfigController.class);
        startActivity(config);
        finish();
    }

    public List<String> getListOfInformations() {

        return listOfInformations;
    }

    public void setListOfInformations(List<String> listOfInformations) {

        this.listOfInformations = listOfInformations;
    }

    public ListView getHealthUnitInfo() {

        return healthUnitInfo;
    }

    public void setHealthUnitInfo(ListView healthUnitInfo) {

        this.healthUnitInfo = healthUnitInfo;
    }

    public Button getButtonRoute() {

        return buttonRoute;
    }

    public void setButtonRoute(Button buttonRoute) {

        this.buttonRoute = buttonRoute;
    }

    public ImageView getButtonGo() {

        return buttonGo;
    }

    public void setButtonGo(ImageView buttonGo) {

        this.buttonGo = buttonGo;
    }

    public Intent getReceive() {

        return receive;
    }

    public void setReceive(Intent receive) {

        this.receive = receive;
    }

    public int getNumberUsSelected() {

        return numberUsSelected;
    }

    public void setNumberUsSelected(int numberUsSelected) {

        this.numberUsSelected = numberUsSelected;
    }

    public String getPadding() {

        return padding;
    }

    public void setPadding(String padding) {

        this.padding = padding;
    }

    public String getTitleHealthUnit() {

        return titleHealthUnit;
    }

    public void setTitleHealthUnit(String titleHealthUnit) {

        this.titleHealthUnit = titleHealthUnit;
    }

    public String getNameHeatlthUnit() {

        return nameHeatlthUnit;
    }

    public void setNameHeatlthUnit(String nameHeatlthUnit) {

        this.nameHeatlthUnit = nameHeatlthUnit;
    }

    public String getUnitType() {

        return unitType;
    }

    public void setUnitType(String unitType) {

        this.unitType = unitType;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getDistrict() {

        return district;
    }

    public void setDistrict(String district) {

        this.district = district;
    }

    public String getAddressNumber() {

        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {

        this.addressNumber = addressNumber;
    }
}


