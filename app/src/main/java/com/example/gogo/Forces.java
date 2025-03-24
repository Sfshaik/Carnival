package com.example.gogo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
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
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.io.InputStreamReader;
import java.util.ArrayList;


public class Forces extends Fragment {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    private Paint paint;
    Button button;
    private Body body;
    FixtureDef fixtureDef2 = new FixtureDef();
    float y;
    FixtureDef fixtureDef = new FixtureDef();

    EditText mass;
    EditText length;
    EditText force;
    float mass1;
    float mass2;
    ArrayList<Vec2> driections;
    float velocity1;
    private World world;

    float velocity2;
    boolean touched;
    Body body3;

    boolean drawing;
    TextView textView;
    Vec2 interection;

    EditText m1, v1, m2, v2;
    Body line;

    BodyDef bodyDef;
    BodyDef second;
    Body ball;



    Switch aSwitch;

    FloatingActionButton floatingActionButton, restart;


    public Forces() {
    }

    public static Forces newInstance() {
        return new Forces();
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
        View view = inflater.inflate(R.layout.fragment_forces, container, false);
        surfaceView = view.findViewById(R.id.simingforce);

        second = new BodyDef();
        bodyDef = new BodyDef();
        EditText one = view.findViewById(R.id.one);
        EditText two = view.findViewById(R.id.two);
        EditText three = view.findViewById(R.id.three);
        EditText four = view.findViewById(R.id.four);
        EditText five = view.findViewById(R.id.five);
        EditText six = view.findViewById(R.id.six);
        EditText seven = view.findViewById(R.id.seven);
        EditText eight = view.findViewById(R.id.eight);


        FloatingActionButton go = view.findViewById(R.id.returns);
        ConstraintLayout layout = view.findViewById(R.id.linearLayout1);
        button = view.findViewById(R.id.button4);

        y = 35*50;
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                interection = new Vec2();
                interection.set(motionEvent.getX() / 50, motionEvent.getY() / 50);
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(ball.getFixtureList().testPoint(interection))
                    {
                        touched = true;
                        Log.d("hanging", " " +ball.getFixtureList());

                    }
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                {
                    Log.d("hanging", "false");
                    if(touched)
                    {
                        drawing = true;

                    }

                    return true;

                }


                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    touched = false;
                    driections = new ArrayList<>();

                    driections.add(new Vec2(interection.x,interection.y));
                    Log.d("gaga", ""+driections.size());

                }


                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                float oner = Float.parseFloat(one.getText().toString());
                float twor = Float.parseFloat(two.getText().toString());
                float threer = Float.parseFloat(three.getText().toString());
                float fourr = Float.parseFloat(four.getText().toString());
                float fiver = Float.parseFloat(five.getText().toString());
                float sixer = Float.parseFloat(six.getText().toString());
                float sevenr = Float.parseFloat(seven.getText().toString());
                float eighter = Float.parseFloat(eight.getText().toString());


                Vec2 forceone = new Vec2(0, -oner).add(new Vec2(-twor, -twor)).add(new Vec2(-threer, 0)).add(new Vec2(-fourr, fourr)).add(new Vec2(0, fiver)).add(new Vec2(sixer, sixer)).add(new Vec2(sevenr, 0)).add(new Vec2(eighter, -eighter));

                ball.setLinearVelocity(forceone);

            }
        });


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ball.setTransform(new Vec2(15, 35), 0);

                ball.setLinearVelocity(new Vec2(0, 0));
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
            world = new World(new Vec2(0, 0f));


            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(15, 35);
            ball = world.createBody(bodyDef);
            CircleShape circle = new CircleShape();
            circle.setRadius(1f);
            fixtureDef.density=1f;
            fixtureDef.shape = circle;
            fixtureDef.friction = 0.3f;
            ball.createFixture(fixtureDef);

            BodyDef pivotDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;
            pivotDef.position.set(15, 28);
            point = world.createBody(pivotDef);


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


                            if(drawing)
                            {
                                canvas.drawLine(ball.getPosition().x* pixels,ball.getPosition().y*pixels, interection.x*pixels, interection.y*pixels, paint);
                            }

                            Log.d("Flag", ""+ball.getPosition().y);

                            float x = ball.getPosition().x * pixels;
                            float y = ball.getPosition().y * pixels;
                            float radius = 1f * pixels;
                            canvas.drawCircle(x, y, radius, paint);


                            Paint green = new Paint();
                            Paint grey = new Paint();
                            Paint cyan = new Paint();
                            Paint red = new Paint();
                            Paint yellow = new Paint();
                            Paint pink = new Paint();
                            Paint black = new Paint();
                            Paint blue = new Paint();

                            green.setColor(Color.GREEN);
                            grey.setColor(Color.DKGRAY);
                            cyan.setColor(Color.CYAN);
                            red.setColor(Color.RED);
                            yellow.setColor(Color.YELLOW);
                            pink.setColor(Color.MAGENTA);
                            black.setColor(Color.BLACK);
                            blue.setColor(Color.BLUE);


                            green.setStrokeWidth(5);
                            grey.setStrokeWidth(5);
                            cyan.setStrokeWidth(5);
                            red.setStrokeWidth(5);
                            yellow.setStrokeWidth(5);
                            pink.setStrokeWidth(5);
                            black.setStrokeWidth(5);
                            blue.setStrokeWidth(5);


                            canvas.drawLine(x, y, (ball.getPosition().x+7) * pixels, (ball.getPosition().y+7) * pixels, blue);
                            canvas.drawLine(x, y, (ball.getPosition().x-7) * pixels, (ball.getPosition().y+7) * pixels, red);
                            canvas.drawLine(x, y, (ball.getPosition().x-7) * pixels, (ball.getPosition().y-7) * pixels, black);
                            canvas.drawLine(x, y, (ball.getPosition().x+7) * pixels, (ball.getPosition().y-7) * pixels, pink);



                            canvas.drawLine(x, y, (ball.getPosition().x) * pixels, (ball.getPosition().y+7) * pixels, green);
                            canvas.drawLine(x, y, (ball.getPosition().x-7) * pixels, (ball.getPosition().y) * pixels, yellow);
                            canvas.drawLine(x, y, (ball.getPosition().x) * pixels, (ball.getPosition().y-7) * pixels, grey);
                            canvas.drawLine(x, y, (ball.getPosition().x+7) * pixels, (ball.getPosition().y) * pixels, cyan);

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








