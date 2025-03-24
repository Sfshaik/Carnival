package com.example.gogo;

import static androidx.core.content.ContextCompat.getDrawable;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class Home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText query;

    private ImageView send, logo, appicon;
    FloatingActionButton showDialogue;

    TextView fun, title;
    private ProgressBar progressBar;

    private LinearLayout chatted;
    private ChatFutures chatModel;

    Dialog dialog;


    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        ImageView imageView = view.findViewById(R.id.imageView);
        fun = view.findViewById(R.id.fun);
        title = view.findViewById(R.id.title);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rand = (int)(Math.random()*8)+1;

                switch (rand)
                {
                    case 1:
                    {
                        imageView.setImageResource(R.drawable.cart);
                        break;
                    }
                    case 2:
                    {
                        imageView.setImageResource(R.drawable.mask);

                        break;
                    }
                    case 3:
                    {
                        imageView.setImageResource(R.drawable.wheel);

                        break;
                    }
                    case 4:
                    {
                        imageView.setImageResource(R.drawable.sign);

                        break;
                    }
                    case 5:
                    {
                        imageView.setImageResource(R.drawable.horse);

                        break;
                    }
                    case 6:
                    {
                        imageView.setImageResource(R.drawable.clown);

                        break;
                    }
                    case 7:
                    {
                        imageView.setImageResource(R.drawable.ticket);

                        break;
                    }
                    case 8:
                    {
                        imageView.setImageResource(R.drawable.carnival);

                        break;
                    }
                }
            }
        });



        return view;


    }
}