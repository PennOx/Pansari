package project.pansari.wholesalerPackage.wholesalerOrderViewPackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import project.pansari.R;
import project.pansari.adapters.OrderProductsRecyclerAdapter;
import project.pansari.databinding.ActivityOrderOverviewBinding;
import project.pansari.models.CartProduct;
import project.pansari.models.OrderWrap;

public class OrderOverviewActivity extends AppCompatActivity {

    private ActivityOrderOverviewBinding binding;


    private Observer<OrderWrap> orderObserver = new Observer<OrderWrap>() {
        @Override
        public void onChanged(OrderWrap orderWrap) {
            binding.setOrder(orderWrap);
        }
    };
    private Observer<List<CartProduct>> orderProductsObserver = new Observer<List<CartProduct>>() {
        @Override
        public void onChanged(List<CartProduct> products) {
            OrderProductsRecyclerAdapter adapter = new OrderProductsRecyclerAdapter(products);
            binding.setRecyclerAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_overview);

        OrderViewViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderViewViewModel(getIntent().getStringExtra("oid"));
            }
        }).get(OrderViewViewModel.class);

        viewModel.getOrder().observe(this, orderObserver);
        viewModel.getOrderProducts().observe(this, orderProductsObserver);
    }
}