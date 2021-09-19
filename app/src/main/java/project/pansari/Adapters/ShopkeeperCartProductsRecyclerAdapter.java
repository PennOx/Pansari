package project.pansari.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.Models.Product;
import project.pansari.R;
import project.pansari.ViewHolders.CartProductHolder;

public class ShopkeeperCartProductsRecyclerAdapter extends RecyclerView.Adapter<CartProductHolder> {

    private List<Product> products;

    public ShopkeeperCartProductsRecyclerAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public CartProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_product_card, parent, false);
        return new CartProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductHolder holder, int position) {
        holder.setData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
