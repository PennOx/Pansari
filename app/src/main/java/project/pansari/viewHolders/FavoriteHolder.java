package project.pansari.viewHolders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.databinding.SingleFavoriteLayoutBinding;
import project.pansari.models.Wholesaler;

public class FavoriteHolder extends RecyclerView.ViewHolder {

    private SingleFavoriteLayoutBinding binding;
    private ViewClickListener clickListener;

    public FavoriteHolder(SingleFavoriteLayoutBinding binding, ViewClickListener listener) {
        super(binding.getRoot());

        this.binding = binding;
        this.clickListener = listener;
    }

    public void setData(Wholesaler w) {
        if (w.getImage() != null && !w.getImage().isEmpty()) {
            Glide.with(binding.favoriteImageview.getContext()).load(w.getImage()).into(binding.favoriteImageview);
        }

        binding.favoriteImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onWholesalerBannerClicked(w.getWid());
            }
        });
    }
}
