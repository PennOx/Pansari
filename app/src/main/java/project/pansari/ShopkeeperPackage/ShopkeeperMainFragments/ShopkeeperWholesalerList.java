package project.pansari.ShopkeeperPackage.ShopkeeperMainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.pansari.Auth.Auth;
import project.pansari.Database.Database;
import project.pansari.Models.Wholesaler;
import project.pansari.R;
import project.pansari.ShopkeeperPackage.WholesalerProductOverviewPackage.WholesalerProductsOverview;
import project.pansari.ViewHolders.WholesalerListHolder;

public class ShopkeeperWholesalerList extends Fragment {

    FirebaseRecyclerAdapter<Boolean, WholesalerListHolder> adapter;
    RecyclerView recyclerView;

    public ShopkeeperWholesalerList() {
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
        View v = inflater.inflate(R.layout.fragment_shopkeeper_wholesaler_list, container, false);

        recyclerView = v.findViewById(R.id.shopkeeper_wholesaler_recycler);

        Database.getShopkeeperRef().child(Auth.getCurrentUser().getUid()).child("pinCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setRecycler(snapshot.getValue(Long.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    private void setRecycler(Long pin) {

        Query query = Database.getPinRef().child(pin + "").limitToLast(50);

        FirebaseRecyclerOptions<Boolean> options = new FirebaseRecyclerOptions.Builder<Boolean>()
                .setQuery(query, Boolean.class).build();

        adapter = new FirebaseRecyclerAdapter<Boolean, WholesalerListHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull WholesalerListHolder holder, int position, @NonNull Boolean status) {
                String wid = getRef(position).getKey();
                holder.setEnable(status);
                Database.getWholesalersRef().child(wid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Wholesaler w = snapshot.getValue(Wholesaler.class);

                        holder.setData(w);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), WholesalerProductsOverview.class);
                                intent.putExtra("wid", w.getWid());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public WholesalerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_wholesaler_banner, parent, false);

                return new WholesalerListHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.startListening();
    }
}

