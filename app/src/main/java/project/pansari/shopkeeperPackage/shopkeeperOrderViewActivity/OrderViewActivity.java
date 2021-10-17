package project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.OrderProductsRecyclerAdapter;
import project.pansari.databinding.ActivityOrderRequestOverviewBinding;
import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;

public class OrderViewActivity extends AppCompatActivity {

    private OrderViewActivityViewModel viewModel;
    private ActivityOrderRequestOverviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_request_overview);
        binding.orderRequestAcceptButton.setVisibility(View.GONE);
        binding.orderRequestDeclineButton.setVisibility(View.GONE);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderViewActivityViewModel(getIntent().getStringExtra("oid"));
            }
        }).get(OrderViewActivityViewModel.class);

        viewModel.getOrderWrap().observe(this, new Observer<OrderWrap>() {
            @Override
            public void onChanged(OrderWrap orderWrap) {
                binding.setOrder(orderWrap);
            }
        });

        viewModel.getOrderProducts().observe(this, new Observer<List<CartProduct>>() {
            @Override
            public void onChanged(List<CartProduct> products) {
                OrderProductsRecyclerAdapter adapter = new OrderProductsRecyclerAdapter(products);
                binding.orderRequestOrdersRecycler.setAdapter(adapter);
            }
        });

    }
}