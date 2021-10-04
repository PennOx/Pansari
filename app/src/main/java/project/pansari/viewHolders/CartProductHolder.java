package project.pansari.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.pansari.R;
import project.pansari.models.Product;

public class CartProductHolder extends RecyclerView.ViewHolder {

    private TextView name;

    public CartProductHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.cart_card_name);
    }

    public void setData(Product p) {
        name.setText(p.getName());
    }
}
