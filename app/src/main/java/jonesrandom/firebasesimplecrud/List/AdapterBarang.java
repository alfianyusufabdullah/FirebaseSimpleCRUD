package jonesrandom.firebasesimplecrud.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jonesrandom.firebasesimplecrud.Barang;
import jonesrandom.firebasesimplecrud.R;

/**
 * Created by JonesRandom on 9/6/17.
 * https://masx-dev.blogspot.com
 */

public class AdapterBarang extends RecyclerView.Adapter<HolderBarang>{

    private List<Barang> data;
    private Context ctx;

    public AdapterBarang(Context ctx ,List<Barang> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public HolderBarang onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_barang , parent , false);
        return new HolderBarang(v);
    }

    @Override
    public void onBindViewHolder(HolderBarang holder, int position) {
        holder.setContent(data.get(position) , ctx);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
