package com.android.DROID_MJ.flashlight;

import android.support.v7.app.AppCompatActivity;
import android.hardware.Camera;
import android.support.v4.app.NotificationCompat;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

public class a extends AppCompatActivity implements OnClickListener {
    public static a a;
    public ImageButton b;
    public CameraManager c;
    public Camera d;
    public boolean e = false;
    public int i = 0;
    public float j = -1.0f;
    public float k = -1.0f;
    public LayoutParams l;
    public Parameters m;

    protected void onCreate(Bundle a) {
        super.onCreate(a);
        setContentView(R.layout.a);
        this.b = (ImageButton) findViewById(R.id.a);
        this.b.setOnClickListener(this);
        this.a = this;
    }

    public static a getInstance() {
        return a;
    }

    public void a1() {
        if (VERSION.SDK_INT >= 23) {
            if (this.c == null) {
                this.c = (CameraManager) getSystemService("camera");
            } try {
                this.c.setTorchMode(this.c.getCameraIdList()[0], true);
                return;
            } catch (Exception a) {
                c(a);
                return;
            }
        } try {
            if (getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
                this.d = Camera.open();
                this.m = this.d.getParameters();
                this.m.setFlashMode("torch");
                this.d.setParameters(this.m);
                this.d.startPreview();
            }
        } catch (Exception e2) {
            c(e2);
        }
    }

    public void b1() {
        if (VERSION.SDK_INT >= 23) {
            try {
                this.c.setTorchMode(this.c.getCameraIdList()[0], false);
                return;
            } catch (Exception a) {
                c(a);
                return;
            }
        } try {
            if (getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
                this.d.stopPreview();
                this.d.release();
                this.d = null;
            }
        } catch (Exception b) {
            c(b);
        }
    }

    public void c(Exception a) {
        Toast.makeText(this, "Error: " + a.getMessage(), 0).show();
    }

    public void onClick(View a) {
        if (a.getId() == R.id.a) {
            if (this.e) {
                b1();
                this.b.setImageResource(R.drawable.a);
            } else {
                a1();
                this.b.setImageResource(R.drawable.b);
            }
            this.e = !this.e;
        }
    }

    public boolean onTouchEvent(MotionEvent a) {
        float b = a.getX();
        float c = a.getY();
        switch (a.getAction()) {
            case 0:
                a.this.j = b;
                a.this.k = c;
                a.this.i = d();
                break;
            case 1:
                a.this.j = -1.0f;
                a.this.k = -1.0f;
                break;
            case 2:
                if (a.this.j < 0.0f || a.this.k < 0.0f) {
                    return false;
                }
                if (Math.abs(c - a.this.k) <= 50.0f || Math.abs(c - a.this.k) >= 2.0f * Math.abs(b - a.this.j)) {
                    e((int) ((a.this.k - c) / 2.0f));
                    break;
                }
                a.this.j = -1.0f;
                a.this.k = -1.0f;
                return false;
                
        }
        return false;
    }

    public int d() {
        return (int) (getWindow().getAttributes().screenBrightness * 255.0f);
    }

    public void e(int a) {
        int b = this.i + a;
        if (b < 0) {
            b = 0;
        }
        if (b > 255) {
            b = 255;
        }
        this.l = getWindow().getAttributes();
        this.l.screenBrightness = ((float) b) * 0.003921569f;
        getWindow().setAttributes(this.l);
    }
}
