package project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.pansari.Adapters.WholesalerProductsRecyclerAdapter;
import project.pansari.Models.Product;
import project.pansari.R;
import project.pansari.ViewHolders.ProductActionClickListener;
import project.pansari.WholesalerPackage.WholesalerAddProductPackage.WholesalerAddProduct;
import project.pansari.WholesalerPackage.WholesalerMainActivityPackage.WholesalerMainActivityViewModel;

public class WholesalerProducts extends Fragment implements ProductActionClickListener {

    private WholesalerMainActivityViewModel viewModel;
    private RecyclerView.Adapter<project.pansari.ViewHolders.ProductViewHolder> adapter;
    private RecyclerView recycler;
    private FloatingActionButton fab;


    public WholesalerProducts(WholesalerMainActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                Intent intent = new Intent(getContext(), WholesalerAddProduct.class);
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
        Intent intent = new Intent(getContext(), WholesalerAddProduct.class);
        intent.putExtra("pid", pid);
        Toast.makeText(getContext(), pid, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}

