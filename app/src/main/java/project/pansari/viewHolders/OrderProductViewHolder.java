package project.pansari.viewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.pansari.databinding.SingleOrderProductBinding;
import project.pansari.models.CartProduct;

public class OrderProductViewHolder extends RecyclerView.ViewHolder {

    private SingleOrderProductBinding binding;

    public OrderProductViewHolder(@NonNull SingleOrderProductBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void setData(CartProduct p) {
        binding.setProduct(p);
    }
}
