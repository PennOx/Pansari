package project.pansari.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.R;
import project.pansari.models.Wholesaler;

public class WholesalerListHolder<T extends ViewClickListener> extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView name;
    private TextView address;
    private T listener;

    public WholesalerListHolder(@NonNull View itemView, T listener) {
        super(itemView);
        image = itemView.findViewById(R.id.singleWholesalerImage);
        name = itemView.findViewById(R.id.single_wholesaler_name);
        address = itemView.findViewById(R.id.single_wholesaler_address);
        this.listener = listener;
    }

    public void setData(Wholesaler w) {
        if (w.getImage() != null) {
            Glide.with(itemView).load(w.getImage()).into(image);
        }

        name.setText(w.getShopName());
        address.setText(w.getAddress());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onViewClickListener(w.getWid());
            }
        });
    }

    public void setEnable(Boolean status) {
        itemView.setEnabled(status);
    }
}
