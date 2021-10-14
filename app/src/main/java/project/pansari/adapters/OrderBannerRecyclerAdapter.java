package project.pansari.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.databinding.SingleOrderBannerBinding;
import project.pansari.models.OrderWrap;
import project.pansari.viewHolders.OrderBannerViewHolder;

public class OrderBannerRecyclerAdapter extends RecyclerView.Adapter<OrderBannerViewHolder> {

    private List<OrderWrap> orders;

    public OrderBannerRecyclerAdapter(List<OrderWrap> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleOrderBannerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_order_banner, parent, false);

        return new OrderBannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderBannerViewHolder holder, int position) {

        holder.setData(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
