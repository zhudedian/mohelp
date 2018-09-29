package com.edong.mohelp.skill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edong.mohelp.R;
import com.edong.mohelp.music.Music;
import com.edong.mohelp.music.MusicAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SkillAdapter extends ArrayAdapter<Skill> {
    private List<Skill> skills;
    public SkillAdapter(Context context, int textViewResourceId, List<Skill> objects){
        super(context,textViewResourceId,objects);
        this.skills = objects;
    }
    @Override
    public View getView(int posetion, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        final Skill skill = skills.get(posetion);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_skill, null);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.content1 = convertView.findViewById(R.id.content1);
            viewHolder.content2 = convertView.findViewById(R.id.content2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView .getTag();
        }

        viewHolder.title.setText(skill.getTitle());
        viewHolder.content1.setText(skill.getContent1());
        viewHolder.content2.setText(skill.getContent2());
        return convertView;
    }

    class ViewHolder{
        TextView title,content1,content2;

    }
}
