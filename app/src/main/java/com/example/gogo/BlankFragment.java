package com.example.gogo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;


public class BlankFragment extends Fragment {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    private Paint paint;
    Button button;
    private Body body;

    Body wheel;
    Body roller;
    Body wheel3;
    Body roller2;
    FixtureDef fixtureDef2 = new FixtureDef();
    FixtureDef fixtureDef = new FixtureDef();


    float mass1;
    float mass2;
    float velocity1;
    private World world;

    float velocity2;
    private Body body3;

    TextView textView;

    EditText m1, v1, m2, v2;
    BodyDef bodyDef;
    BodyDef second;


    Switch aSwitch;

    FloatingActionButton floatingActionButton, restart;


    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        return new BlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        surfaceView = view.findViewById(R.id.simulation_surface_view);
        button = view.findViewById(R.id.button);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        second = new BodyDef();
        bodyDef = new BodyDef();
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    velocity1 = Float.parseFloat(v1.getText().toString());
                    velocity2 = Float.parseFloat(v2.getText().toString());


                body.applyLinearImpulse(new Vec2(velocity1, 0f), body.getWorldCenter());
               body3.applyLinearImpulse(new Vec2(-velocity2, 0f), body3.getWorldCenter());



                fixtureDef.density = Float.parseFloat(m1.getText().toString());
                fixtureDef2.density = Float.parseFloat(m2.getText().toString());


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


        private Body groundBody;
        private static final float PIXELS_PER_METER = 50.0f;

        public PhysicsThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
            world = new World(new Vec2(0, 10));
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(6, 29);

            body = world.createBody(bodyDef);

            CircleShape circle = new CircleShape();
            circle.m_radius = 1f;

            fixtureDef.shape = circle;
            fixtureDef.friction = 0.3f;

            body.createFixture(fixtureDef);




            second.type = BodyType.DYNAMIC;
            second.position.set(20, 29);

            body3 = world.createBody(second);

            CircleShape circles = new CircleShape();
            circles.m_radius = 1f;

            fixtureDef2.shape = circles;




            fixtureDef2.friction = 0.3f;
            body3.createFixture(fixtureDef2);




            //PolygonShape polygonShape = new PolygonShape();
           // polygonShape.set(vertices, vertices.length);


            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.position.set(10.0f,30.0f); // Position the ground 5 meters down in the world
            groundBody = world.createBody(groundBodyDef);

            EdgeShape groundShape = new EdgeShape();
            groundShape.set(new Vec2(-100.0f, 0.0f), new Vec2(100.0f, 0.0f)); // 20 meters wide ground

            groundBody.createFixture(groundShape, 0.0f);
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

                            float x = body.getPosition().x * PIXELS_PER_METER;
                            float y = body.getPosition().y * PIXELS_PER_METER;
                            float radius = 1f * PIXELS_PER_METER;
                            canvas.drawCircle(x, y, radius, paint);

                            float x22 = body3.getPosition().x * PIXELS_PER_METER;
                            float y22 = body3.getPosition().y * PIXELS_PER_METER;
                            canvas.drawCircle(x22, y22, radius, paint);

                            Paint groundPaint = new Paint();
                            groundPaint.setColor(Color.BLACK);
                            groundPaint.setStrokeWidth(5.0f);
                            Vec2 v1 = groundBody.getWorldPoint(new Vec2(-100.0f, 0.0f));
                            Vec2 v2 = groundBody.getWorldPoint(new Vec2(100.0f, 0.0f));

                            float x1 = v1.x * PIXELS_PER_METER;
                            float y1 = v1.y * PIXELS_PER_METER;
                            float x2 = v2.x * PIXELS_PER_METER;
                            float y2 = v2.y * PIXELS_PER_METER;

                            canvas.drawLine(x1, y1, x2, y2, groundPaint);
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








