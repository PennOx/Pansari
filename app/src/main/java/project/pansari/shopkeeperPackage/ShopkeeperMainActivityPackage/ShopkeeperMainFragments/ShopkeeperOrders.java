package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.OrderBannerRecyclerAdapter;
import project.pansari.databinding.FragmentShopkeeperOrdersBinding;
import project.pansari.models.OrderWrap;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;
import project.pansari.shopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductsOverview;
import project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity.OrderOverviewActivity;
import project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity.OrderViewActivity;
import project.pansari.viewHolders.OrderBannerClickListener;

public class ShopkeeperOrders extends Fragment implements OrderBannerClickListener {

    private ShopkeeperMainActivityViewModel viewModel;
    private FragmentShopkeeperOrdersBinding binding;
    private OrderBannerRecyclerAdapter adapter;

    public ShopkeeperOrders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ShopkeeperMainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopkeeper_orders, container, false);
        binding.shopkeeperOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.shopkeeperOrdersSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shopkeeperOrdersSwipeRefreshLayout.setRefreshing(false);
                viewModel.refreshOrders();
            }
        });

        viewModel.getOrders().observe(getViewLifecycleOwner(), new Observer<List<OrderWrap>>() {
            @Override
            public void onChanged(List<OrderWrap> orderWraps) {
                adapter = new OrderBannerRecyclerAdapter(orderWraps, ShopkeeperOrders.this);
                binding.shopkeeperOrdersRecycler.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClickOrderBanner(int pos) {
        Intent intent = new Intent(getContext(), WholesalerProductsOverview.class);
        intent.putExtra("wid", viewModel.getOrders().getValue().get(pos).getTo());
        startActivity(intent);
    }

    @Override
    public void onClickViewOrder(int pos) {

        String status = viewModel.getOrders().getValue().get(pos).getStatus();

        if (status.equals("Cancelled") || status.equals("Completed") || status.equals("Declined")) {
            Intent intent = new Intent(getContext(), OrderOverviewActivity.class);
            intent.putExtra("oid", viewModel.getOrders().getValue().get(pos).getId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), OrderViewActivity.class);
            intent.putExtra("oid", viewModel.getOrders().getValue().get(pos).getId());
            startActivity(intent);
        }
    }
}