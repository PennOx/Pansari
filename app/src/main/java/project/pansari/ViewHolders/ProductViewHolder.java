package project.pansari.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.Models.Product;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductsOverview;

public class ProductViewHolder<T extends ProductActionClickListener> extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView name;
    private TextView description;
    private Button actionButton;
    private ProductActionClickListener actionClickListener;

    private ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.wholesaler_overview_banner);
        name = itemView.findViewById(R.id.single_product_name);
        description = itemView.findViewById(R.id.single_product_description);
        actionButton = itemView.findViewById(R.id.single_product_button);
    }

    public ProductViewHolder(@NonNull View itemView, T context) {
        this(itemView);

        if (context instanceof WholesalerProductsOverview) {
            actionButton.setText("Add to cart");
        }
        actionClickListener = context;
    }

    public void setData(Product p) {

        if (p.getImage() != null) {
            Glide.with(itemView).load(p.getImage()).into(image);

        } else {
            image.setVisibility(View.GONE);
        }

        name.setText(p.getName());
        description.setText(p.getQuantity());

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionClickListener.onProductActionClick(getBindingAdapterPosition());
            }
        });
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDescription() {
        return description;
    }

    public Button getActionButton() {
        return actionButton;
    }

}

