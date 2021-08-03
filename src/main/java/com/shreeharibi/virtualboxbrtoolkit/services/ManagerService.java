package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.IVirtualBox;

@Service
public class ManagerService {

    private VBManager vbManager;

    @Autowired
    public ManagerService(VBManager vbManager) {
        this.vbManager = vbManager;
    }

    public String getVirtualBoxVersion() {
        IVirtualBox vbox = vbManager.getVirtualBoxManager().getVBox();
        if (vbox != null) {
            return vbox.getVersion();
        }
        return null;
    }
}
