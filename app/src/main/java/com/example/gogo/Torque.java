package com.example.gogo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.io.InputStreamReader;


public class Torque extends Fragment {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    private Paint paint;
    Button button;
    private Body body;
    FixtureDef fixtureDef2 = new FixtureDef();
    FixtureDef fixtureDef = new FixtureDef();


    float mass1;
    float mass2;
    PolygonShape rects;
    float velocity1;
    private World world;

    float velocity2;
    Body body3;

    TextView textView;

    EditText m1, v1, m2, v2;
    Body line;

    BodyDef bodyDef;
    BodyDef second;
    Body ball;



    Switch aSwitch;

    FloatingActionButton floatingActionButton, restart;


    public Torque() {
    }

    public static Torque newInstance() {
        return new Torque();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paint = new Paint();
        paint.setColor(Color.BLUE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_torque, container, false);
        surfaceView = view.findViewById(R.id.torquesim);

        second = new BodyDef();
        bodyDef = new BodyDef();
        button = view.findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                world.setGravity(new Vec2(0f, 10f));
                Vec2 impulse = new Vec2(0.0f, 10.0f);
                ball.applyLinearImpulse(impulse, ball.getWorldCenter());
            }
        });
        /*
        m1 = view.findViewById(R.id.mass1);
        v1 = view.findViewById(R.id.v1);
        v2 = view.findViewById(R.id.v2);
        m2 = view.findViewById(R.id.m2);
        restart = view.findViewById(R.id.restart);
        aSwitch= view.findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    fixtureDef.restitution = 0.0f;
                    fixtureDef2.restitution = 0.0f;
                    aSwitch.setText("Inelastic");
                }
                else if(!b)
                {
                    fixtureDef.restitution = 1.0f;
                    fixtureDef2.restitution = 1.0f;
                    aSwitch.setText("Elastic");
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Inside", "clicked");

                body.setLinearVelocity(new Vec2(0f, 0f));
                body3.setLinearVelocity(new Vec2(0f, 0f));


                body.setTransform(new Vec2(6, 29), 0);
                body3.setTransform(new Vec2(20, 29), 0);




            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(m1.getVisibility() == View.VISIBLE)
                {
                    Log.d("Inside", "gone");
                    m1.setVisibility(View.GONE);
                    aSwitch.setVisibility(View.GONE);
                    m2.setVisibility(View.GONE);
                    v1.setVisibility(View.GONE);
                    v2.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);

                }


                else if(m1.getVisibility() == View.GONE)
                {
                    Log.d("Inside", "visible");

                    m1.setVisibility(View.VISIBLE);
                    aSwitch.setVisibility(View.VISIBLE);
                    m2.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);

                    v2.setVisibility(View.VISIBLE);

                }
            }
        });

         */
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        physicsThread = new PhysicsThread(surfaceView.getHolder());
        physicsThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        physicsThread.stopSimulation();
    }

    private class PhysicsThread extends Thread {
        private final SurfaceHolder surfaceHolder;
        private boolean running = true;


        private Body point;

        float pixels = 50.0f;

        public PhysicsThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;

            world = new World(new Vec2(0, 10f));


            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(25, 50);
            ball = world.createBody(bodyDef);

             rects = new PolygonShape();
            rects.setAsBox(8f, 1f);
            fixtureDef.density=1f;
            fixtureDef.shape = rects;
            fixtureDef.friction = 0.3f;
            ball.createFixture(fixtureDef);


            BodyDef pivotDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;
            pivotDef.position.set(25, 50);
            point = world.createBody(pivotDef);

            RevoluteJointDef jointDef = new RevoluteJointDef();
            jointDef.initialize(point, ball, ball.getPosition());
            world.createJoint(jointDef);
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        world.step(1 / 60f, 6, 2);
                        if (canvas != null) {
                            canvas.drawColor(Color.WHITE);

                            paint.setStrokeWidth(5f);

                            float one = (rects.getVertex(0).x+10)* pixels;
                            float two = (rects.getVertex(0).y+10)* pixels;
                            float three = (rects.getVertex(1).x+10)* pixels;
                            float four = (rects.getVertex(2).y+10)* pixels;
                            canvas.drawRect(one, two, three, four, paint);

                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void stopSimulation() {
            running = false;
        }
    }


}








