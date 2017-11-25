package jonesrandom.firebasesimplecrud;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import jonesrandom.firebasesimplecrud.List.HolderBarang;
import jonesrandom.firebasesimplecrud.Utils.NumberTextWatcher;
import jonesrandom.firebasesimplecrud.model.ModelBarang;

public class DetailBarang extends AppCompatActivity {

    @BindView(R.id.namaBarang)
    EditText NamaBarang;

    @BindView(R.id.hargaBarang)
    EditText HargaBarang;

    @BindView(R.id.keteranganBarang)
    EditText KeteranganBarang;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String Nama;
    String Harga;
    String Keterangan;
    String Node;

    FirebaseDatabase dbFirebase;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        ButterKnife.bind(this);

        dbFirebase = FirebaseDatabase.getInstance();
        dbReference = dbFirebase.getReference("barang");

        toolbar.setTitle("Detail ModelBarang");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        Nama = getIntent().getStringExtra(HolderBarang.NAMA);
        Harga = getIntent().getStringExtra(HolderBarang.HARGA);
        Keterangan = getIntent().getStringExtra(HolderBarang.KETERANGAN);
        Node = getIntent().getStringExtra(HolderBarang.NODE);

        Log.d("DETAIL", "loadData: " + Node);

        NamaBarang.setText(Nama);
        HargaBarang.setText(Harga);
        KeteranganBarang.setText(Keterangan);

        HargaBarang.addTextChangedListener(new NumberTextWatcher(HargaBarang));

    }

    @OnClick({R.id.btnSave , R.id.btnHapus})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.btnHapus:

                AlertDialog.Builder build = new AlertDialog.Builder(DetailBarang.this);
                build.setTitle("Hapus Data");
                build.setMessage("Apakah Anda Yakin Ingin Menghapus " + Nama + "?");
                build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(Node);
                    }
                });
                build.setNegativeButton("NO" , null);
                build.create().show();

                break;

            case R.id.btnSave:

                Nama = NamaBarang.getText().toString();
                Harga = HargaBarang.getText().toString();
                Keterangan = KeteranganBarang.getText().toString();

                if (Nama.isEmpty() || Harga.isEmpty() || Keterangan.isEmpty()){

                    Snackbar.make(findViewById(R.id.parent) , "Lengkapi Form Untuk Menyimpan ModelBarang" , Snackbar.LENGTH_SHORT).show();
                } else {

                    ModelBarang barang = new ModelBarang();
                    barang.setNama(Nama);
                    barang.setHarga(Harga);
                    barang.setKeterangan(Keterangan);

                    dbReference.child(Node).setValue(barang);
                    dbReference.child(Node).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Snackbar.make(findViewById(R.id.parent) , "Terjadi Kesalahan Saat Menyimpan" , Snackbar.LENGTH_SHORT).show();
                            Log.d("DetailBarang", "onCancelled: " + databaseError.getMessage());
                        }
                    });
                }
                break;
        }
    }

    private void delete(String Nodes){
        dbReference.child(Nodes).removeValue();
        dbReference.child(Nodes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("nama").getValue() == null){
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(findViewById(R.id.parent) , "Terjadi Kesalahan Saat Menghapus" , Snackbar.LENGTH_SHORT).show();
                Log.d("DetailBarang", "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
