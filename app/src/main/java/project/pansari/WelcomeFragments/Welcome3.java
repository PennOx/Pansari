package project.pansari.WelcomeFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import project.pansari.LoginActivity;
import project.pansari.R;

public class Welcome3 extends Fragment {

    Context context;

    public Welcome3() {
        // Required empty public constructor
    }

    public Welcome3(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome3, container, false);

        view.findViewById(R.id.welcome_shopkeeper_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity('s');
            }
        });

        view.findViewById(R.id.welcome_wholesaler_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity('w');
            }
        });

        return view;
    }


    private void startLoginActivity(char type) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}