package io.github.sjchin88.Twinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@RequestMapping("/Twinder")
public class TwinderApplication {
    private static final int LOWER_BOUND = 0;
    private static final int SWIPER_UPPER = 1_000_000;
    private static final int SWIPEE_UPPER = 1_000_000;

    public static void main(String[] args) {
        SpringApplication.run(TwinderApplication.class, args);
    }

    @GetMapping("/")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping(path = {"/swipe/left", "/swipe/right"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<SwipeResponse> addSwipe(@RequestBody SwipeDetail swipeDetail){
        SwipeResponse swipeResponse = new SwipeResponse();
        System.out.println(swipeDetail);
        int swiperId  = Integer.parseInt(swipeDetail.getSwiper());
        if(swiperId < LOWER_BOUND || swiperId > SWIPER_UPPER) {
            swipeResponse.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(swipeResponse);
        }

        int swipeeId = Integer.parseInt(swipeDetail.getSwipee());
        if(swipeeId < LOWER_BOUND || swipeeId > SWIPEE_UPPER){
            swipeResponse.setMessage("Invalid Input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(swipeResponse);
        }

        if(swipeDetail.getComment() == null){
            swipeResponse.setMessage("Invalid Input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(swipeResponse);
        }

        swipeResponse.setMessage("swipe ok");
        return ResponseEntity.status(HttpStatus.CREATED).body(swipeResponse);
    }
}
