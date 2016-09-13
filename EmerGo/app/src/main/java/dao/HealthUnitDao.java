/*****************************
 * Class name: HealthUnitDao (.java)
 *
 * Purpose: This class has a method to lower Firebase data and save the data in the database.
 ****************************/

package dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import unlv.erc.emergo.controller.HealthUnitController;
import unlv.erc.emergo.model.HealthUnit;

public class HealthUnitDao {

    private Context context;
    private static final String URL_BASE_DB = "https://emergodf.firebaseio.com/";

    /**
     * You receive the "context" of the HealthUnitDao class.
     * @param context
     */
    public HealthUnitDao(Context context) {

        this.context = context;
    }

    /**
     * Low data from health units and saved in the database. In addition to putting this data in the
     * arraylist.
     */

    public void setDataOnSugar() {

        Firebase ref = new Firebase(URL_BASE_DB);
        HealthUnit healthUnit = new HealthUnit();
        List<HealthUnit> list;
        list = healthUnit.listAll(HealthUnit.class);

        if (list.size() == 0 || list == null ) {

            ref.child("EmerGo").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    HealthUnit model;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        double latitude = (double) child.child("lat").getValue();
                        double longitude = (double) child.child("long").getValue();
                        String nameHospital = child.child("no_fantasia").getValue().toString();
                        String unitType = child.child("ds_tipo_unidade").getValue().toString();
                        String addressNumber = child.child("co_cep").getValue().toString();
                        String district = child.child("no_bairro").getValue().toString();
                        String state = child.child("uf").getValue().toString();
                        String city = child.child("municipio").getValue().toString();

                        if((unitType.contains("HOSPITAL GERAL") || unitType.contains("CENTRO DE SAUDE/UNIDADE BASICA")
                                || unitType.contains("UNIDADE MOVEL DE NIVEL PRE-HOSPITALAR NA AREA DE URGENCIA") ||
                                unitType.contains("UNIDADE MOVEL TERRESTRE"))) {

                            model = new HealthUnit(latitude,longitude,nameHospital,unitType,
                                    addressNumber,district,state,city);
                            model.save();
                            HealthUnitController.setClosestsUs(model);
                        }
                    }
                    Toast.makeText(context, "Atualize o mapa para carregar mais USs" ,
                                    Toast.LENGTH_LONG).show();
                    Log.i("Database has finished", HealthUnitController.getClosestsUs().size() + "Us");
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } else {

            for(int aux = 0 ; aux < list.size(); aux++) {
                HealthUnitController.setClosestsUs(list.get(aux));
            }
            //Arrumar log depois, para i
            Log.d("log123", "preenchida offline");
            Log.i("Database has finished", HealthUnitController.getClosestsUs().size() + " Us");
        }
    }
}
