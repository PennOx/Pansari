package project.pansari.shopkeeperPackage.shopkeeperOrderViewActivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    private final Observer<OrderWrap> orderObserver = new Observer<OrderWrap>() {
        @Override
        public void onChanged(OrderWrap orderWrap) {
            binding.setOrder(orderWrap);
        }
    };

    private final Observer<List<CartProduct>> orderProductsObserver = new Observer<List<CartProduct>>() {
        @Override
        public void onChanged(List<CartProduct> products) {
            OrderProductsRecyclerAdapter adapter = new OrderProductsRecyclerAdapter(products);
            binding.orderRequestOrdersRecycler.setAdapter(adapter);
        }
    };

    private final View.OnClickListener onCancelOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderViewActivity.this);
            builder.setMessage("Do you really want to cancel this order.");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> viewModel.cancelOrder());

            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        }
    };

    private final Observer<Boolean> orderCancelFlagObserver = flag -> {
        if (flag) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_request_overview);
        binding.orderRequestAcceptButton.setVisibility(View.GONE);
        binding.orderRequestDeclineButton.setVisibility(View.GONE);
        binding.orderRequestCancelButton.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderViewActivityViewModel(getIntent().getStringExtra("oid"));
            }
        }).get(OrderViewActivityViewModel.class);

        viewModel.getOrderWrap().observe(this, orderObserver);

        viewModel.getOrderProducts().observe(this, orderProductsObserver);

        viewModel.getOrderCancelFlag().observe(this, orderCancelFlagObserver);

        binding.setOrderCancelClickListener(onCancelOrderClick);

    }
}