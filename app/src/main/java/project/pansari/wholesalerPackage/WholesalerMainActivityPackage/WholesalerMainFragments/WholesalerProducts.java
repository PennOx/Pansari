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
import project.pansari.adapters.WholesalerProductsRecyclerAdapter;
import project.pansari.databinding.FragmentWholesalerProductsBinding;
import project.pansari.models.Product;
import project.pansari.viewHolders.ProductActionClickListener;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;
import project.pansari.wholesalerPackage.WholesalerProductOverviewPackage.WholesalerProductOverview;

public class WholesalerProducts extends Fragment implements ProductActionClickListener {

    private FragmentWholesalerProductsBinding binding;
    private WholesalerMainActivityViewModel viewModel;
    private WholesalerProductsRecyclerAdapter<WholesalerProducts> adapter;

    public WholesalerProducts() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(WholesalerMainActivityViewModel.class);
        adapter = new WholesalerProductsRecyclerAdapter<>(this,
                viewModel.getWholesalerProducts().getValue());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wholesaler_products, container, false);

        binding.wholesalerAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WholesalerProductOverview.class);
                startActivity(intent);
            }
        });

        binding.wholesalerProductsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshProducts();
            }
        });

        viewModel.getWholesalerProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                binding.wholesalerProductsSwipeRefreshLayout.setRefreshing(false);
                adapter = new WholesalerProductsRecyclerAdapter<>(WholesalerProducts.this, productList);
                binding.wholesalerProductsRecycler.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onProductActionClick(String pid) {
        Intent intent = new Intent(getContext(), WholesalerProductOverview.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }
}

