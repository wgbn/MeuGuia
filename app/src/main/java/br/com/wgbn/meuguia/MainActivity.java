package br.com.wgbn.meuguia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.LinkedList;

import br.com.wgbn.meuguia.Models.Lugar;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView lstLugares;
    private LinkedList<Lugar> lugares = new LinkedList<Lugar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        lstLugares = (ListView) findViewById(R.id.lstLugares);

        this.mock();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng lauro = new LatLng(-12.8599849, -38.3620983);
        //mMap.addMarker(new MarkerOptions().position(lauro).title("Lauro de Freitas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lauro, 12.0f));

        for (Lugar lugar : this.lugares) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(lugar.lat, lugar.lon)).title(lugar.nome));
        }
    }

    private void mock(){
        Lugar l1 = new Lugar();
        l1.lat = -12.8599849;
        l1.lon = -38.3620983;
        l1.nome = "Lauro de Freitas";
        l1.historia = "lorem ipsum amet";
        l1.fundacao = new Date(1930, 06, 14);
        l1.imagem = "http://imagem.bocaonews.com.br/fotos/noticias/130903/mg/002.jpg?time=1449526113";

        this.lugares.add(l1);

        Lugar l2 = new Lugar();
        l2.lat = -12.881303;
        l2.lon = -38.3165084;
        l2.nome = "Hospital Geral Menandro de Farias";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "http://imagem.bocaonews.com.br/ckfinder/userfiles/fotos_e_imagens/hospital_menandro_farias_bocaonews_rv%20(8).JPG";

        this.lugares.add(l2);
    }
}
