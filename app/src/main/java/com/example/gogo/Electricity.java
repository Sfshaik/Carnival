package com.example.gogo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.service.credentials.Action;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.io.InputStreamReader;


public class Electricity extends Fragment   {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    private Paint paint;

    Rect balls;
    float pixels = 50.0f;

    Vec2 position;
    Button button;
    private Body body;
    FixtureDef fixtureDef2 = new FixtureDef();
    FixtureDef fixtureDef = new FixtureDef();
    float they;



    boolean drawing;
    boolean touched2, touched3;

    float mass1;
    Vec2 end;
    float mass2;
    float velocity1;
    private World world;

    float velocity2;
    Vec2 interection, interection3;
    Vec2 interection2;


    Body body3;

    TextView textView;
    boolean touched;

    EditText m1, v1, m2, v2;
    Body line;

    BodyDef bodyDef;
    BodyDef second;
    Body ball;
    boolean ending, ending3;
    boolean ending2;




    Switch aSwitch;

    FloatingActionButton floatingActionButton, restart;


    public Electricity() {
    }

    public static Electricity newInstance() {
        return new Electricity();
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
        View view = inflater.inflate(R.layout.fragment_electricity, container, false);
        surfaceView = view.findViewById(R.id.simingelectricity);
        ConstraintLayout linear = view.findViewById(R.id.linearLayout1);

        end = null;
        position = new Vec2();
        second = new BodyDef();
        bodyDef = new BodyDef();

        button = view.findViewById(R.id.button3);


        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                interection = new Vec2();
                interection.set(motionEvent.getX() / 50, motionEvent.getY() / 50);
                interection2 = new Vec2();
                interection2.set(motionEvent.getX() / 50, motionEvent.getY() / 50);
                interection3 = new Vec2();
                interection3.set(motionEvent.getX() / 50, motionEvent.getY() / 50);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(ball.getFixtureList().testPoint(interection))
                    {

                        ending = true;
                        touched = true;
                        Log.d("hanging", " " +ball.getFixtureList());

                    }

                    if(body.getFixtureList().testPoint(interection2))
                    {
                        ending2 = true;
                        touched2 = true;
                        Log.d("hanging", " " +ball.getFixtureList());

                    }
                    if(body3.getFixtureList().testPoint(interection3))
                    {
                        ending3 = true;
                        touched3 = true;
                        Log.d("hanging", " " +ball.getFixtureList());

                    }
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                {
                    Log.d("hanging", "false");
                    if(touched)
                    {
                        ball.setTransform(interection, 0);

                        //interection.set(motionEvent.getX()/50, motionEvent.getY()/50);
                        drawing = true;
                        //  they = ball.getPosition().y*pixels - interection.y*50;

                    }

                    if(touched2)
                    {
                        body.setTransform(interection2, 0);

                        //interection.set(motionEvent.getX()/50, motionEvent.getY()/50);
                        drawing = true;
                        //  they = ball.getPosition().y*pixels - interection.y*50;

                    }
                    if(touched3)
                    {
                        body3.setTransform(interection3, 0);

                        //interection.set(motionEvent.getX()/50, motionEvent.getY()/50);
                        drawing = true;
                        //  they = ball.getPosition().y*pixels - interection.y*50;

                    }
                    return true;

                }


                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    touched = false;
                    touched2 = false;
                    touched3 = false;


                    end  = new Vec2();
                    end = interection;

                       /* ImageView arrow1 = new ImageView(getActivity());
                        arrow1.setId(View.generateViewId());
                        arrow1.setImageResource(R.drawable.baseline_face_24);
                        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(150, 150);
                        arrow1.setLayoutParams(layoutParams);
                        linear.addView(arrow1);

                        ConstraintSet newConstraintSet = new ConstraintSet();
                        newConstraintSet.clone(linear);
                        newConstraintSet.connect(arrow1.getId(), ConstraintSet.LEFT, linear.getId(), ConstraintSet.LEFT);
                        newConstraintSet.connect(arrow1.getId(), ConstraintSet.RIGHT, linear.getId(), ConstraintSet.RIGHT);
                        newConstraintSet.connect(arrow1.getId(), ConstraintSet.TOP,  linear.getId(), ConstraintSet.TOP);
                        newConstraintSet.setMargin(arrow1.getId(), ConstraintSet.TOP, 200);
                        newConstraintSet.applyTo(linear);

                        */
                    }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vec2 impulse = new Vec2(0.0f, 100.0f);
                ball.applyLinearImpulse(impulse, ball.getWorldCenter());
                body3.applyLinearImpulse(impulse, body3.getWorldCenter());
                body.applyLinearImpulse(impulse, body.getWorldCenter());



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


        public PhysicsThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
            world = new World(new Vec2(0, 0f));

            position.set(10, 10);

            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(position);
            ball = world.createBody(bodyDef);
            CircleShape circle = new CircleShape();
            circle.setRadius(1f);
            fixtureDef.density=1f;
            ball.setLinearVelocity(new Vec2(0,0));
            fixtureDef.shape = circle;
            fixtureDef.friction = 0.3f;
            fixtureDef.isSensor = true;
            ball.createFixture(fixtureDef).setUserData("SENSOR");;

            BodyDef def = new BodyDef();
            FixtureDef def1 = new FixtureDef();

            def.type = BodyType.DYNAMIC;
            def.position.set(new Vec2(10, 35));
            body3 = world.createBody(def);
            CircleShape circle3 = new CircleShape();
            circle3.setRadius(1f);
            def1.density=1f;
            body3.setLinearVelocity(new Vec2(0,0));
            def1.shape = circle3;
            def1.friction = 0.3f;
            def1.isSensor = true;
            body3.createFixture(def1).setUserData("SENSOR");;





            second.type = BodyType.DYNAMIC;
            second.position.set(new Vec2(20, 10));
            body = world.createBody(second);
            CircleShape circle2 = new CircleShape();
            circle2.setRadius(1f);
            fixtureDef2.density=1f;
            body.setLinearVelocity(new Vec2(0,0));
            fixtureDef2.shape = circle2;
            fixtureDef2.friction = 0.3f;
            fixtureDef2.isSensor = true;



            body.createFixture(fixtureDef2).setUserData("SENSOR");;

        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        world.step(1 / 60f, 6, 10);
                        if (canvas != null) {
                            canvas.drawColor(Color.WHITE);

                            Paint paint1 = new Paint();
                            paint1.setColor(Color.CYAN);

                            if(ending&&ending2&&ending3) {
                                for (int j = 0; j < canvas.getWidth(); j += 40) {
                                    for (int i = 0; i < canvas.getHeight(); i += 40) {
                                        //canvas.drawLine(j + ball.getPosition().x, i + ball.getPosition().y, j + body.getPosition().x, i + body.getPosition().y, paint);
                                       // canvas.drawCircle(j + body.getPosition().x, i + body.getPosition().y, 3f, paint);
                                        float balldistance = (float) Math.sqrt((j-ball.getPosition().x)*(j-ball.getPosition().x) + (i-ball.getPosition().y)*(i-ball.getPosition().y));
                                        float bodydistance = (float) Math.sqrt((j-body3.getPosition().x)*(j-body3.getPosition().x) + (i-body3.getPosition().y)*(i-body3.getPosition().y));
                                        if(balldistance>= bodydistance)
                                        {
                                            canvas.drawLine(j + body3.getPosition().x, i + body3.getPosition().y, j + body.getPosition().x, i + body.getPosition().y, paint);
                                             canvas.drawCircle(j + body.getPosition().x, i + body.getPosition().y, 3f, paint);

                                        }
                                        else if(bodydistance>=balldistance)
                                        {
                                             canvas.drawCircle(j + body.getPosition().x, i + body.getPosition().y, 3f, paint);

                                            canvas.drawLine(j + ball.getPosition().x, i + ball.getPosition().y, j + body.getPosition().x, i + body.getPosition().y, paint);


                                        }

                                    }
                                }
                            }
                            float x = ball.getPosition().x * pixels;
                            float y = ball.getPosition().y * pixels;
                            float radius = 1f * pixels;
                            canvas.drawCircle(x, y, radius, paint);

                            float x3 = body3.getPosition().x * pixels;
                            float y3 = body3.getPosition().y * pixels;
                            canvas.drawCircle(x3, y3, radius, paint);


                            float x2 = body.getPosition().x * pixels;
                            float y2 = body.getPosition().y * pixels;
                            canvas.drawCircle(x2, y2, radius, paint1);


                            if(!ending && !ending2&& !ending3)
                                for (int j = 0; j < canvas.getWidth(); j += 40) {
                                    for (int i = 0; i < canvas.getHeight(); i += 40) {
                                        canvas.drawLine(j, i, j+30, i+30, paint);
                                    }
                                }




                            /*

                                canvas.drawLine(ball.getPosition().x*pixels, ball.getPosition().y*pixels, (ball.getPosition().x+2)*pixels, (ball.getPosition().y+3)*pixels, paint);
                                canvas.drawLine(ball.getPosition().x*pixels, ball.getPosition().y*pixels, (ball.getPosition().x+2)*pixels, (ball.getPosition().y)*pixels, paint);
                                canvas.drawLine(ball.getPosition().x*pixels, ball.getPosition().y*pixels, (ball.getPosition().x-2)*pixels, (ball.getPosition().y)*pixels, paint);
                                canvas.drawLine(ball.getPosition().x*pixels, ball.getPosition().y*pixels, (ball.getPosition().x-2)*pixels, (ball.getPosition().y-3)*pixels, paint);
                                                         */




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








