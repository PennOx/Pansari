package project.pansari.LoginAndSignupPackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import project.pansari.LoginAndSignupPackage.LoginPackage.LoginActivity;
import project.pansari.R;

public class WelcomeActivity extends AppCompatActivity {

    RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        public final int[] images = {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3};

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.welcome_slider_layout, parent, false);

            return new RecyclerView.ViewHolder(view) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ImageView imageView = holder.itemView.findViewById(R.id.welcome_slider_image);
            imageView.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

    };

    private LinearLayout dotsLayout;
    ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dotsLayout = findViewById(R.id.welcome_dots_layout);
        addDotsIndicator(0);


        ViewPager2 vpg = findViewById(R.id.welcome_pager);
        vpg.setAdapter(adapter);
        vpg.registerOnPageChangeCallback(callback);

    }

    private void addDotsIndicator(int active) {
        TextView[] dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < 3; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            if (active == i) {
                dots[i].setTextColor(getResources().getColor(R.color.primary_color));
            } else {
                dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            }

            dotsLayout.addView(dots[i]);

        }

    }

    private void startLoginActivity(char type) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }


    public void shopkeeperLogin(View view) {
        startLoginActivity('s');
    }

    public void wholesalerLogin(View view) {
        startLoginActivity('w');

    }
}