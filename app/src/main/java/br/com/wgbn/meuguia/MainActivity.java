package br.com.wgbn.meuguia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import br.com.wgbn.meuguia.Models.Lugar;
import br.com.wgbn.meuguia.Utils.LugaresAdapter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView lstLugares;
    private ArrayList<Lugar> lugares = new ArrayList<Lugar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        lstLugares = (ListView) findViewById(R.id.lstLugares);

        this.mock();
        this.preencheLista();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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

        l2 = new Lugar();
        l2.lat = -12.9383436;
        l2.lon = -38.3892009;
        l2.nome = "Faculdade SENAI CIMATEC";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "https://lh6.googleusercontent.com/-Z_nwLY4VAKU/U7MLToBQoUI/AAAAAAAAABA/0xf2Q9CwXisIG9g5nWZijVjCZQQc-hhOQ/s408-k-no/";

        this.lugares.add(l2);

        l2 = new Lugar();
        l2.lat = -12.9816632;
        l2.lon = -38.4666431;
        l2.nome = "Shopping da Bahia";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "https://lh4.googleusercontent.com/proxy/gYvauyTuHx22Iu44DtPRzCUy5DDqu7Un8LObAtETDIgxMLZ4mrXJLk-pJPDHyIKmqCsfyvtI3-tXS_aa4h8e0Xda9-kniQ=w408-h306";

        this.lugares.add(l2);

        l2 = new Lugar();
        l2.lat = -12.978135;
        l2.lon = -38.457105;
        l2.nome = "Salvador Shopping";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "https://lh4.googleusercontent.com/proxy/RUzRg_Z81qHQ-gH6vabTqPqLRT8zJ-o3-Y8FiuhUVhTXH3smT1ZBSN7EiW01uO0oVB0PcCM9AOBLvX6F9fsZL8ANE9ne8Rg=w408-h306";

        this.lugares.add(l2);

        l2 = new Lugar();
        l2.lat = -12.9994458;
        l2.lon = -38.4747004;
        l2.nome = "Parque da Cidade";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "https://lh6.googleusercontent.com/proxy/j0y28mpx_Y1W1gDr8AqQ7YneaZwxhwVT2M3r_iND3cs6gH1AWZJtcdSDkvQ564WXOp6V64h-nEtwNIkvCq0xg041RcKDww=w408-h306";

        this.lugares.add(l2);

        l2 = new Lugar();
        l2.lat = -12.9832466;
        l2.lon = -38.5132682;
        l2.nome = "Estação da Lapa";
        l2.historia = "lorem ipsum amet";
        l2.fundacao = new Date(1930, 06, 14);
        l2.imagem = "https://lh3.googleusercontent.com/proxy/9LuFsNotux-yvnHk-Gy7P-Il8yA3FL0AMs7Ue4oKL0hTzWAPk9K7BI0cV41S9xcexQw1akcaPHIPIuk429qXAeXAeJg27JMGWv4frbNj2wnjz1g6CXaNhisH3h6b0UWFb2uk3crBGMGXHeuAgAiSgOU6wYzWk0I=w408-h305";

        this.lugares.add(l2);
    }

    private void preencheLista(){
        LugaresAdapter adapter = new LugaresAdapter(this, lugares);
        lstLugares.setAdapter(adapter);
        lstLugares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lugares.get(position).lat, lugares.get(position).lon), 15.0f));
                //
            }
        });
    }
}
