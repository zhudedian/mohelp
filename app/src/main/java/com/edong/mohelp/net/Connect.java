package com.edong.mohelp.net;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eric on 2017/9/6.
 */

public class Connect {

    private static Context context;
    private static InetAddress address;
    private static MulticastSocket multicastSocket;
    private static Handler mHandler;
    private static int connectCount ;
    private static List<Device> devices = new ArrayList<>();
    private static boolean isConnecting = false;
    private static boolean isScanComplete;
    private static int readCount = 0;
    public static boolean isConnect = false;
    public static String boxIP;
    public static String eventUrl;
    public static String downchannelUrl;
    public static void onBrodacastSend(Context context,Handler handler) {
        if (isConnecting){
            return;
        }
        if (NetUtil.isMobileConnected(context)){
            Toast.makeText(context, "请连接WiFi", Toast.LENGTH_SHORT).show();
            return;
        }
        Connect.context = context;
        devices.clear();
        isScanComplete = false;
        isConnecting = true;
        if (handler!=null)
        mHandler = handler;
        connectCount = 0;
        readCount = 0;
        isConnect = false;
        try {
            // 侦听的端口
            multicastSocket = new MulticastSocket(8082);
            // 使用D类地址，该地址为发起组播的那个ip段，即侦听10001的套接字
            address = InetAddress.getByName("239.0.0.1");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(sendInfo,1000);
                    while (!isConnect&&!isScanComplete) {
                        // 获取当前时间
                        //String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        // 当前时间+标识后缀
                        //time = time + " >>> form server onBrodacastSend()";
                        String msg = "connect";
                        // 获取当前时间+标识后缀的字节数组
                        byte[] buf = msg.getBytes();
                        // 组报
                        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                        // 向组播ID，即接收group /239.0.0.1  端口 10001
                        datagramPacket.setAddress(address);
                        // 发送的端口号
                        datagramPacket.setPort(10001);
                        try {
                            // 开始发送
                            multicastSocket.send(datagramPacket);
                            onBrodacastReceiver();
                            // 每执行一次，线程休眠2s，然后继续下一次任务
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void onBrodacastReceiver() {
        //Log.i("receiver","onBrodacastReceiver");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 字节数组的格式，即最大大小
                    byte[] buf = new byte[1024];
                        // 组报格式
                        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                        // 接收来自group组播10001端口的二次确认，阻塞
                        multicastSocket.receive(datagramPacket);
                        // 从buf中截取收到的数据
                        byte[] message = new byte[datagramPacket.getLength()];
                        // 数组拷贝
                        System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
                        // 这里打印ip字段
                    String ip = datagramPacket.getAddress().toString();
                    String ms = new String(message);
                    if (ms.contains("snoop")){
                        mHandler.removeCallbacks(sendInfo);
                        String[] deviceInfo = ms.replace("snoop","").split(":");
                        Device device = new Device(ip.replace("/",""),deviceInfo[0],deviceInfo[1]);
                        Device bindDevice = BindUtil.getBindDevice();
                        if (bindDevice!=null&&bindDevice.equals(device)){
                            isScanComplete = true;
                            BindUtil.setBindDevice(device);
                            setData(device);
                            return;
                        }

                        if (!devices.contains(device)){
                            devices.add(device);
                        }else {
                            readCount++;
                            if (readCount>=5) {
                                isConnecting = false;
                                isScanComplete = true;

                                handler.sendEmptyMessage(0);


                            }
                        }

                    }
                        System.out.println(ip);
                        // 打印组播端口10001发送过来的消息
                        System.out.println(new String(message));
                        // 这里可以根据结接收到的内容进行分发处理，假如收到 10001的 "snoop"字段为关闭命令，即可在此处关闭套接字从而释放资源
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void setData(Device device){
        isConnecting = false;
        isConnect = true;
        String oldIP = boxIP;
        boxIP = device.getIp();

        eventUrl = "http://"+boxIP+":8083/event";
        downchannelUrl = "http:/"+boxIP+":8083/downchannel";

        if (mHandler!=null) {
            mHandler.sendEmptyMessage(0);
        }
    }
    private static Runnable sendInfo = new Runnable() {
        @Override
        public void run() {
            isConnecting = false;
            isScanComplete = true;
            mHandler.sendEmptyMessage(1);
        }
    };

    public static List<Device> getDevices(){
        return devices;
    }
    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    if (devices.size()!=0){
                        Intent intent = new Intent(context,DeviceListActivity.class);
                        context.startActivity(intent);
                    }else {
                        if (mHandler!=null) {
                            mHandler.sendEmptyMessage(1);
                        }
                    }

                    break;
                default:
                    break;
            }

        }
    };
    public interface CompleteListener{
        void complete(List<Device> list);
    }
}
