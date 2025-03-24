package com.example.gogo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lessons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lessons extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Lessons() {
    }


    public static Lessons newInstance(String param1, String param2) {
        Lessons fragment = new Lessons();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_lessons, container, false);

        TextView title = view.findViewById(R.id.undertitle);
        TextView text = view.findViewById(R.id.undertext);


        RecyclerView recyclerView = view.findViewById(R.id.recyled);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://images.unsplash.com/photo-1633493702341-4d04841df53b?q=80&w=1180&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
       arrayList.add("https://images.unsplash.com/photo-1529310399831-ed472b81d589?q=80&w=2756&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1540928910700-11bccf592598?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1580551730007-11f498ebb39d?q=80&w=3002&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        arrayList.add("https://images.unsplash.com/photo-1710429512400-6e7b9285931d?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        arrayList.add("https://plus.unsplash.com/premium_photo-1664304463486-26246ff4cb8c?q=80&w=2968&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");


        arrayList.add("https://plus.unsplash.com/premium_photo-1670535227093-eb5cfe861a0c?q=80&w=3132&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");


        arrayList.add("https://images.unsplash.com/photo-1562447208-3d5f81e66179?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        ImageAdapter adapter = new ImageAdapter(getActivity(), arrayList);

        adapter.setOnItemClickListener(new ImageAdapter.onItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {

                if(url.equals("https://images.unsplash.com/photo-1633493702341-4d04841df53b?q=80&w=1180&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                   title.setText("Momentum");
                    text.setText("Momentum is a word that we hear used colloquially in everyday life. We are often told that sports teams and political candidates have 'a lot of momentum'. In this context, the speaker usually means to imply that the team or candidate has had a lot of recent success and that it would be difficult for an opponent to change their trajectory. This is also the essence of the meaning in physics, though in physics we need to be much more precise.\n" +
                            "Momentum is a measurement of mass in motion: how much mass is in how much motion.");
                }

                if(url.equals("https://images.unsplash.com/photo-1529310399831-ed472b81d589?q=80&w=2756&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Electricity");
                    text.setText("Electricity is defined as the presence of charge, which is better thought of as the flow of particles with charge (e.g. electrons flowing through a wire). The energy of these particles in motion can be converted to other forms of energy, such as creating light in a lamp, playing sound from a stereo, or lifting people in an elevator. To understand electricity, the atom is revisited. With sufficient energy, a force causes an electron to leave an atom.");
                }

                if(url.equals("https://images.unsplash.com/photo-1540928910700-11bccf592598?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Pendulum");
                    text.setText("A simple pendulum consists of a relatively massive object hung by a string from a fixed support. It typically hangs vertically in its equilibrium position. The massive object is affectionately referred to as the pendulum bob. When the bob is displaced from equilibrium and then released, it begins its back and forth vibration about its fixed equilibrium position. The motion is regular and repeating, an example of periodic motion. Pendulum motion was introduced earlier in this lesson as we made an attempt to understand the nature of vibrating objects. Pendulum motion was discussed again as we looked at the mathematical properties of objects that are in periodic motion. Here we will investigate pendulum motion in even greater detail as we focus upon how a variety of quantities change over the course of time. Such quantities will include forces, position, velocity and energy - both kinetic and potential energy.");
                }

                if(url.equals("https://images.unsplash.com/photo-1580551730007-11f498ebb39d?q=80&w=3002&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Force");
                    text.setText("A force is a push or a pull that acts on an object due to the interaction with another object. Force is measured in newtons (N). There are two main categories of forces -contact forces and non-contact forces.  Did you know?  You cannot see a force but you can see its effect. For example, when you apply a force to push a door open.");
                }


                if(url.equals("https://images.unsplash.com/photo-1710429512400-6e7b9285931d?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Spring");
                    text.setText("A spring is an object that can be deformed by a force and then return to its original shape after the force is removed." +
                            "Springs come in a huge variety of different forms, but the simple metal coil spring is probably the most familiar. Springs are an essential part of almost all moderately complex mechanical devices; from ball-point pens to racing car engines." +
                            "There is nothing particularly magical about the shape of a coil spring that makes it behave like a spring. The 'springiness', or more correctly, the elasticity is a fundamental property of the wire that the spring is made from. A long straight metal wire also has the ability to ‘spring back’ following a stretching or twisting action. Winding the wire into a spring just allows us to exploit the properties of a long piece of wire in a small space. This is much more convenient for building mechanical devices. ");
                }

                if(url.equals("https://plus.unsplash.com/premium_photo-1664304463486-26246ff4cb8c?q=80&w=2968&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Waves");
                    text.setText("A wave can be described as a disturbance that travels through a medium from one location to another location. Consider a slinky wave as an example of a wave. When the slinky is stretched from end to end and is held at rest, it assumes a natural position known as the equilibrium or rest position. The coils of the slinky naturally assume this position, spaced equally far apart. To introduce a wave into the slinky, the first particle is displaced or moved from its equilibrium or rest position. The particle might be moved upwards or downwards, forwards or backwards; but once moved, it is returned to its original equilibrium or rest position. The act of moving the first coil of the slinky in a given direction and then returning it to its equilibrium position creates a disturbance in the slinky. We can then observe this disturbance moving through the slinky from one end to the other. If the first coil of the slinky is given a single back-and-forth vibration, then we call the observed motion of the disturbance through the slinky a slinky pulse. A pulse is a single disturbance moving through a medium from one location to another location. However, if the first coil of the slinky is continuously and periodically vibrated in a back-and-forth manner, we would observe a repeating disturbance moving within the slinky that endures over some prolonged period of time. The repeating and periodic disturbance that moves through a medium from one location to another is referred to as a wave.");
                }

                if(url.equals("https://plus.unsplash.com/premium_photo-1670535227093-eb5cfe861a0c?q=80&w=3132&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Torque");
                    text.setText("Torque is a measure of the force that can cause an object to rotate about an axis. Just as force is what causes an object to accelerate in linear kinematics, torque is what causes an object to acquire angular acceleration. Torque is a vector quantity. The direction of the torque vector depends on the direction of the force on the axis. Anyone who has ever opened a door has an intuitive understanding of torque. When a person opens a door, they push on the side of the door farthest from the hinges. Pushing on the side closest to the hinges requires considerably more force. Although the work done is the same in both cases (the larger force would be applied over a smaller distance) people generally prefer to apply less force, hence the usual location of the door handle.");
                }

                if(url.equals("https://images.unsplash.com/photo-1562447208-3d5f81e66179?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                {
                    title.setText("Circuits");
                    text.setText("A circuit is an unbroken loop of conductive material that allows charge carriers to flow through continuously without beginning or end.  If a circuit is “broken,” that means its conductive elements no longer form a complete path, and continuous charge flow cannot occur in it.  The location of a break in a circuit is irrelevant to its inability to sustain continuous charge flow. Any break, anywhere in a circuit prevents the flow of charge carriers throughout the circuit.");
                }

               // startActivity(new Intent(getContext(), ImageActivity.class).putExtra("image", url), ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,"image").toBundle());
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new CarouselLayoutManager());


        return view;

    }
}