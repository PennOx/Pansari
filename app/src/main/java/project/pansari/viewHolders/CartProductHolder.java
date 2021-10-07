package project.pansari.viewHolders;

import androidx.recyclerview.widget.RecyclerView;

import project.pansari.databinding.SingleCartProductCardBinding;
import project.pansari.models.CartItem;

public class CartProductHolder extends RecyclerView.ViewHolder {

    private SingleCartProductCardBinding binding;

    public CartProductHolder(SingleCartProductCardBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void setData(CartItem p) {
        binding.setItem(p);
    }
}
