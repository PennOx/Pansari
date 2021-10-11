package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.ShopkeeperCartProductsRecyclerAdapter;
import project.pansari.databinding.FragmentShopkeeperCartBinding;
import project.pansari.models.CartItem;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;

public class ShopkeeperCart extends Fragment {

    private ShopkeeperMainActivityViewModel viewModel;
    private ShopkeeperCartProductsRecyclerAdapter adapter;

    private FragmentShopkeeperCartBinding binding;

    public ShopkeeperCart(ShopkeeperMainActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopkeeper_cart, container, false);

        binding.shopkeeperCartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getInCartProducts().observe(getActivity(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> productList) {
                binding.shopkeeperCartSwipeRefreshLayout.setRefreshing(false);
                adapter = new ShopkeeperCartProductsRecyclerAdapter(viewModel.getInCartProducts().getValue());
                binding.shopkeeperCartRecycler.setAdapter(adapter);
            }
        });

        binding.shopkeeperCartSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });

        binding.shopkeeperCartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckoutClick();
            }
        });

        return binding.getRoot();
    }

    private void onCheckoutClick() {
        startOrderSummaryActivity();
    }

    private void startOrderSummaryActivity() {
        Toast.makeText(getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    private void onSwipeRefresh() {
        viewModel.refreshCartProducts();
    }
}