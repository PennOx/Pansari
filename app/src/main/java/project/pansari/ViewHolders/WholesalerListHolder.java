package project.pansari.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.Models.Wholesaler;
import project.pansari.R;

public class WholesalerListHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView name;
    TextView address;

    public WholesalerListHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.single_wholesaler_image);
        name = itemView.findViewById(R.id.single_wholesaler_name);
        address = itemView.findViewById(R.id.single_wholesaler_address);
    }

    public void setData(Wholesaler w) {
        if (w.getImage() != null) {
            Glide.with(itemView).load(w.getImage()).into(image);
        }

        name.setText(w.getShopName());
        address.setText(w.getAddress());
    }

    public void setEnable(Boolean status) {
        itemView.setEnabled(status);
    }
}
