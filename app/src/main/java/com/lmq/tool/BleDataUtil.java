package com.lmq.tool;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

public class BleDataUtil {

    BluetoothGattCharacteristic mWriteCharacteristic;
    BluetoothGattCharacteristic mReadCharacteristic;
    BluetoothGatt mBluetoothGatt;
    public static  String END_FLAG="===end===";
    public static  int MTU=20;
    public void sendMessageBLE(String msg) {
        if (mBluetoothGatt != null && mWriteCharacteristic != null) {
            msg += END_FLAG;
            byte[] temp, data = msg.getBytes();
            int i = 0, j = 0;

            for (; i < data.length / MTU; i++) {
                temp = new byte[MTU];
                System.arraycopy(data, j, temp, 0, MTU);
                //logd("send: " + new String(temp));
                mWriteCharacteristic.setValue(temp);
                mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
              //  logd(mBluetoothGatt.writeCharacteristic(mWriteCharacteristic));
                j += MTU;
            }

            if (j < data.length) {
                temp = new byte[data.length - j];
                System.arraycopy(data, j, temp, 0, data.length - j);
                //logd("send: " + new String(temp));
                mWriteCharacteristic.setValue(temp);
                mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
               // logd(mBluetoothGatt.writeCharacteristic(mWriteCharacteristic));
            }
        } else {
           // logd("can not send message out by BLE.");
        }
    }
    public StringBuilder receiveMsg=new StringBuilder();
    public  void receiveData() {

        receiveMsg.append(new String(mReadCharacteristic.getValue()));
        if (receiveMsg.toString().contains(END_FLAG)
                && receiveMsg.toString().endsWith(END_FLAG)) {
            String[] msgs = receiveMsg.toString().split(END_FLAG);
            for (String s : msgs) {
              //  logd(s.length() + ":" + s);/处理接受到的数据
              //  dealwithReceivedMsg(s);
            }
            receiveMsg = new StringBuilder();

        }
    }

    }
