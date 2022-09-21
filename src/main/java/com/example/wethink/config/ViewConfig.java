package com.example.wethink.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ViewConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("start ... run your browser ... ");
        try {
            Runtime.getRuntime().exec("cmd   /c   start  http://localhost:8081/");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
