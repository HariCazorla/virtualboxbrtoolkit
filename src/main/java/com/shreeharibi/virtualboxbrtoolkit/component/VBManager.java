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
    private String url;
    private String username;
    private String password;

    public VBManager() {
        virtualBoxManager = VirtualBoxManager.createInstance(null);
        url = "http://localhost:18083";
        System.out.println("Connecting to " + url);
        virtualBoxManager.connect(url, null, null);
    }

    @Bean
    public VirtualBoxManager getVirtualBoxManager() {
        return virtualBoxManager;
    }
}
