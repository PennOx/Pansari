package project.pansari.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.ViewHolders.ProductActionClickListener;
import project.pansari.ViewHolders.ProductViewHolder;
import project.pansari.databinding.SingleProductViewBinding;
import project.pansari.models.Product;

public class WholesalerProductsRecyclerAdapter<T extends ProductActionClickListener> extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> productList;
    private T context;

    public WholesalerProductsRecyclerAdapter(T context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleProductViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_product_view, parent, false);
        return new ProductViewHolder<>(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.setData(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
