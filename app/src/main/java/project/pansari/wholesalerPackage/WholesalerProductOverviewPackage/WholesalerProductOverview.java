package project.pansari.wholesalerPackage.WholesalerProductOverviewPackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private final View.OnClickListener onClickEditProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product p = binding.getProduct();

            viewModel.editProduct(p);

            Toast.makeText(WholesalerProductOverview.this, "Product edited.", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    private final View.OnClickListener onCLickAddProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product p = binding.getProduct();
            viewModel.addProduct(p);
            Toast.makeText(WholesalerProductOverview.this, "Product added.", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    private final View.OnClickListener onImageClick = v -> getImageFromUser();

    private final Observer<Boolean> newProductObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean) {
                binding.setAppbarTitle(getString(R.string.add_product));
                binding.setActionButtonText(getString(R.string.add_product));
                binding.setOnActionClick(onCLickAddProduct);
                binding.setOnImageClick(onImageClick);
                binding.setProduct(new Product());
            } else {

                binding.setAppbarTitle(getString(R.string.edit_product));
                binding.setActionButtonText(getString(R.string.edit_product));
                binding.setOnActionClick(onClickEditProduct);
                binding.setOnImageClick(onImageClick);
            }
        }
    };

    private final Observer<Product> productsObserver = this::setData;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 997) {
            if (resultCode == RESULT_OK) {
                setImage(data.getData());
            }
        } else {
            Toast.makeText(this, "Why are we here?", Toast.LENGTH_LONG).show();
        }
    }

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

        viewModel.isNewProduct().observe(this, newProductObserver);

        viewModel.getProduct().observe(this, productsObserver);
    }

    private void getImageFromUser() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 997);
    }

    private void setImage(Uri imageUri) {
        viewModel.setNewImageUri(imageUri);
    }

    private void setData(Product p) {

        binding.setProduct(p);

        if (p.getImage() != null) {
            Glide.with(this).load(p.getImage()).into(binding.productOverviewImage);
        }
    }

}