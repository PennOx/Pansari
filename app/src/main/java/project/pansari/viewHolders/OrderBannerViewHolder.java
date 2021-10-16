package project.pansari.viewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        binding.setOnBannerClickListener(v -> listener.onClickOrderBanner(getAdapterPosition()));

        binding.setOnViewOrderClickListener(v -> listener.onClickViewOrder(getAdapterPosition()));
    }
}
