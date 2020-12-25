package com.wood.changeskin_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhy.changeskin.SkinManager;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {
    public boolean isChange=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.getInstance().register(this);

        setSelctTv((LinearLayout) findViewById(R.id.ll_3));
        setSelctTv((LinearLayout) findViewById(R.id.ll));




    }

    private void setSelctTv(final LinearLayout ll) {
        final int childCount = ll.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int finalI = i;
            ll.getChildAt(0).setSelected(true);

            ll.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i1 = 0; i1 < childCount; i1++) {
                        if (i1 == finalI) {
                            ll.getChildAt(i1).setSelected(true);
                        } else {
                            ll.getChildAt(i1).setSelected(false);
                        }
                    }
                }
            });
        }
    }

    public void  changeskin(View view){
        if (isChange) {
            isChange=false;
        SkinManager.getInstance().changeSkin("red");
        }else {
            isChange=true;
        SkinManager.getInstance().changeSkin("");

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}