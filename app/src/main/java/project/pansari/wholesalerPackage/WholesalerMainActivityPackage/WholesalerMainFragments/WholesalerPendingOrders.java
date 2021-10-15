package project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.OrderBannerRecyclerAdapter;
import project.pansari.databinding.FragmentWholesalerPendingOrdersBinding;
import project.pansari.models.OrderWrap;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;

public class WholesalerPendingOrders extends Fragment {

    private OrderBannerRecyclerAdapter adapter;
    private WholesalerMainActivityViewModel viewModel;
    private FragmentWholesalerPendingOrdersBinding binding;

    public WholesalerPendingOrders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(WholesalerMainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wholesaler_pending_orders, container, false);

        binding.wholesalerPendingOrdersSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshPendingOrders();
            }
        });

        viewModel.getPendingOrders().observe(getViewLifecycleOwner(), new Observer<List<OrderWrap>>() {
            @Override
            public void onChanged(List<OrderWrap> orderWraps) {
                binding.wholesalerPendingOrdersSwipeRefreshLayout.setRefreshing(false);
                adapter = new OrderBannerRecyclerAdapter(orderWraps);
                binding.wholesalerPendingOrdersRecycler.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }
}