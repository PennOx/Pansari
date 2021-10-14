package project.pansari.viewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import project.pansari.databinding.SingleOrderBannerBinding;
import project.pansari.models.OrderWrap;

public class OrderBannerViewHolder extends RecyclerView.ViewHolder {

    private SingleOrderBannerBinding binding;

    public OrderBannerViewHolder(@NonNull SingleOrderBannerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setData(OrderWrap orderWrap) {

        binding.setOrder(orderWrap);
    }
}
