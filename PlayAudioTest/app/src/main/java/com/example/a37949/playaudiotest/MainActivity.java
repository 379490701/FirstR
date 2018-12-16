package com.example.a37949.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //实例化媒体播放器
    private MediaPlayer mediaPlayer = new MediaPlayer();

    //进度条
    private SeekBar music_length;
    //进度条文本
    private TextView music_length_text;
    //歌曲名
    private TextView music_title_text;
    //歌曲列表控件
    private ListView mMusicList;
    //适配器
    private SimpleAdapter mAdapter;
    //歌曲列表
    List<Mp3Info> mp3Infos = null;
    //用于进度条的更新
    Handler handler = new Handler();

    private int sum, mPosition, count = 1, flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = (Button) findViewById(R.id.play);
        Button next = (Button) findViewById(R.id.next);
        Button stop = (Button) findViewById(R.id.stop);
        Button last = (Button) findViewById(R.id.last);
        Button loop = (Button) findViewById(R.id.loop);
        music_length = (SeekBar) findViewById(R.id.music_length);
        music_length_text = (TextView) findViewById(R.id.music_length_text);
        music_title_text = (TextView) findViewById(R.id.music_title_text);
        mMusicList = (ListView) findViewById(R.id.music_list);

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        stop.setOnClickListener(this);
        last.setOnClickListener(this);
        loop.setOnClickListener(this);

        //运行时权限处理——动态申请WRITE_EXTERNAL_STORAGE权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //为ListView添加数据，获取歌曲对象集合
            mp3Infos = MediaUtil.getMp3Infos(getApplicationContext());
            //显示歌曲列表
            setListAdapter(MediaUtil.getMusicMaps(mp3Infos));
            initMediaPlayer();
        }

        //点击列表获得item
        mMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获得当前位置
                mPosition = position;
                play(mPosition);
            }
        });

        //进度条最大值
        music_length.setMax(mediaPlayer.getDuration());
        sum = mediaPlayer.getDuration();
        //设置进度条具体参数
        music_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //数值改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //显示文字
                int time = progress / 1000;//总秒数
                int sum_time = sum / 1000;
                int minute = time % 3600 / 60;//分钟数
                int sum_minute = sum_time % 3600 / 60;
                int second = time % 3600 % 60;//秒数
                int sum_second = sum_time % 3600 % 60;
                music_length_text.setText((minute >= 10 ? (minute + "") : ("0" + minute)) + ":" + (second >= 10 ? (second + "") : ("0" + second)) + "/" + (sum_minute >= 10 ? (sum_minute + "") : ("0" + sum_minute)) + ":" + (sum_second >= 10 ? (sum_second + "") : ("0" + sum_second)));
            }

            //开始拖动进度条
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //停止拖动进度条
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(music_length.getProgress());
            }
        });

        //音乐播放结束的监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //先更新位置后播放
                mPosition = setOrder(count);
                play(mPosition);
            }
        });

    }//onCreate

    //填充列表
    public void setListAdapter(List<HashMap<String, String>> mp3list) {
        mAdapter = new SimpleAdapter(this, mp3list, R.layout.item,
                new String[]{"number", "title", "Artist"},
                new int[]{R.id.number, R.id.music_title, R.id.music_artist});
        mMusicList.setAdapter(mAdapter);

    }

    //同步进度条时间
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            //同步歌曲进度
            music_length.setProgress(mediaPlayer.getCurrentPosition());
            //更新频率
            handler.postDelayed(update, 500);
        }
    };

    //初始化MediaPlayer
    private void initMediaPlayer() {
        try {
            String path = mp3Infos.get(0).getUrl();
            //播放之前要先把音频文件重置
            mediaPlayer.reset();
            //调用方法传进去要播放的音频路径
            mediaPlayer.setDataSource(path);
            music_title_text.setText(mp3Infos.get(0).getTitle() + "-" + mp3Infos.get(0).getArtist());
            //让MediaPlayer进入到准备状态
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //权限请求
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //按钮点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    //开始播放
                    mediaPlayer.start();
                    //进度条启动
                    handler.post(update);
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();//暂停播放
                }
                break;
            case R.id.next:
                //先更新位置后播放
                mPosition = setOrder(count);
                if (mPosition >= mp3Infos.size()) {
                    break;
                }
                play(mPosition);
                break;
            case R.id.last:
                if (mPosition <= 0) {
                    break;
                }
                play(mPosition - 1);
                mPosition--;
                break;
            case R.id.stop:
                //停止播放
                mediaPlayer.reset();
                try {
                    flag = 1;
                    //调用方法传进去要播放的音频路径
                    String path = mp3Infos.get(mPosition).getUrl();
                    mediaPlayer.setDataSource(path);
                    music_title_text.setText(mp3Infos.get(mPosition).getTitle() + "-" + mp3Infos.get(mPosition).getArtist());
                    //让MediaPlayer进入到准备状态
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.loop:
                count++;
                if (count >= 3) {
                    count = 0;
                }
                if (count == 0) {
                    Toast.makeText(MainActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                } else if (count == 1) {
                    Toast.makeText(MainActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
                } else if (count == 2) {
                    Toast.makeText(MainActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public int setOrder(int count) {
        if (count == 0) {
            //单曲循环
            return mPosition;
        } else if (count == 1) {
            //顺序播放
            return mPosition + 1;
        } else if (count == 2) {
            //随机播放
            int length = mp3Infos.size();
            return mPosition = (int) (Math.random() * length + 1);
        }
        return mPosition;
    }

    //获得播放状态并且播放
    public void play(int mPosition) {
        //设置进度条最大值
        music_length.setMax((int) mp3Infos.get(mPosition).getDuration());
        sum = (int) mp3Infos.get(mPosition).getDuration();
        music_title_text.setText(mp3Infos.get(mPosition).getTitle() + "-" + mp3Infos.get(mPosition).getArtist());
        //进度条启动
        handler.post(update);
        playing(mp3Infos.get(mPosition).getUrl());
    }

    //播放音频
    public void playing(String path) {
        try {
            //播放之前要先把音频文件重置
            mediaPlayer.reset();
            //调用方法传进去要播放的音频路径
            mediaPlayer.setDataSource(path);
            //异步准备音频资源
            mediaPlayer.prepareAsync();
            //调用mediaPlayer的监听方法，音频准备完毕会响应此方法
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (flag == 0) {
                        mp.start();
                    }
                    flag = 0;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //活动销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}//MainActivity
