package project.pansari;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import project.pansari.WholesalerMainFragments.WholesalerCompletedOrders;
import project.pansari.WholesalerMainFragments.WholesalerPendingOrders;
import project.pansari.WholesalerMainFragments.WholesalerProducts;
import project.pansari.WholesalerMainFragments.WholesalerSettings;

public class WholesalerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_main);

        ViewPager2 vpg = findViewById(R.id.wholesaler_main_pager);
        vpg.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new WholesalerPendingOrders();
                    case 1:
                        return new WholesalerCompletedOrders();
                    case 2:
                        return new WholesalerProducts();
                    case 3:
                        return new WholesalerSettings();
                }
                return null;
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        TabLayout tl = findViewById(R.id.wholesaler_main_tablayout);
        new TabLayoutMediator(tl, vpg,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position) {
                            case 0:
                                tab.setText("Pending");
                                break;
                            case 1:
                                tab.setText("Completed");
                                break;
                            case 2:
                                tab.setText("Products");
                                break;
                            case 3:
                                tab.setText("Settings");
                                break;

                        }
                    }
                }).attach();

    }
}