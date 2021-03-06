package project.pansari.viewHolders;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import project.pansari.database.Database;
import project.pansari.databinding.SingleCartProductCardBinding;
import project.pansari.databinding.SingleCartProductItemBinding;
import project.pansari.models.CartItem;
import project.pansari.models.CartProduct;

public class CartProductHolder extends RecyclerView.ViewHolder {

    private SingleCartProductCardBinding binding;

    public CartProductHolder(SingleCartProductCardBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void setData(CartItem p) {
        binding.setItem(p);

        binding.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.singleCartProductsLayout.getVisibility() == View.VISIBLE) {
                    binding.singleCartProductsLayout.setVisibility(View.GONE);
                } else {
                    binding.singleCartProductsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        for (CartProduct cartProduct : p.getProducts()) {

            SingleCartProductItemBinding bind = SingleCartProductItemBinding.inflate(LayoutInflater.from(itemView.getContext()), binding.singleCartProductsLayout, false);
            bind.setProduct(cartProduct);
            bind.setIncreaseListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database.getCartRef().child(cartProduct.getSellerId()).child(cartProduct.getPid()).setValue(cartProduct.getInCartQuantity() + 1);
                    cartProduct.setInCartQuantity(cartProduct.getInCartQuantity() + 1);
                    bind.invalidateAll();
                }
            });

            bind.setDecreaseListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartProduct.getInCartQuantity() <= 1) {
                        Database.getCartRef().child(cartProduct.getSellerId()).child(cartProduct.getPid()).setValue(null);
                        cartProduct.setInCartQuantity(1);
                    } else {
                        Database.getCartRef().child(cartProduct.getSellerId()).child(cartProduct.getPid()).setValue(cartProduct.getInCartQuantity() - 1);

                    }
                    cartProduct.setInCartQuantity(cartProduct.getInCartQuantity() - 1);
                    bind.invalidateAll();
                }
            });


            bind.setLifecycleOwner(binding.getLifecycleOwner());
            binding.singleCartProductsLayout.addView(bind.getRoot());
        }
    }
}
