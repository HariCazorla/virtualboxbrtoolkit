package com.shreeharibi.virtualboxbrtoolkit.model;

public class VirtualDisk {
    private String name;
    private Long size;
    private String path;
    private String controller;

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public VirtualDisk() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VirtualDisk{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", controller='" + controller + '\'' +
                '}';
    }
}
