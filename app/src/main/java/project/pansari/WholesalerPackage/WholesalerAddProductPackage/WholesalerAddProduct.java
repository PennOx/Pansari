package project.pansari.WholesalerPackage.WholesalerAddProductPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import project.pansari.Models.Product;
import project.pansari.R;

public class WholesalerAddProduct extends AppCompatActivity {

    private ImageView image;
    private FloatingActionButton addImage;
    private EditText name;
    private EditText brand;
    private EditText quantity;
    private EditText description;
    private ExtendedFloatingActionButton action;

    private WholesalerAddProductViewModel viewModel;
    private View.OnClickListener onClickEditProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product p = new Product();
            p.setName(name.getText().toString());
            p.setBrand(brand.getText().toString());
            p.setQuantity(quantity.getText().toString());
            p.setDescription(description.getText().toString());

            viewModel.editProduct(p);

            Toast.makeText(WholesalerAddProduct.this, "Product edited.", Toast.LENGTH_LONG).show();
            finish();
        }
    };
    private View.OnClickListener onCLickAddProduct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = new Product();

                    p.setName(name.getText().toString());
                    p.setBrand(brand.getText().toString());
                    p.setQuantity(quantity.getText().toString());
                    p.setDescription(description.getText().toString());

                    viewModel.addProduct(p);

                    Toast.makeText(WholesalerAddProduct.this, "Product added.", Toast.LENGTH_LONG).show();
                    finish();
                }
            };
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_add_product);

        image = findViewById(R.id.product_overview_image);
        addImage = findViewById(R.id.product_overview_edit_image_floating_button);
        name = ((TextInputLayout) findViewById(R.id.product_overview_name)).getEditText();
        brand = ((TextInputLayout) findViewById(R.id.product_overview_brand)).getEditText();
        quantity = ((TextInputLayout) findViewById(R.id.product_overview_quantity)).getEditText();
        description = ((TextInputLayout) findViewById(R.id.product_overview_description)).getEditText();
        action = findViewById(R.id.product_overview_action_button);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new WholesalerAddProductViewModel(getIntent().getStringExtra("pid"));
            }
        }).get(WholesalerAddProductViewModel.class);

        viewModel.isNewProduct().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    action.setText("Edit Product");
                    action.setOnClickListener(onClickEditProduct);
                } else {


                    action.setText("Add product");
                    action.setOnClickListener(onCLickAddProduct);
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

        name.setText(p.getName());
        brand.setText(p.getBrand());
        quantity.setText(p.getQuantity());
        description.setText(p.getDescription());

        if (p.getImage() != null) {
            Glide.with(this).load(p.getImage()).into(image);
        }
    }
}