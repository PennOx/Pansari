package project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.pansari.Adapters.ShopkeeperCartProductsRecyclerAdapter;
import project.pansari.Models.Product;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.ShopkeeperMainActivityPackage.ShopkeeperMainActivityViewModel;

public class ShopkeeperCart extends Fragment {

    private ShopkeeperMainActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private ShopkeeperCartProductsRecyclerAdapter adapter;

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

        View view = inflater.inflate(R.layout.fragment_shopkeeper_cart, container, false);
        recyclerView = view.findViewById(R.id.shopkeeper_cart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ShopkeeperCartProductsRecyclerAdapter(viewModel.getInCartProducts().getValue());

        viewModel.getInCartProducts().observe(getActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}