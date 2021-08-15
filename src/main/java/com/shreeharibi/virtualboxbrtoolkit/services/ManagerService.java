package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import com.shreeharibi.virtualboxbrtoolkit.exceptions.VirtualMachineException;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualDisk;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualMachine;
import com.shreeharibi.virtualboxbrtoolkit.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(ManagerService.class);

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

    public List<String> getVirtualMachines() throws VirtualMachineException {
        List<String> result = new ArrayList<>();
        try {
            logger.info("Fetching the virtual machine instances");
            List<IMachine> vmList = vbox.getMachines();
            result = vmList.stream()
                                    .map(m -> m.getId())
                                    .collect(Collectors.toList());

        } catch (VBoxException e) {
            throw new VirtualMachineException("Failed to fetch the virtual machine instances", e);
        }
        return result;
    }

    public VirtualMachine getVirtualMachineSummary(String vmId) throws VirtualMachineException {
        try {
            logger.info("Fetching the virtual machine instances");
            String result = null;
            IMachine vmObj = vbox.getMachines().stream()
                    .filter(m -> m.getId().contentEquals(vmId))
                    .collect(Collectors.toList()).get(0);
            logger.info("creating virtual machine summary");
            return Utils.createVMfromIMachine(vmObj);
        } catch (VBoxException e) {
            throw new VirtualMachineException("Failed to fetch the virtual machine instances.");
        }
    }

    public boolean renameVirtualMachine(String vmId, String newName) throws VirtualMachineException {
        try {
            logger.info("Fetching the virtual machine instances");
            String result = null;
            IMachine vmObj = vbox.getMachines().stream()
                    .filter(m -> m.getId().contentEquals(vmId))
                    .collect(Collectors.toList()).get(0);
            logger.info(vmObj.getName() + ": attempting to lock the virtual machine");
            vmObj.lockMachine(session, LockType.Shared);
            logger.info(vmObj.getName() + ": lock the virtual machine successfully");
            session.getMachine().setName(newName);
            logger.info(vmObj.getName() + ": attempting to rename the virtual machine");
            session.getMachine().saveSettings();
            logger.info(vmObj.getName() + ": rename virtual machine successfully");
            return true;
        } catch (VBoxException e) {
            throw new VirtualMachineException("Failed to rename virtual machine.");
        }
    }
}
