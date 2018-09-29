package com.edong.mohelp.net;

public class Device {

    private String ip;
    private String serial;
    private String product;
    private String device;
    private String display_id;
    private String id;
    private String board;
    private String hardware;
    private String wifi;
    private String bluetooth;

    public Device(String ip,String serail,String product){
        this.ip = ip;
        this.serial = serail;
        this.product = product;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSerial() {
        return serial;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setDisplay_id(String display_id) {
        this.display_id = display_id;
    }

    public String getDisplay_id() {
        return display_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBoard() {
        return board;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getHardware() {
        return hardware;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getWifi() {
        return wifi;
    }

    @Override
    public int hashCode(){
        return 2;
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof Device){
            Device device = (Device)object;
            if (device.serial.equals(serial)){
                return true;
            }
        }
        return false;
    }
}
