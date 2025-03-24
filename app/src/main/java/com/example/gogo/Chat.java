package com.example.gogo;

import static androidx.core.content.ContextCompat.getDrawable;

import android.app.Dialog;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Chat extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText query;

    private ImageView send, logo;

    String apple = "";


    String file = "info.json";
    String data;
    FloatingActionButton showDialogue;

    private LinearLayout chatted;
    private ChatFutures chatModel;

    Dialog dialog;


    private String mParam1;
    private String mParam2;

    public Chat() {
        // Required empty public constructor
    }

    public static Chat newInstance(String param1, String param2) {
        Chat fragment = new Chat();
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

        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.message_dialogue);

        if(dialog.getWindow() !=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
        }

        send = dialog.findViewById(R.id.send);

        query = dialog.findViewById(R.id.queryEditText);

        showDialogue = view.findViewById(R.id.enterbutton);

        chatted = view.findViewById(R.id.chatBot);

        chatModel = getChatModel();
        showDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                String q = query.getText().toString();

                query.setText("");

                chatBody("You", q, getDrawable(getContext(), R.drawable.baseline_face_24));

                GeminiRes.getResponse(chatModel, q, new ResponseCallBack() {
                    @Override
                    public void onResponse(String Response) {
                        chatBody("AI", Response, getDrawable(getContext(),R.drawable.baseline_chat_bubble_24));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        chatBody("AI", "Please try again.", getDrawable(getContext(),R.drawable.baseline_chat_bubble_24));
                    }
                });

            }


        });

        return view;


    }

    private ChatFutures getChatModel()
    {
        GeminiRes model = new GeminiRes();
        GenerativeModelFutures modelFutures = model.getModel();
        return modelFutures.startChat();

    }

    private void chatBody(String you, String query, Drawable drawable) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.chat_message, null);
        View view2 = inflater.inflate(R.layout.fragment_chat, null);

        TextView name = view.findViewById(R.id.name);
        TextView message = view.findViewById(R.id.message);

        ImageView logo = view.findViewById(R.id.logo);

        name.setText(you);
        message.setText(query);

        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(requireContext().openFileOutput(file, Context.MODE_PRIVATE));
            // outputStreamWriter.write(data);
            //   outputStreamWriter.close();
            //FileOutputStream outputStreamWriter = openFileOutput(file, Context.MODE_PRIVATE);
            //FileOutputStream outputStreamWriter = openFileOutput(file, Context.MODE_PRIVATE);

           /* Writer out = new BufferedWriter(outputStreamWriter);
            out.write(data);
            out.close();

           */



           /*FileWriter output = new FileWriter(outputStreamWriter.getFD());
            output.write(data);
            output.close();

            */

            FileOutputStream fis = requireContext().openFileOutput(file,Context.MODE_PRIVATE);
            FileWriter fw = new FileWriter(fis.getFD());

            fw.write(query);
            fw.close();
            fis.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            File file1 = new File(requireContext().getFilesDir(), file);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;

            while ((st = br.readLine()) != null)
            {
                apple+=st;
            }

            br.close();
        }
        catch (Exception e)
        {

        }
        logo.setImageDrawable(drawable);
        ScrollView scrollView = view2.findViewById(R.id.scroll);

        chatted.addView(view);

        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}