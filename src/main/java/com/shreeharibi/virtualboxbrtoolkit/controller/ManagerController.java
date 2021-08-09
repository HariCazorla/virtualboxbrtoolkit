package com.shreeharibi.virtualboxbrtoolkit.controller;

import com.shreeharibi.virtualboxbrtoolkit.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manager")
public class ManagerController {
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("about")
    public String getDescription() {
        try {
            return "virtual box brtoolkit is up. " + "virtual-box version:" + managerService.getVirtualBoxVersion();
        }
        catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @GetMapping("vm")
    @ResponseBody
    public List<String> getVirtualMachines() {
        try {
            return managerService.getVirtualMachines();
        } catch (IllegalStateException e) {
            return List.of(e.getMessage());
        }
    }

    @GetMapping(path = "{vmID}")
    @ResponseBody
    public Object getVirtualMachineSummary(
            @PathVariable("vmID") String vmID
    ) {
        try {
            return managerService.getVirtualMachineSummary(vmID);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
