package com.shreeharibi.virtualboxbrtoolkit.model;

import org.virtualbox_6_1.IAppliance;
import org.virtualbox_6_1.IMachine;
import org.virtualbox_6_1.IMediumAttachment;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {
    private String name;
    private String description;
    private List<VirtualDisk> disks;
    private String configPath;

    public VirtualMachine() {
        disks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<VirtualDisk> getDisks() {
        return disks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addDisk(VirtualDisk disk) {
        this.disks.add(disk);
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", configPath='" + configPath + '\'' +
                '}';
    }
}
