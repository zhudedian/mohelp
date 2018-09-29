package com.edong.mohelp.net;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.edong.mohelp.R;
import com.edong.mohelp.config.util.WifiAdapter;
import com.edong.mohelp.config.view.WifiSignalView;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<Device> {

    private List<Device> devices;
    public DeviceAdapter(Context context, int textViewResourceId, List<Device> objects){
        super(context,textViewResourceId,objects);
        this.devices = objects;
    }
    @Override
    public View getView(int posetion, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        final Device device = devices.get(posetion);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_device, null);
            viewHolder.product = convertView.findViewById(R.id.product);
            viewHolder.ip = convertView.findViewById(R.id.ip);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView .getTag();
        }

        viewHolder.product.setText(device.getProduct());
        viewHolder.ip.setText(device.getIp());
        return convertView;
    }

    class ViewHolder{
        TextView product,ip;

    }
}
