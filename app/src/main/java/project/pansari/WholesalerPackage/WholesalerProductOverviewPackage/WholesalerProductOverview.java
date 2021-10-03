package project.pansari.WholesalerPackage.WholesalerProductOverviewPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import project.pansari.R;
import project.pansari.databinding.LayoutProductOverviewBinding;
import project.pansari.models.Product;

public class WholesalerProductOverview extends AppCompatActivity {

    private LayoutProductOverviewBinding binding;
    private WholesalerProductOverviewVM viewModel;

    private View.OnClickListener onClickEditProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product p = binding.getProduct();

            viewModel.editProduct(p);

            Toast.makeText(WholesalerProductOverview.this, "Product edited.", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    private View.OnClickListener onCLickAddProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product p = binding.getProduct();
            viewModel.addProduct(p);
            Toast.makeText(WholesalerProductOverview.this, "Product added.", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_product_overview);

        setSupportActionBar(binding.productOverviewToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new WholesalerProductOverviewVM(getIntent().getStringExtra("pid"));
            }
        }).get(WholesalerProductOverviewVM.class);

        viewModel.isNewProduct().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.setAppbarTitle(getString(R.string.add_product));
                    binding.setActionButtonText(getString(R.string.add_product));
                    binding.setOnActionClick(onCLickAddProduct);
                    binding.setProduct(new Product());
                } else {

                    binding.setAppbarTitle(getString(R.string.edit_product));
                    binding.setActionButtonText(getString(R.string.edit_product));
                    binding.setOnActionClick(onClickEditProduct);
                }
            }
        });

        viewModel.getProduct().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                setData(product);
            }
        });
    }

    private void setData(Product p) {

        binding.setProduct(p);

        if (p.getImage() != null) {
            Glide.with(this).load(p.getImage()).into(binding.productOverviewImage);
        }
    }
}