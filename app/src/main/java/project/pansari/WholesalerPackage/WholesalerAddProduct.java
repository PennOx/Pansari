package project.pansari.WholesalerPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_add_product);

        String pid = getIntent().getStringExtra("pid");

        image = findViewById(R.id.product_overview_image);
        addImage = findViewById(R.id.product_overview_edit_image_floating_button);
        name = ((TextInputLayout) findViewById(R.id.product_overview_name)).getEditText();
        brand = ((TextInputLayout) findViewById(R.id.product_overview_brand)).getEditText();
        quantity = ((TextInputLayout) findViewById(R.id.product_overview_quantity)).getEditText();
        description = ((TextInputLayout) findViewById(R.id.product_overview_description)).getEditText();
        action = findViewById(R.id.product_overview_action_button);

        if (pid != null) {

            action.setText("Edit Product");

            Database.getProductsRef().child(pid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Product p = snapshot.getValue(Product.class);
                    setData(p);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = new Product();
                    p.setPid(pid);
                    p.setName(name.getText().toString());
                    p.setBrand(brand.getText().toString());
                    p.setQuantity(quantity.getText().toString());
                    p.setDescription(description.getText().toString());

                    Database.getProductsRef().child(pid).setValue(p);
                    Toast.makeText(WholesalerAddProduct.this, "Product edited.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        } else {
            action.setText("Add product");
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newPid = System.currentTimeMillis() + "";
                    Product p = new Product();
                    p.setPid(newPid);
                    p.setName(name.getText().toString());
                    p.setBrand(brand.getText().toString());
                    p.setQuantity(quantity.getText().toString());
                    p.setDescription(description.getText().toString());


                    Database.getProductsRef().child(newPid).setValue(p);
                    Database.getWholesalerProductsRef(Auth.getCurrentUserUid()).child(newPid).setValue(newPid);
                    Toast.makeText(WholesalerAddProduct.this, "Product added.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }

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