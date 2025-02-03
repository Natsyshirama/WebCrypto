package controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.crypto.CryptoCours;

public class AppContextListener implements ServletContextListener {

    private CryptoCours updater;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        updater = new CryptoCours();
        updater.start();
        System.out.println("Mise a jour Cours  démarré.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (updater != null) {
            updater.stop();
        }
        System.out.println("Mise a jour Cours arrêté.");
    }
}
