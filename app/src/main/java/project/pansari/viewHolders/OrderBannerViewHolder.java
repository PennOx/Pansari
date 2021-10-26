package project.pansari.viewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.databinding.SingleOrderBannerBinding;
import project.pansari.models.OrderWrap;

public class OrderBannerViewHolder extends RecyclerView.ViewHolder {

    private SingleOrderBannerBinding binding;
    private OrderBannerClickListener listener;

    public OrderBannerViewHolder(@NonNull SingleOrderBannerBinding binding, OrderBannerClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void setData(OrderWrap orderWrap) {

        binding.setOrder(orderWrap);

        Glide.with(binding.getRoot()).load(orderWrap.getUser().getImage()).into(binding.singleCartProductWholesalerImage);

        binding.setOnBannerClickListener(v -> listener.onClickOrderBanner(getAdapterPosition()));

        binding.setOnViewOrderClickListener(v -> listener.onClickViewOrder(getAdapterPosition()));
    }
}
