package com.alisimsek.HumorousBlog.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "humorous")
@Configuration
public class HumorousProperties {
    private Email email;
    private Client client;

    private Storage storage = new Storage();

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public static record Email(
            String username,
            String password,
            String host,
            int port,
            String from
    ) { }

    public static record Client (
            String host
    ){}

    @Getter
    @Setter
    public static class Storage{
        String root = "uploads";
        String profile = "profile";
    }

}
