package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualDisk;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private IVirtualBox vbox;

    @Autowired
    public ManagerService(VBManager vbManager) {
        vbox = vbManager.getVirtualBoxInstance();
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
            VirtualMachine vm = new VirtualMachine();
            IMachine vmObj = vbox.getMachines().stream()
                    .filter(m -> m.getId().contentEquals(vmId))
                    .collect(Collectors.toList()).get(0);
            return vm.createVMfromIMachine(vmObj);
        } catch (VBoxException e) {
            throw new IllegalStateException("Failed to create VM summary.");
        }
    }
}
