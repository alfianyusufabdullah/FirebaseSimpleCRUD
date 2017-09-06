package jonesrandom.firebasesimplecrud.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jonesrandom.firebasesimplecrud.Barang;
import jonesrandom.firebasesimplecrud.DetailBarang;
import jonesrandom.firebasesimplecrud.R;

/**
 * Created by JonesRandom on 9/6/17.
 * https://masx-dev.blogspot.com
 */

public class HolderBarang extends RecyclerView.ViewHolder {

    @BindView(R.id.rowNama)
    TextView NamaBarang;

    @BindView(R.id.rowHarga)
    TextView HargaBarang;

    @BindView(R.id.parentRow)
    LinearLayout parent;

    public static final String NAMA = "nama";
    public static final String HARGA = "harga";
    public static final String KETERANGAN = "ket";
    public static final String NODE = "node";

    public HolderBarang(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setContent(final Barang barang, final Context ctx) {

        NamaBarang.setText(barang.getNama());
        HargaBarang.setText(" Rp. " + barang.getHarga());

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detail = new Intent(ctx, DetailBarang.class);
                detail.putExtra(NAMA, barang.getNama());
                detail.putExtra(KETERANGAN, barang.getKeterangan());
                detail.putExtra(HARGA, barang.getHarga());
                detail.putExtra(NODE, barang.getNode());

                ctx.startActivity(detail);

            }
        });

    }
}
