/**
 * Aplicativo desenvolvido como trabalho final da matéria de Desenvolvimento Móvel
 * com prof. André Portugal
 *
 * Este app usa a lib LocationManager para facilitar o acesso à localização atual do usuário:
 * http://android-arsenal.com/details/1/3148
 *
 * Este app usa a lib HttpAgent para facilitar o uso de requisições HTTP abstraindo o AsyncTask
 * https://android-arsenal.com/details/1/3756
 *
 * Este app usa a lib Gson, do Google, para fazer o parser do Json recebido
 */

package br.com.wgbn.meuguia;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.studioidan.httpagent.HttpAgent;
import com.studioidan.httpagent.JsonArrayCallback;
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.constants.FailType;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Date;

import br.com.wgbn.meuguia.Models.Lugar;
import br.com.wgbn.meuguia.Utils.LugaresAdapter;

public class MainActivity extends LocationBaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView lstLugares;
    private ArrayList<Lugar> lugares = new ArrayList<Lugar>();
    private Location localizacao;

    ProgressDialog progressDialog;
    String baseUrl = "http://lugares-meuguia.rhcloud.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fragmento que contém o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        lstLugares = (ListView) findViewById(R.id.lstLugares);

        // função da lib LocationManager que pega a localização atual
        getLocation();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Localizando");
    }

    /**
     * Função que é executada quando o widget do Google Maps está pronto
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    /**
     * Função chamada pelo LocationManager quando consegue a localização do usuário
     * Está função centraliza o mapa na posição atual do usuário, exibe o diálogo de carregamento
     * e dispara a consulta ao webservice
     */
    private void carregaMapa(){
        if (this.localizacao != null){
            LatLng seuLocal = new LatLng(this.localizacao.getLatitude(), this.localizacao.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seuLocal, 14.0f));

            progressDialog.show();

            carregaLugaresProximos();
        }
    }

    /**
     * Esta função usa a lib HTTPAgent para consultar o webservice e recuperar o JSON com os locais
     * próximos ao usuário. Caso o resultado contenha algum local, a função popula o ArrayList de lugares
     * e chama o método que preenche a listView e marca os pontos no mapa.
     */
    private void carregaLugaresProximos(){
        HttpAgent.get(this.baseUrl)
                .queryParams("coords", this.localizacao.getLatitude() + "," + this.localizacao.getLongitude())
                .goJsonArray(new JsonArrayCallback() {
                    @Override
                    protected void onDone(boolean success, JSONArray jsonArray) {
                        progressDialog.dismiss();
                        Gson gson;
                        lugares = new ArrayList<Lugar>();

                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                gson = new Gson();
                                lugares.add(gson.fromJson(jsonArray.getString(i), Lugar.class));
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                        if (lugares.size() > 0)
                            preencheLista();
                    }
                });
    }

    /**
     * Esta função preenche a listView com os lugares próximos a partir do ArrayList e marca este lugares no mapa
     */
    private void preencheLista(){
        LugaresAdapter adapter = new LugaresAdapter(this, lugares);
        lstLugares.setAdapter(adapter);
        lstLugares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lugares.get(position).coords[1], lugares.get(position).coords[0]), 16.0f));
            }
        });

        if (this.lugares.size() > 0)
            for (Lugar lugar : this.lugares) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(lugar.coords[1], lugar.coords[0])).title(lugar.nome));
            }
    }

    /**
     * Função da lib LocationManager que configura a lib e os providers de localização
     * @return
     */
    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(true)
                .askForGooglePlayServices(true)
                .setGPSMessage("Você se importaria de ligar a navegação GPS?")
                .setRationalMessage("Autorize a utilização do GPS");
    }

    /**
     * Função da lib LocationManager que prover um fallback para o caso de erro na identificação do
     * local. Permite que tratemos o erro de geolocalização.
     * Neste caso apenas informo ou usuário que não foi possível achar o local onde ele está.
     * @param failType
     */
    @Override
    public void onLocationFailed(int failType) {
        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                Toast.makeText(MainActivity.this, "Não foi possível obter a localização, porque o usuário não deu permissão", Toast.LENGTH_LONG).show();
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                Toast.makeText(MainActivity.this, "Não foi possível obter a localização, porque o Google Play Services não está disponível", Toast.LENGTH_LONG).show();
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                Toast.makeText(MainActivity.this, "Não foi possível obter a localização, porque a rede não está acessível", Toast.LENGTH_LONG).show();
                break;
            }
            case FailType.TIMEOUT: {
                Toast.makeText(MainActivity.this, "Não foi possível obter a localização e tempo de espera", Toast.LENGTH_LONG).show();
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DENIED: {
                Toast.makeText(MainActivity.this, "Não foi possível obter a localização, porque o usuário não ativar os provedores via settingsApi", Toast.LENGTH_LONG).show();
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DIALOG: {
                Toast.makeText(MainActivity.this, "Não foi possível exibir diálogo settingsApi", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    /**
     * Função daã lib LocationManager que é executada cada vez que a posição do usuário é alterada
     * A localizaão é salva num atributo local e a função carregaMapa é chamada para centralizar o
     * mapa na posição atual
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        this.localizacao = location;
        this.carregaMapa();
    }
}
