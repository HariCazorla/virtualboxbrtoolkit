package com.shreeharibi.virtualboxbrtoolkit.services;

import com.shreeharibi.virtualboxbrtoolkit.component.VBManager;
import com.shreeharibi.virtualboxbrtoolkit.model.VirtualDisk;
import com.shreeharibi.virtualboxbrtoolkit.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.virtualbox_6_1.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackupService {
    private IVirtualBox vbox;
    private ISession session;

    @Autowired
    public BackupService(VBManager vbManager) {
        vbox = vbManager.getVirtualBoxInstance();
        session = vbManager.getSession();
    }

    public String backupVirtualMachine(String vmID, boolean encryption) {
        IMachine vmObj = vbox.getMachines().stream()
                .filter(m -> m.getId().contentEquals(vmID))
                .collect(Collectors.toList()).get(0);
        System.out.println("Locking vm:"+ vmObj.getName());
        vmObj.lockMachine(session, LockType.Shared);
        if (vmObj.getSnapshotCount() > 0) {
            throw new IllegalStateException("User snapshot found, backup aborted...");
        }
        List<VirtualDisk> disks = Utils.createVMfromIMachine(vmObj).getDisks();
        try {
            File configFile = new File(vmObj.getSettingsFilePath());
            File backupConfig = new File("D:\\backups\\" + configFile.getName());
            System.out.println("Backup config file:" + configFile.getAbsolutePath());
            FileUtils.copyFile(configFile, backupConfig);
            System.out.println("Creating a snapshot of the vm:" + vmObj.getName());
            Holder<String> snapshotUUID = new Holder<>();
            System.out.println("vm state:"+vmObj.getState());
            IProgress progress = session.getMachine().takeSnapshot(
                    "backup_snapshot" + LocalDateTime.now(),
                    "backup snapshot taken by vbox brtoolkit",
                    true,
                    snapshotUUID
            );
            progress.waitForCompletion(-1);
            System.out.println("Snapshot id:"+snapshotUUID);
            for (VirtualDisk disk:
                    disks) {
                File diskFile = new File(disk.getPath());
                File backupFile = new File("D:\\backups\\" + diskFile.getName());
                FileInputStream fin  = null;
                FileOutputStream fout = null;
                long length = diskFile.length();
                System.out.println("Length:"+length);
                long counter = 0;
                int r = 0;
                System.out.println("Backup disk:" + diskFile.getAbsolutePath());
                byte[] b = new byte[1024];
                try {
                    fin  = new FileInputStream(diskFile);
                    fout = new FileOutputStream(backupFile);
                    while( (r = fin.read(b)) != -1) {
                        counter += r;
                        System.out.println( 1.0 * counter / length );
                        fout.write(b, 0, r);
                    }
                }
                catch(Exception e){
                    System.out.println("Error while copying disk file.");
                }
            }
            System.out.println("Removing snapshot:" + snapshotUUID.value);
            session.getMachine().deleteSnapshot(snapshotUUID.value);
            System.out.println("Unlocking vm:"+ vmObj.getName());
            session.unlockMachine();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to backup vm";
        }
    }
}
