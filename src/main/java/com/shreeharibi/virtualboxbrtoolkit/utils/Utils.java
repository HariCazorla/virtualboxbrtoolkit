package com.shreeharibi.virtualboxbrtoolkit.utils;

import com.shreeharibi.virtualboxbrtoolkit.model.VirtualDisk;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualMachine;
import org.virtualbox_6_1.IMachine;
import org.virtualbox_6_1.IMediumAttachment;

import java.util.List;

public class Utils {
    public static VirtualMachine createVMfromIMachine(IMachine obj){
        VirtualMachine vm = new VirtualMachine();
        vm.setName(obj.getName());
        vm.setDescription(obj.getDescription());
        vm.setConfigPath(obj.getSettingsFilePath());
        List<IMediumAttachment> mediumAttachments = obj.getMediumAttachments();
        for (IMediumAttachment medium:
                mediumAttachments) {
            if(medium.getMedium() != null){
                VirtualDisk disk = new VirtualDisk();
                disk.setName(medium.getMedium().getName());
                disk.setSize(medium.getMedium().getLogicalSize());
                disk.setPath(medium.getMedium().getLocation());
                disk.setController(medium.getController());
                vm.addDisk(disk);
            }
        }
        return vm;
    }
}
