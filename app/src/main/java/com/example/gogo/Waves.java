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

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;


public class Waves extends Fragment {

    private SurfaceView surfaceView;
    private PhysicsThread physicsThread;
    Body waveBody;
    private Paint paint;
    Button button;
    private Body body;
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


    public Waves() {
    }

    public static Waves newInstance() {
        return new Waves();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paint = new Paint();
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLUE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waves, container, false);
        surfaceView = view.findViewById(R.id.wavesim);

        second = new BodyDef();
        bodyDef = new BodyDef();

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

        Vec2[] vertices;

        private Body groundBody;
        private static final float PIXELS_PER_METER = 50.0f;

        public PhysicsThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
            world = new World(new Vec2(0, 10));

            /*
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(6, 29);

            body = world.createBody(bodyDef);

            CircleShape circle = new CircleShape();
            circle.m_radius = 1f;
            fixtureDef.shape = circle;
            fixtureDef.friction = 0.3f;
            body.createFixture(fixtureDef);
             */



            vertices = new Vec2[60];
            for (int i = 0; i < 60; i++) {
                float x = i * 2f;
                float y = (float) Math.sin(x) * 5.0f;
                vertices[i] = new Vec2(x, y);
            }

            ChainShape waveShape = new ChainShape();
            waveShape.createChain(vertices, vertices.length);
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;
            bodyDef.position.set(0, 0);
             waveBody = world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = waveShape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.3f;
            fixtureDef.restitution = 0.5f;
            waveBody.createFixture(fixtureDef);


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








