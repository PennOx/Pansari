package project.pansari.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.pansari.R;
import project.pansari.models.Wholesaler;

public class FavoriteHolder extends RecyclerView.ViewHolder {

    private CircleImageView image;

    public FavoriteHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.favorite_imageview);
    }

    public void setData(Wholesaler w) {
        if (w.getImage() != null && !w.getImage().isEmpty()) {
            Glide.with(image.getContext()).load(w.getImage()).into(image);
        }
    }
}
