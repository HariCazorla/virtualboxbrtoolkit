package com.shreeharibi.virtualboxbrtoolkit.controller;

import com.shreeharibi.virtualboxbrtoolkit.services.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/backup")
public class BackupController {
    private final BackupService backupService;
    @Autowired
    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @PostMapping(path = "{vmID}/backup")
    public String backupVirtualMachine(
            @PathVariable("vmID") String vmID,
            @RequestParam(required = true) boolean encryption
    ) {
        try {
            return backupService.backupVirtualMachine(vmID, encryption);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
