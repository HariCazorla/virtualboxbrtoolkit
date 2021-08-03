package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.IVirtualBox;
import org.virtualbox_6_1.VBoxException;
import org.virtualbox_6_1.VirtualBoxManager;

@Service
public class ManagerService {

    private VBManager vbManager;

    @Autowired
    public ManagerService(VBManager vbManager) {
        this.vbManager = vbManager;
    }

    public String getVirtualBoxVersion() {
        String url = "http://localhost:18083";
        String version = null;
        System.out.println("Connecting to " + url);
        try {
            VirtualBoxManager virtualBoxManager = vbManager.getVirtualBoxManager();
            virtualBoxManager.connect(url, null, null);
            IVirtualBox vbox = vbManager.getVirtualBoxManager().getVBox();
            if (vbox != null) {
                version = vbox.getVersion();
            }
            virtualBoxManager.disconnect();
        }
        catch (VBoxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to fetch the version.");
        }
        return version;
    }
}
