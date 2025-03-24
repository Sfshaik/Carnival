package com.example.gogo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
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
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.io.InputStreamReader;


public class Spring extends Fragment   {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    private Paint paint;

    Rect balls;
    float pixels = 50.0f;DistanceJointDef distanceJoint;
    Body point;RevoluteJointDef jointDef;


    int turn;
    Vec2 position;
    DistanceJoint joint;

    int seconds = 0;
    Button button;
    private Body body;
    FixtureDef fixtureDef2 = new FixtureDef();
    FixtureDef fixtureDef = new FixtureDef();
    float they;



    boolean drawing;

    float mass1;
    Vec2 end;
    float mass2;
    float velocity1;


    private World world;

    float velocity2;
    Vec2 interection;

    Body body3;

    TextView textView;
    boolean touched;

    EditText m1, v1, m2, v2;
    Body line;

    BodyDef bodyDef;
    BodyDef second;
    Body ball;



    Switch aSwitch;

    FloatingActionButton floatingActionButton, restart;


    public Spring() {
    }

    public static Spring newInstance() {
        return new Spring();
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
        View view = inflater.inflate(R.layout.fragment_spring, container, false);
        surfaceView = view.findViewById(R.id.simingspring);
        ConstraintLayout linear = view.findViewById(R.id.linearLayout1);

        end = null;
        position = new Vec2();
        second = new BodyDef();
        bodyDef = new BodyDef();

        TextView times = view.findViewById(R.id.time);

        EditText mass;


        Handler handler
                = new Handler();


        handler.post(new Runnable() {
            @Override

            public void run()
            {
                times.setText("" + seconds);
                seconds++;

                handler.postDelayed(this, 1000);
            }
        });
        EditText damping;
        EditText hz;

        mass = view.findViewById(R.id.mass);
        hz = view.findViewById(R.id.frequency);
        damping = view.findViewById(R.id.damping);

        button = view.findViewById(R.id.button2);


        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                interection = new Vec2();
                //if(end == null) {
                interection.set(motionEvent.getX() / 50, motionEvent.getY() / 50);
                //        }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (ball.getFixtureList().testPoint(interection)) {
                        turn = 1;

                        touched = true;
                        Log.d("hanging", " " + ball.getFixtureList());

                    }
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d("hanging", "false");
                    if (touched) {
                        ball.setTransform(interection, 0);

                        //interection.set(motionEvent.getX()/50, motionEvent.getY()/50);
                        drawing = true;
                        //  they = ball.getPosition().y*pixels - interection.y*50;

                    }
                    return true;

                }


                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    float d = (float) Float.parseFloat(damping.getText().toString());
                    float m = (float) Float.parseFloat(mass.getText().toString());
                    float f = (float) Float.parseFloat(hz.getText().toString());

                    touched = false;
                    end = interection;
                    distanceJoint = new DistanceJointDef();
                    distanceJoint.initialize(point, ball, point.getPosition(), ball.getPosition());
                    distanceJoint.localAnchorA.set(point.getPosition());
                    distanceJoint.localAnchorB.set(ball.getPosition());
                    distanceJoint.frequencyHz = f;
                    fixtureDef.density=m;
                    distanceJoint.dampingRatio = d;

                    if(joint !=null)
                    {
                        world.destroyJoint(joint);
                    }

                    joint = (DistanceJoint) world.createJoint(distanceJoint);


                    // they = ball.getPosition().y*pixels - interection.y*50;

                }

                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Vec2 impulse = new Vec2(0.0f, 100.0f);
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




        public PhysicsThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
            world = new World(new Vec2(0, 10f));

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
            balls = new Rect();







            BodyDef pivotDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;
            pivotDef.position.set(10, 3);
            point = world.createBody(pivotDef);

            distanceJoint = new DistanceJointDef();
            distanceJoint.initialize(point, ball, point.getPosition(), ball.getPosition());
            joint = (DistanceJoint) world.createJoint(distanceJoint);

            //jointDef = new RevoluteJointDef();
           // jointDef.initialize(point, ball, point.getPosition());
           // joint = (RevoluteJoint) world.createJoint(jointDef);


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






                            ball.setLinearVelocity(new Vec2(0, ball.getLinearVelocity().y));

                            float x = ball.getPosition().x * pixels;
                            float y = ball.getPosition().y * pixels;
                            float radius = 1f * pixels;
                            canvas.drawCircle(x, y, radius, paint);

                            Paint groundPaint = new Paint();
                            groundPaint.setColor(Color.BLACK);
                            groundPaint.setStrokeWidth(5.0f);
                            Paint pivotPaint = new Paint();
                            pivotPaint.setColor(Color.BLACK);
                            canvas.drawCircle(10 * pixels, 3 * pixels, 10, pivotPaint);

                            Paint linePaint = new Paint();
                            linePaint.setColor(Color.BLACK);
                            linePaint.setStrokeWidth(5.0f);
                            canvas.drawLine(
                                    10 * pixels, 3 * pixels, x, y, linePaint);
                            Paint paints = new Paint();
                            paints.setColor(Color.GREEN);
                            paints.setStrokeWidth(5.0f);
                            canvas.drawRect(balls, paints);




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








