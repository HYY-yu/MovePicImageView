package com.example.yufeng.movepicimageview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feng.moveimageview.MovePicImageView;

public class MainActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private Button btn_login;
    private Button btn_register;

    private MovePicImageView movePicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movePicImageView = (MovePicImageView) findViewById(R.id.movePicIV);
        movePicImageView.setBG(R.drawable.flowbloom);
        movePicImageView.invalidate();

        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NetworkHandler

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_login.getVisibility() != View.INVISIBLE) {
                    ObjectAnimator o1 = ObjectAnimator.ofFloat(btn_login,"alpha",0.3f,0f)
                            .setDuration
                                    (600);
                    o1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            btn_login.setVisibility(View.INVISIBLE);
                            ObjectAnimator o2 = ObjectAnimator.ofFloat(btn_register,
                                    "translationY",0,-100)
                                    .setDuration(600);
                            o2.start();
                            btn_register.setText("确认");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    o1.start();

                } else {
                    //按下确认

                }
            }
        });
    }
}
