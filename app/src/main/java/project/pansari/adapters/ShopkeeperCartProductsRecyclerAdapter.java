package project.pansari.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.databinding.SingleCartProductCardBinding;
import project.pansari.models.CartItem;
import project.pansari.viewHolders.CartProductHolder;

public class ShopkeeperCartProductsRecyclerAdapter extends RecyclerView.Adapter<CartProductHolder> {

    private List<CartItem> products;

    public ShopkeeperCartProductsRecyclerAdapter(List<CartItem> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public CartProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleCartProductCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_cart_product_card, parent, false);
        return new CartProductHolder(binding);
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
