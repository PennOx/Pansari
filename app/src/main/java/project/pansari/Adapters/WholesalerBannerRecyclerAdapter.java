package project.pansari.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.ViewHolders.ViewClickListener;
import project.pansari.ViewHolders.WholesalerListHolder;
import project.pansari.models.Wholesaler;

public class WholesalerBannerRecyclerAdapter extends RecyclerView.Adapter<WholesalerListHolder> {

    private List<Wholesaler> wholesalers;
    private ViewClickListener listener;

    public WholesalerBannerRecyclerAdapter(List<Wholesaler> wholesalers, ViewClickListener clickListener) {
        this.wholesalers = wholesalers;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public WholesalerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_wholesaler_banner, parent, false);
        return new WholesalerListHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WholesalerListHolder holder, int position) {
        holder.setData(wholesalers.get(position));
    }

    @Override
    public int getItemCount() {
        return wholesalers.size();
    }
}
