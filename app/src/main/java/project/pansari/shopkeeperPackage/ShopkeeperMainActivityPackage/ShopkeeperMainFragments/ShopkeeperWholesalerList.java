package project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.WholesalerBannerRecyclerAdapter;
import project.pansari.models.Wholesaler;
import project.pansari.shopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;
import project.pansari.shopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductsOverview;
import project.pansari.viewHolders.ViewClickListener;

public class ShopkeeperWholesalerList extends Fragment implements ViewClickListener {

    private RecyclerView.Adapter adapter;

    private ShopkeeperMainActivityViewModel viewModel;

    public ShopkeeperWholesalerList(ShopkeeperMainActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new WholesalerBannerRecyclerAdapter(viewModel.getAvailableWholesalers().getValue(), this);

        viewModel.getAvailableWholesalers().observe(this, new Observer<List<Wholesaler>>() {
            @Override
            public void onChanged(List<Wholesaler> wholesalers) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopkeeper_wholesaler_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.shopkeeper_wholesaler_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewClickListener(String wid) {
        Intent intent = new Intent(getContext(), WholesalerProductsOverview.class);
        intent.putExtra("wid", wid);
        startActivity(intent);
    }
}

