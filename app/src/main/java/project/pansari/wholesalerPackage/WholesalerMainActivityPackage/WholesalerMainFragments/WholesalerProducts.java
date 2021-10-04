package project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.WholesalerProductsRecyclerAdapter;
import project.pansari.models.Product;
import project.pansari.viewHolders.ProductActionClickListener;
import project.pansari.wholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;
import project.pansari.wholesalerPackage.WholesalerProductOverviewPackage.WholesalerProductOverview;

public class WholesalerProducts extends Fragment implements ProductActionClickListener {

    private WholesalerMainActivityViewModel viewModel;
    private RecyclerView.Adapter<project.pansari.viewHolders.ProductViewHolder> adapter;
    private RecyclerView recycler;
    private FloatingActionButton fab;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wholesaler_products, container, false);

        fab = view.findViewById(R.id.wholesaler_add_product_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WholesalerProductOverview.class);
                startActivity(intent);
            }
        });

        recycler = view.findViewById(R.id.wholesaler_products_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        viewModel.getWholesalerProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onProductActionClick(String pid) {
        Intent intent = new Intent(getContext(), WholesalerProductOverview.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }
}

