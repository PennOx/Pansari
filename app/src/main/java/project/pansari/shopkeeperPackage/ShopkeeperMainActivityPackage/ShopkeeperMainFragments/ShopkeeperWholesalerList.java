package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

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

    public ShopkeeperWholesalerList(ShopkeeperMainActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wholesalersAdapter = new WholesalerBannerRecyclerAdapter(viewModel.getAvailableWholesalers().getValue(), this);
        favoriteWholesalersAdapter = new FavoriteWholesalerRecyclerAdapter(viewModel.getFavoriteWholesalers().getValue(), this);

        viewModel.getAvailableWholesalers().observe(this, new Observer<List<Wholesaler>>() {
            @Override
            public void onChanged(List<Wholesaler> wholesalers) {
                wholesalersAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getFavoriteWholesalers().observe(this, new Observer<List<Wholesaler>>() {
            @Override
            public void onChanged(List<Wholesaler> wholesalers) {
                favoriteWholesalersAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopkeeper_wholesaler_list, container, false);

        binding.shopkeeperWholesalersListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.shopkeeperWholesalerListFavoriteWholesalersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.shopkeeperWholesalersListRecycler.setAdapter(wholesalersAdapter);
        binding.shopkeeperWholesalerListFavoriteWholesalersRecycler.setAdapter(favoriteWholesalersAdapter);

        return binding.getRoot();
    }

    @Override
    public void onWholesalerBannerClicked(String wid) {
        Intent intent = new Intent(getContext(), WholesalerProductsOverview.class);
        intent.putExtra("wid", wid);
        startActivity(intent);
    }
}

