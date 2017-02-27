package com.yztc.fazhi.ui.request;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.yztc.fazhi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetImagesActivity extends AppCompatActivity {

    @BindView(R.id.request_grid_choicedImages)
    GridView requestGridChoicedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_images);
        ButterKnife.bind(this);


        ArrayList<String> imgs = getIntent().getStringArrayListExtra("imgs");

        ListImagesAdapter adapter=new ListImagesAdapter(this,imgs);
        requestGridChoicedImages.setAdapter(adapter);

    }
}
