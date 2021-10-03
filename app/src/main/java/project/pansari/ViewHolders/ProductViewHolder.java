package project.pansari.ViewHolders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.databinding.SingleProductViewBinding;
import project.pansari.models.Product;

public class ProductViewHolder<T extends ProductActionClickListener> extends RecyclerView.ViewHolder {

    private ProductActionClickListener actionClickListener;
    private SingleProductViewBinding binding;

    private ProductViewHolder(SingleProductViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ProductViewHolder(SingleProductViewBinding binding, T context) {
        this(binding);

        actionClickListener = context;
    }

    public void setData(Product p) {

        if (p.getImage() != null) {
            Glide.with(itemView).load(p.getImage()).into(binding.singleProductImage);

        }

        binding.setProduct(p);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionClickListener.onProductActionClick(p.getPid());
            }
        });
    }

}

