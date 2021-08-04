package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.IMachine;
import org.virtualbox_6_1.IVirtualBox;
import org.virtualbox_6_1.VBoxException;
import org.virtualbox_6_1.VirtualBoxManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private VBManager vbManager;
    private String url;


    @Autowired
    public ManagerService(VBManager vbManager) {
        this.vbManager = vbManager;
        url = "http://localhost:18083";
    }

    public String getVirtualBoxVersion() {
        String version = null;
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

    public List<String> getVirtualMachines() {
        List<String> result = new ArrayList<>();
        try {
            VirtualBoxManager virtualBoxManager = vbManager.getVirtualBoxManager();
            virtualBoxManager.connect(url, null, null);
            IVirtualBox vbox = vbManager.getVirtualBoxManager().getVBox();
            List<IMachine> vmList = vbox.getMachines();

            result = vmList.stream()
                                    .map(m -> m.getName())
                                    .collect(Collectors.toList());

        } catch (VBoxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to fetch the VM information.");
        }
        return result;
    }
}
