package project.pansari.WholesalerMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import project.pansari.R;
import project.pansari.WholesalerAddProduct;

public class WholesalerProducts extends Fragment {


    public WholesalerProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wholesaler_products, container, false);

        FloatingActionButton fab = view.findViewById(R.id.wholesaler_add_product_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WholesalerAddProduct.class);
                startActivity(intent);
            }
        });

        return view;
    }
}