package project.pansari.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import project.pansari.Models.Product;
import project.pansari.R;

public class ProductViewHolder<T extends ProductActionClickListener> extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView name;
    private TextView description;
    private ProductActionClickListener actionClickListener;

    private ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.wholesaler_overview_banner);
        name = itemView.findViewById(R.id.single_product_name);
        description = itemView.findViewById(R.id.single_product_description);
    }

    public ProductViewHolder(@NonNull View itemView, T context) {
        this(itemView);

        actionClickListener = context;
    }

    public void setData(Product p) {

        if (p.getImage() != null) {
            Glide.with(itemView).load(p.getImage()).into(image);

        }

        name.setText(p.getName());
        description.setText(p.getQuantity());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionClickListener.onProductActionClick(p.getPid());
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

}

