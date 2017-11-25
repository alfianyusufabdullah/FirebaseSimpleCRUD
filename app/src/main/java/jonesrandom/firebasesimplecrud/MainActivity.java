package jonesrandom.firebasesimplecrud;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonesrandom.firebasesimplecrud.Utils.NumberTextWatcher;
import jonesrandom.firebasesimplecrud.model.ModelBarang;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.namaBarang)
    EditText NamaBarang;

    @BindView(R.id.hargaBarang)
    EditText HargaBarang;

    @BindView(R.id.keteranganBarang)
    EditText KeteranganBarang;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    DatabaseReference dbReferences;
    FirebaseDatabase dbFirebase;

    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        dbFirebase = FirebaseDatabase.getInstance();

        loadStatus();

        if (toolbar != null){
            toolbar.setTitle("Tambah Data");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        HargaBarang.addTextChangedListener(new NumberTextWatcher(HargaBarang));

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

    @OnClick(R.id.btnSave)
    public void onClick(View v) {

        String Barangs = NamaBarang.getText().toString();
        String Keterangan = KeteranganBarang.getText().toString();
        final String Harga = HargaBarang.getText().toString();

        if (Barangs.isEmpty() || Keterangan.isEmpty() || Harga.isEmpty()) {

            Snackbar.make(findViewById(R.id.parent), "Lengkapi Form Untuk Menyimpan ModelBarang", Snackbar.LENGTH_SHORT).show();
        } else {

            dbReferences = dbFirebase.getReference("barang");
            UserID = dbReferences.push().getKey();
            final ModelBarang barang = new ModelBarang(Barangs, Keterangan, Harga);

            dbReferences.child(UserID).setValue(barang);
            dbReferences.child(UserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Snackbar.make(findViewById(R.id.parent), "Berhasil Menambah Data", Snackbar.LENGTH_SHORT).show();

                    NamaBarang.setText("");
                    KeteranganBarang.setText("");
                    HargaBarang.setText("");

                    NamaBarang.requestFocus();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Snackbar.make(findViewById(R.id.parent), "Terjadi Kesalahan", Snackbar.LENGTH_SHORT).show();
                    Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                }
            });

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
