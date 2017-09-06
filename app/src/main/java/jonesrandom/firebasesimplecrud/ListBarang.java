package jonesrandom.firebasesimplecrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jonesrandom.firebasesimplecrud.List.AdapterBarang;

public class ListBarang extends AppCompatActivity {


    @BindView(R.id.listBarang)
    RecyclerView listBarang;

    FirebaseDatabase dbFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);

        dbFirebase = FirebaseDatabase.getInstance();

        ButterKnife.bind(this);

        loadData();
    }

    private void loadData() {

        dbFirebase.getReferenceFromUrl("https://jonesrandom-97.firebaseio.com/barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Barang> listData = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Barang datas = data.getValue(Barang.class);
                    Barang barang = new Barang();
                    barang.setNama(datas.getNama());
                    barang.setHarga(datas.getHarga());
                    barang.setKeterangan(datas.getKeterangan());
                    barang.setNode(data.getKey());
                    listData.add(barang);

                    Log.d("ListBarang", "onCancelled: " + barang.getNama());
                }

                AdapterBarang adapterBarang = new AdapterBarang(ListBarang.this , listData);

                listBarang.setHasFixedSize(true);
                listBarang.setLayoutManager(new LinearLayoutManager(ListBarang.this));
                listBarang.setAdapter(adapterBarang);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ListBarang", "onCancelled: " + databaseError.getMessage());
            }
        });

    }
}
