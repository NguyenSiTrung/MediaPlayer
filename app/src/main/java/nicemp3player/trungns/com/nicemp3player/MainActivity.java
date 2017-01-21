package nicemp3player.trungns.com.nicemp3player;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, MediaManager.OnPlayStartedListener,AdapterView.OnItemLongClickListener {
    private static final int LEVEL_PAUSE = 1;
    private static final int LEVEL_PLAY = 0;
    private static final int UPDATE_PROGRESS = 101;
    private static boolean IS_RUNNING = false;
    private MyDialogInfomation myDialogInfomation;
    private TextView mTxtNameSongInfo, mTxtCaSiInfo,mTxtSizeList;
    private ImageView ivPlay, ivStop, ivNext, ivBack;
    private ListView lvSong;
    SongAdapter songAdapter;
    private int oldMusic;
    private MediaManager mediaMgr;
    private SeekBar mSeekBar;
    private TextView tvName, tvCurrentTime, tvTotalTime;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_PROGRESS:
                    mSeekBar.setProgress(msg.arg1);
                    if (mSeekBar.getProgress()>= mSeekBar.getMax()-110){
                        oldMusic = mediaMgr.getIndexSong();

                        mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
                        songAdapter.notifyDataSetChanged();
                        mediaMgr.next();
                        mediaMgr.getListSong().get(mediaMgr.getIndexSong()).setIsPlaying(true);
                        songAdapter.notifyDataSetChanged();

                        oldMusic = mediaMgr.getIndexSong();

                        mTxtNameSongInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getName());
                        mTxtCaSiInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getArtist());
                        int size = mediaMgr.getListSong().size();
                        mTxtSizeList.setText(mediaMgr.getIndexSong()+1+"/"+size);
                    }
                    tvCurrentTime.setText(MainActivity.this.getTime(msg.arg1));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldMusic = 0;
        initViews();
        mSeekBar.setMax(8888);

        mediaMgr = new MediaManager(this);
        mediaMgr.setOnPlayStartedListener(this);

        songAdapter = new SongAdapter(this, mediaMgr.getListSong());
        lvSong.setAdapter(songAdapter);

        mTxtNameSongInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getName());
        mTxtCaSiInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getArtist());
        int size = mediaMgr.getListSong().size();
        mTxtSizeList.setText(mediaMgr.getIndexSong()+1+"/"+size);

        Thread thread = new Thread(runnable);
        thread.start();

    }

    private void initViews() {
        lvSong = (ListView) findViewById(R.id.lv_song);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        ivPlay = (ImageView) findViewById(R.id.iv_play);
        ivStop = (ImageView) findViewById(R.id.iv_stop);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        tvTotalTime = (TextView) findViewById(R.id.tv_total_time);

        mTxtNameSongInfo = (TextView)findViewById(R.id.tv_song_name_info);
        mTxtCaSiInfo = (TextView)findViewById(R.id.tv_tenCaSi_info);
        mTxtSizeList =(TextView)findViewById(R.id.tv_size_list_info);
        lvSong.setOnItemLongClickListener(this);

        ivBack.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivStop.setOnClickListener(this);
        lvSong.setOnItemClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                mediaMgr.playOrPause(false);
                mediaMgr.getListSong().get(mediaMgr.getIndexSong()).setIsPlaying(true);
                songAdapter.notifyDataSetChanged();
                IS_RUNNING = true;
                break;
            case R.id.iv_next:
                IS_RUNNING = true;
                mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
                songAdapter.notifyDataSetChanged();
                oldMusic = mediaMgr.getIndexSong();
                mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
                songAdapter.notifyDataSetChanged();
                mediaMgr.next();
                mediaMgr.getListSong().get(mediaMgr.getIndexSong()).setIsPlaying(true);
                songAdapter.notifyDataSetChanged();
                oldMusic = mediaMgr.getIndexSong();
                songAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                IS_RUNNING = true;
                mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
                songAdapter.notifyDataSetChanged();
                oldMusic = mediaMgr.getIndexSong();
                mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
                songAdapter.notifyDataSetChanged();
                mediaMgr.previous();
                mediaMgr.getListSong().get(mediaMgr.getIndexSong()).setIsPlaying(true);
                songAdapter.notifyDataSetChanged();
                oldMusic = mediaMgr.getIndexSong();
                songAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_stop:
                //IS_RUNNING = false;
                mediaMgr.stop();
                break;
            default:
                break;

        }
        changeImageState();
    }

    private void changeImageState() {
        if (mediaMgr.isPlaying()) {
            ivPlay.setImageLevel(LEVEL_PAUSE);
        } else {
            ivPlay.setImageLevel(LEVEL_PLAY);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        IS_RUNNING = true;
        mediaMgr.setIndexSong(index);
        mediaMgr.playOrPause(true);
        changeImageState();
        mediaMgr.getListSong().get(oldMusic).setIsPlaying(false);
        songAdapter.notifyDataSetChanged();

        mediaMgr.getListSong().get(mediaMgr.getIndexSong()).setIsPlaying(true);
        songAdapter.notifyDataSetChanged();

        oldMusic = index;

        mTxtNameSongInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getName());
        mTxtCaSiInfo.setText(mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getArtist());
        int size = mediaMgr.getListSong().size();
        mTxtSizeList.setText(mediaMgr.getIndexSong()+1+"/"+size);


    }

    @Override
    protected void onDestroy() {
        //mediaMgr.stop();
       // IS_RUNNING=false;
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaMgr.seekTo(seekBar.getProgress());
    }

    @Override
    public void onMediaStarted(String songName, String totalTime, int totalTimeInt) {
        tvCurrentTime.setText("00:00");
        tvTotalTime.setText(totalTime);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(totalTimeInt);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            int progress = mediaMgr.getCurrentTime();
            while (progress < mediaMgr.getListSong().get(mediaMgr.getIndexSong()).getDuration()) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress = mediaMgr.getCurrentTime();
                Message message = new Message();
                message.what = UPDATE_PROGRESS;
                message.arg1 = progress;
                message.setTarget(handler);
                message.sendToTarget();
            }
        }
    };
    private String getTime(Integer value) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(new Date(value));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        myDialogInfomation = new MyDialogInfomation(MainActivity.this);

        myDialogInfomation.getmTxtName().setText(mediaMgr.getListSong().get(position).getName());
        myDialogInfomation.getmTxtCasi().setText("Ca Sĩ : "+mediaMgr.getListSong().get(position).getArtist());
        myDialogInfomation.getmTxtAlbum().setText("Album : "+mediaMgr.getListSong().get(position).getAlbum());
        myDialogInfomation.getmTxtTime().setText("Thời lượng : "+mediaMgr.getListSong().get(position).getTime());

                MainActivity.this.myDialogInfomation.show();

        return true;
    }
}
