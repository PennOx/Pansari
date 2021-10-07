package project.pansari.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.databinding.SingleFavoriteLayoutBinding;
import project.pansari.models.Wholesaler;
import project.pansari.viewHolders.FavoriteHolder;
import project.pansari.viewHolders.ViewClickListener;

public class FavoriteWholesalerRecyclerAdapter extends RecyclerView.Adapter<FavoriteHolder> {

    private List<Wholesaler> favoriteWholesalers;
    private ViewClickListener listener;

    public FavoriteWholesalerRecyclerAdapter(List<Wholesaler> favoriteWholesalers, ViewClickListener clickListener) {
        this.favoriteWholesalers = favoriteWholesalers;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleFavoriteLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_favorite_layout, parent, false);
        return new FavoriteHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        holder.setData(favoriteWholesalers.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteWholesalers.size();
    }
}
