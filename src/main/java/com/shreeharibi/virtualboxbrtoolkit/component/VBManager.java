package com.shreeharibi.virtualboxbrtoolkit.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.virtualbox_6_1.ISession;
import org.virtualbox_6_1.IVirtualBox;
import org.virtualbox_6_1.VBoxException;
import org.virtualbox_6_1.VirtualBoxManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@PropertySource("classpath:application.properties")
public class VBManager {

    private IVirtualBox vbox;

    private VirtualBoxManager virtualBoxManager;

    private ISession session;

    @Value("${virtualbox.vboxwebsrv.url}")
    private String url;

    public VBManager() {
        try {
            virtualBoxManager = VirtualBoxManager.createInstance(null);
        } catch (VBoxException e) {
            throw new IllegalStateException("Failed to instantiate virtualboxmanager");
        }
    }

    @PostConstruct
    public void connect() {
        try {
            System.out.println("Connecting...");
            virtualBoxManager.connect(url, null, null);
            vbox = virtualBoxManager.getVBox();
            session = virtualBoxManager.getSessionObject();
        } catch (VBoxException e) {
            System.out.println("Failed to connect to the virtualboxmanager");
        }
    }

    @PreDestroy
    public void disconnect() {
        try{
            System.out.println("Disconnecting...");
            virtualBoxManager.disconnect();
        } catch (VBoxException e) {
            System.out.println("Failed to disconnect from the virtualboxmanager");
        }
    }

    @Bean
    public IVirtualBox getVirtualBoxInstance() {
        return vbox;
    }

    public ISession getSession() {
        return session;
    }

    public void setSession(ISession session) {
        this.session = session;
    }
}
