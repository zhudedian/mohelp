package com.edong.mohelp.skill;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.edong.mohelp.R;

import java.util.ArrayList;
import java.util.List;

public class SkillUtil {
    private static List<Skill> skills;

    public static List<Skill> getSkills(Context context) {
        if (skills!=null){
            return skills;
        }
        skills= new ArrayList<>();
        try {

            //获取信息的方法
            Resources res = context.getResources();
            XmlResourceParser xrp = res.getXml(R.xml.skill);
            //判断是否已经到了文件的末尾
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if (name.equals("skill")) {
                        //关键词搜索，如果匹配，那么添加进去如果不匹配就不添加，如果没输入关键词“”,那么默认搜索全部
                        String title = xrp.getAttributeValue(null, "title");
                        String content1 = xrp.getAttributeValue(null, "content1");
                        String content2 = xrp.getAttributeValue(null, "content2");
//                        Log.e("edong","title="+title+",content1="+content1+",content2="+content2);
                        Skill skill = new Skill(title,"\""+content1+"\"","\""+content2+"\"");
                        skills.add(skill);
                    }
                }
                //搜索过第一个信息后，接着搜索下一个
                xrp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return skills;
    }
}
