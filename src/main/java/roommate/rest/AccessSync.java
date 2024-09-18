package roommate.rest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import roommate.rest.AccessEntity.Key;
import roommate.rest.AccessEntity.Room;

import java.time.Duration;
import java.util.List;

@Service
public class AccessSync {

    @Scheduled(fixedDelay = 1000L)
    public void updateRoomAndKeys(){
        fetchKey();
        fetchRoom();
    }


    public List<Room> fetchRoom(){
        return WebClient.create()
                .get()
                .uri(uri->uri.scheme("http")
                        .host("localhost")
                        .port(3000)
                        .path("/room")
                        .build()
                ).retrieve()
                .bodyToFlux(Room.class)
                .collectList().block(Duration.ofSeconds(1L));
    }


    public List<Key> fetchKey(){
       return WebClient.create()
                .get()
                .uri(uri->uri.scheme("http")
                        .host("localhost")
                        .port(3000)
                        .path("/key")
                        .build()
                ).retrieve()
                .bodyToFlux(Key.class)
                .collectList().block(Duration.ofSeconds(1L));
    }






}
