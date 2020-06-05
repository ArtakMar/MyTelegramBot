package ru.artak;

import ru.artak.client.telegram.TelegramClient;
import ru.artak.server.BotHttpServer;
import ru.artak.service.StravaService;
import ru.artak.service.TelegramService;
import ru.artak.storage.InMemoryStorage;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        TelegramClient telegramClient = new TelegramClient();
        InMemoryStorage inMemoryStorage = InMemoryStorage.getInstance();
        
        StravaService stravaService = new StravaService(telegramClient, inMemoryStorage);
        BotHttpServer botHttpServer = new BotHttpServer(stravaService);
        
        Thread httpServerThread = new Thread(() -> {
            try {
                botHttpServer.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        httpServerThread.start();
        
        TelegramService telegramService = new TelegramService(telegramClient, inMemoryStorage);
        telegramService.sendGet();
    }
}




