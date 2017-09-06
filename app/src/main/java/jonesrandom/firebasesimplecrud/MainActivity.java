package jonesrandom.firebasesimplecrud;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.namaBarang)
    TextView NamaBarang;

    @BindView(R.id.hargaBarang)
    TextView HargaBarang;

    @BindView(R.id.keteranganBarang)
    TextView KeteranganBarang;

    DatabaseReference dbReferences;
    FirebaseDatabase dbFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        dbFirebase = FirebaseDatabase.getInstance();
        dbReferences = dbFirebase.getReference("barang");

        loadStatus();

    }

    private void loadStatus() {

        dbFirebase.getReference("status").setValue("online");
        dbFirebase.getReference("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Data = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: " + Data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }

        });

    }

    @OnClick({R.id.btnSave , R.id.btnLihat})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.btnSave:

                String Barangs = NamaBarang.getText().toString();
                String Keterangan = KeteranganBarang.getText().toString();
                final String Harga = HargaBarang.getText().toString();

                if (Barangs.isEmpty() || Keterangan.isEmpty() || Harga.isEmpty()){

                    Snackbar.make(findViewById(R.id.parent) , "Lengkapi Form Untuk Menyimpan Barang" , Snackbar.LENGTH_SHORT).show();
                } else {

                    String UserID = dbReferences.push().getKey();
                    Barang barang = new Barang(Barangs , Keterangan , Harga);

                    dbReferences.child(UserID).setValue(barang);
                    dbReferences.child(UserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Snackbar.make(findViewById(R.id.parent) , "Berhasil Menambah Data" , Snackbar.LENGTH_SHORT).show();

                            NamaBarang.setText("");
                            KeteranganBarang.setText("");
                            HargaBarang.setText("");

                            NamaBarang.requestFocus();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Snackbar.make(findViewById(R.id.parent) , "Terjadi Kesalahan" , Snackbar.LENGTH_SHORT).show();
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });

                }
                break;
            case R.id.btnLihat:

                startActivity(new Intent(MainActivity.this , ListBarang.class));
                break;

        }

    }
}
