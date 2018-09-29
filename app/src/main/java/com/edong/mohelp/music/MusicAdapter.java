package com.edong.mohelp.music;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edong.mohelp.R;
import com.edong.mohelp.config.util.WifiAdapter;
import com.edong.mohelp.config.view.WifiSignalView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicAdapter extends ArrayAdapter<Music> {

    private List<Music> musics;
    public MusicAdapter(Context context, int textViewResourceId, List<Music> objects){
        super(context,textViewResourceId,objects);
        this.musics = objects;
    }
    @Override
    public View getView(int posetion, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        final Music music = musics.get(posetion);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_music, null);
            viewHolder.imageView = convertView.findViewById(R.id.image_view);
            viewHolder.art = convertView.findViewById(R.id.art);
            viewHolder.artist = convertView.findViewById(R.id.artist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView .getTag();
        }
        Glide.with(getContext()).load(music.getAlbumPic()).error(R.drawable.ic_music_defult).into(viewHolder.imageView);
        viewHolder.art.setText(music.getTitle());
        viewHolder.artist.setText(music.getArtist()+" - "+music.getAlbum());
        return convertView;
    }

    class ViewHolder{
        CircleImageView imageView;
        TextView art,artist;

    }
}
