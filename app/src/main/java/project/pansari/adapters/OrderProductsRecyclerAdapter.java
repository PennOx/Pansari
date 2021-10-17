package project.pansari.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.databinding.SingleOrderProductBinding;
import project.pansari.models.CartProduct;
import project.pansari.viewHolders.OrderProductViewHolder;

public class OrderProductsRecyclerAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {

    private List<CartProduct> products;

    public OrderProductsRecyclerAdapter(List<CartProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleOrderProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_order_product, parent, false);
        return new OrderProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        holder.setData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
