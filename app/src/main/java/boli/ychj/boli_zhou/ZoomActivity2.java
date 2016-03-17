package boli.ychj.boli_zhou;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import boli.ychj.bean.BoliPic2;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomActivity2 extends AppCompatActivity {
    private BoliPic2 mBoliPic;
    private Bitmap mBitmap;
    private PhotoViewAttacher mAttacher;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("图片细节");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ivImage = (ImageView) findViewById(R.id.iv_photo);

//        mBitmap = getIntent().getParcelableExtra("bolibitmap");
//
//        if(mBitmap == null){
//            Log.i("ychj2","mBitmap == null");
//        }

   //     ivImage.setImageBitmap(mBitmap);

       mBoliPic = (BoliPic2)getIntent().getSerializableExtra("boli");


     //   mBoliPic.getPic().loadImage(ivImage.getContext(),ivImage);

        Glide.with(ivImage.getContext())
                .load(mBoliPic.getPic().getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ivImage);

    //    mAttacher = new PhotoViewAttacher(ivImage);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


}
