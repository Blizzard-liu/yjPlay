package chuangyuan.ycj.yjplay.defaults;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;

import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.listener.VideoWindowListener;
import chuangyuan.ycj.videolibrary.video.GestureVideoPlayer;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;
import chuangyuan.ycj.yjplay.R;
import chuangyuan.ycj.yjplay.data.DataSource;

public class GuangGaoPlayerdActivity extends Activity {

    private GestureVideoPlayer exoPlayerManager;
    private VideoPlayerView videoPlayerView;
    private static final String TAG = "OfficeDetailedActivity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coutom2);
        videoPlayerView = (VideoPlayerView) findViewById(R.id.exo_play_context_id);
        exoPlayerManager = new GestureVideoPlayer(this, videoPlayerView, new DataSource(this.getApplication()));
        exoPlayerManager.setTitle("视频标题");
        exoPlayerManager.setExoPlayWatermarkImg(R.mipmap.watermark_big);
        if (Build.VERSION.SDK_INT < 21) {//低版本不支持高分辨视频
            exoPlayerManager.setPlayUri(0, "http://mp4.vjshi.com/2013-07-25/2013072519392517096.mp4", "http://mp4.vjshi.com/2013-11-11/1384169050648_274.mp4");
        } else {
            exoPlayerManager.setPlayUri(0, "http://mp4.vjshi.com/2016-12-22/3ccab5a78036fa933c8585f4a1e57a44.mp4", "http://mp4.vjshi.com/2016-04-05/add12db77c7c5cd6dfef4c1955b36a80.mp4");
        }
        ///默认实现  播放广告视频时手势操作禁用和开启操作
        //exoPlayerManager.setPlayerGestureOnTouch(true);
        //如果视频需要自己实现该回调 视频切换回调处理，进行布局处理，控制布局显示
        exoPlayerManager.setOnWindowListener(new VideoWindowListener() {
            @Override
            public void onCurrentIndex(int currentIndex, int windowCount) {
                if (currentIndex == 0) {
                    Log.d(TAG,"setOnWindowListener:"+currentIndex);
                    //屏蔽控制布局
                    exoPlayerManager.hideControllerView(true);
                    //true如果屏蔽控制布局 但是需要显示全屏按钮。手动显示，播放正常视频自动还原。
                } else {
                    //恢复控制布局
                    exoPlayerManager.showControllerView();
                }
            }
        });
        exoPlayerManager.setVideoInfoListener(new VideoInfoListener() {
            @Override
            public void onPlayStart() {

            }

            @Override
            public void onLoadingChanged() {

            }

            @Override
            public void onPlayerError( ExoPlaybackException e) {

            }

            @Override
            public void onPlayEnd() {
                Toast.makeText(getApplication(),"asd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void isPlaying(boolean playWhenReady) {

            }
        });
        Glide.with(this)
                .load("http://i3.letvimg.com/lc08_yunzhuanma/201707/29/20/49/3280a525bef381311b374579f360e80a_v2_MTMxODYyNjMw/thumb/2_960_540.jpg")
                .fitCenter()
                .placeholder(R.mipmap.test)
                .into(videoPlayerView.getPreviewImage());
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exoPlayerManager.next();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        exoPlayerManager.onPause();
    }


    @Override
    protected void onDestroy() {
        exoPlayerManager.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (exoPlayerManager.onBackPressed()) {
            ActivityCompat.finishAfterTransition(this);

        }
    }

}
