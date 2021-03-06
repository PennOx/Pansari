package project.pansari.shopkeeperPackage.WholesalerProductOverviewPackage;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.WholesalerProductsRecyclerAdapter;
import project.pansari.models.Product;
import project.pansari.models.Wholesaler;
import project.pansari.viewHolders.ProductActionClickListener;

public class WholesalerProductsOverview extends AppCompatActivity implements ProductActionClickListener {

    private ImageView image;
    private TextView name;
    private TextView address;
    private FloatingActionButton call;
    private RecyclerView productsRecycler;
    private RecyclerView.Adapter<project.pansari.viewHolders.ProductViewHolder> adapter;

    private WholesalerProductOverviewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_products_overview);

        String wid = getIntent().getStringExtra("wid");
        image = findViewById(R.id.singleWholesalerImage);
        name = findViewById(R.id.wholesaler_overview_name);
        address = findViewById(R.id.wholesaler_overview_address);
        call = findViewById(R.id.wholesaler_overview_call);

        productsRecycler = findViewById(R.id.wholesaler_overview_products_recycler);
        productsRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                T vm = (T) new WholesalerProductOverviewViewModel(getApplication(), wid);
                return vm;
            }
        }).get(WholesalerProductOverviewViewModel.class);

        adapter = new WholesalerProductsRecyclerAdapter<>(WholesalerProductsOverview.this, (List<Product>) viewModel.getWholesalerProducts().getValue());
        productsRecycler.setAdapter(adapter);

        viewModel.getWholesalerProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getCurrentWholesaler().observe(this, new Observer<Wholesaler>() {
            @Override
            public void onChanged(Wholesaler wholesaler) {
                setData(wholesaler);
            }
        });

    }

    private void setData(Wholesaler w) {
        if (w.getImage() != null) {
            Glide.with(this).load(w.getImage()).into(image);
        }
        name.setText(w.getName());
        address.setText(w.getAddress());
    }

    @Override
    public void onProductActionClick(String pid) {
        viewModel.addToCart(pid);
    }

}
