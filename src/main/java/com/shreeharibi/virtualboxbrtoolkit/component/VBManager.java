package com.shreeharibi.virtualboxbrtoolkit.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.virtualbox_6_1.VirtualBoxManager;

@Component
@PropertySource("classpath:application.properties")
public class VBManager {
    @Autowired
    private VirtualBoxManager virtualBoxManager;

    public VBManager() {
        virtualBoxManager = VirtualBoxManager.createInstance(null);
    }

    @Bean
    public VirtualBoxManager getVirtualBoxManager() {
        return virtualBoxManager;
    }
}
