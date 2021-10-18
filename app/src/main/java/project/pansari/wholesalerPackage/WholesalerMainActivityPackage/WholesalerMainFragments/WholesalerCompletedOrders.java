package project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.OrderBannerRecyclerAdapter;
import project.pansari.databinding.FragmentWholesalerCompletedOrdersBinding;
import project.pansari.models.OrderWrap;
import project.pansari.viewHolders.OrderBannerClickListener;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;
import project.pansari.wholesalerPackage.wholesalerOrderViewPackage.OrderOverviewActivity;

public class WholesalerCompletedOrders extends Fragment implements OrderBannerClickListener {

    private WholesalerMainActivityViewModel viewModel;
    private FragmentWholesalerCompletedOrdersBinding binding;
    private OrderBannerRecyclerAdapter adapter;

    public WholesalerCompletedOrders() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(WholesalerMainActivityViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wholesaler_completed_orders, container, false);

        binding.wholesalerCompletedOrdersSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshCompletedOrders();
            }
        });

        viewModel.getCompletedOrders().observe(getViewLifecycleOwner(), new Observer<List<OrderWrap>>() {
            @Override
            public void onChanged(List<OrderWrap> orderWraps) {
                binding.wholesalerCompletedOrdersSwipeRefreshLayout.setRefreshing(false);
                adapter = new OrderBannerRecyclerAdapter(orderWraps, WholesalerCompletedOrders.this);
                binding.wholesalerCompletedOrdersRecycler.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refreshCompletedOrders();
    }

    @Override
    public void onClickOrderBanner(int pos) {
        //Intentionally left blank
    }

    @Override
    public void onClickViewOrder(int pos) {
        Intent intent = new Intent(getContext(), OrderOverviewActivity.class);
        intent.putExtra("oid", viewModel.getCompletedOrders().getValue().get(pos).getId());
        startActivity(intent);
    }
}