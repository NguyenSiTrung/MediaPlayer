package nicemp3player.trungns.com.nicemp3player;

import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thanh Nguyen on 7/23/2016.
 */
public class SongAdapter extends BaseAdapter {
    private List<Song> listSong;
    private LayoutInflater mInflate;
    private LevelListDrawable levelListDrawable;

    public SongAdapter(Context context, List<Song> songs) {
        listSong = songs;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int position) {
        return listSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflate.inflate(R.layout.item_view, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvArtist = (TextView) view.findViewById(R.id.tv_artist);
            holder.tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            holder.mIvPlayOrPause =(ImageView)view.findViewById(R.id.iv_play_pause);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        Song itemSong = listSong.get(position);
        if(itemSong.isPlaying()){
            holder.mIvPlayOrPause.setImageLevel(1);
            view.setBackgroundResource(R.color.bgActionBar);
        }
        else {
            holder.mIvPlayOrPause.setImageLevel(0);
            view.setBackgroundResource(R.color.bgmp3);
        }
        holder.tvName.setText(itemSong.getName());
        holder.tvArtist.setText(itemSong.getArtist());
        holder.tvDuration.setText(itemSong.getTime());
        return view;
    }

    private class ViewHolder {
        TextView tvName, tvArtist, tvDuration;
        ImageView mIvPlayOrPause;
    }
}
