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
import project.pansari.adapters.FavoriteWholesalerRecyclerAdapter;
import project.pansari.adapters.WholesalerBannerRecyclerAdapter;
import project.pansari.databinding.FragmentShopkeeperWholesalerListBinding;
import project.pansari.models.Wholesaler;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;
import project.pansari.shopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductsOverview;
import project.pansari.viewHolders.ViewClickListener;

public class ShopkeeperWholesalerList extends Fragment implements ViewClickListener {

    private WholesalerBannerRecyclerAdapter wholesalersAdapter;
    private FavoriteWholesalerRecyclerAdapter favoriteWholesalersAdapter;

    private ShopkeeperMainActivityViewModel viewModel;
    private FragmentShopkeeperWholesalerListBinding binding;

    public ShopkeeperWholesalerList() {
        //Require Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ShopkeeperMainActivityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopkeeper_wholesaler_list, container, false);

        binding.shopkeeperWholesalersListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.shopkeeperWholesalerListFavoriteWholesalersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.shopkeeperWholesalerListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shopkeeperWholesalerListSwipeRefreshLayout.setRefreshing(false);
                viewModel.refreshAvailableWholesalers();
                viewModel.refreshFavoriteWholesalers();
            }
        });

        viewModel.getAvailableWholesalers().observe(getViewLifecycleOwner(), new Observer<List<Wholesaler>>() {
            @Override
            public void onChanged(List<Wholesaler> wholesalers) {
                wholesalersAdapter = new WholesalerBannerRecyclerAdapter(viewModel.getAvailableWholesalers().getValue(), ShopkeeperWholesalerList.this);
                binding.shopkeeperWholesalersListRecycler.setAdapter(wholesalersAdapter);
            }
        });

        viewModel.getFavoriteWholesalers().observe(getViewLifecycleOwner(), new Observer<List<Wholesaler>>() {
            @Override
            public void onChanged(List<Wholesaler> wholesalers) {
                favoriteWholesalersAdapter = new FavoriteWholesalerRecyclerAdapter(viewModel.getFavoriteWholesalers().getValue(), ShopkeeperWholesalerList.this);
                binding.shopkeeperWholesalerListFavoriteWholesalersRecycler.setAdapter(favoriteWholesalersAdapter);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onWholesalerBannerClicked(String wid) {
        Intent intent = new Intent(getContext(), WholesalerProductsOverview.class);
        intent.putExtra("wid", wid);
        startActivity(intent);
    }
}

