package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualDisk;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualMachine;
import com.shreeharibi.virtualboxbrtoolkit.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private IVirtualBox vbox;
    private ISession session;

    @Autowired
    public ManagerService(VBManager vbManager) {
        vbox = vbManager.getVirtualBoxInstance();
        session = vbManager.getSession();
    }

    public String getVirtualBoxVersion() {
        String version = null;
        try {
            if (vbox != null) {
                version = vbox.getVersion();
            }
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
            List<IMachine> vmList = vbox.getMachines();
            result = vmList.stream()
                                    .map(m -> m.getId())
                                    .collect(Collectors.toList());

        } catch (VBoxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to fetch the VM information.");
        }
        return result;
    }

    public VirtualMachine getVirtualMachineSummary(String vmId) {
        try {
            String result = null;
            IMachine vmObj = vbox.getMachines().stream()
                    .filter(m -> m.getId().contentEquals(vmId))
                    .collect(Collectors.toList()).get(0);
            return Utils.createVMfromIMachine(vmObj);
        } catch (VBoxException e) {
            throw new IllegalStateException("Failed to create VM summary.");
        }
    }

    public boolean renameVirtualMachine(String vmId, String newName) {
        try {
            String result = null;
            IMachine vmObj = vbox.getMachines().stream()
                    .filter(m -> m.getId().contentEquals(vmId))
                    .collect(Collectors.toList()).get(0);
            vmObj.lockMachine(session, LockType.Shared);
            session.getMachine().setName(newName);
            session.getMachine().saveSettings();
            return true;
        } catch (VBoxException e) {
            throw new IllegalStateException("Failed to create VM summary.");
        }
    }
}
