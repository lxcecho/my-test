package org.example.biz;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17:53 25-02-2023
 */
public class CecData {

    @ExcelProperty(value = "devicetype")
    private String deviceType;

    @ExcelProperty(value = "tclosversion")
    private String tclOsVersion;

    @ExcelProperty(value = "cec_devices")
    private String cecDevices;

    @ExcelProperty(value = "cec_message")
    private String cecMessage;

    @ExcelProperty(value = "amp_devices")
    private String ampDevices;

    @Override
    public String toString() {
        return "CecMessage{" +
                "deviceType='" + deviceType + '\'' +
                ", tclOsVersion='" + tclOsVersion + '\'' +
                ", cecDevices='" + cecDevices + '\'' +
                ", cecMessage='" + cecMessage + '\'' +
                ", ampDevices='" + ampDevices + '\'' +
                '}';
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTclOsVersion() {
        return tclOsVersion;
    }

    public void setTclOsVersion(String tclOsVersion) {
        this.tclOsVersion = tclOsVersion;
    }

    public String getCecDevices() {
        return cecDevices;
    }

    public void setCecDevices(String cecDevices) {
        this.cecDevices = cecDevices;
    }

    public String getCecMessage() {
        return cecMessage;
    }

    public void setCecMessage(String cecMessage) {
        this.cecMessage = cecMessage;
    }

    public String getAmpDevices() {
        return ampDevices;
    }

    public void setAmpDevices(String ampDevices) {
        this.ampDevices = ampDevices;
    }
}
