package ben.demo.dao;

import ben.demo.dto.CustomerDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    private int amount = 20;

    private static void sleep1(int j) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<CustomerDto> getCustomers() {
        return IntStream
                .rangeClosed(1, amount)
                .peek(CustomerDao::sleep1)
                .peek(System.out::println)
                .mapToObj(i -> new CustomerDto(i, "Customer" + i))
                .collect(Collectors.toList());
    }


    public Flux<CustomerDto> getCustomersStreams() {
        return Flux
                .range(1,amount)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println)
                .map(i -> new CustomerDto(i, "customer" + i));
    }

    public Flux<CustomerDto> getCustomersStreamsNow() {
        return Flux
                .range(1,amount)
                .doOnNext(System.out::println)
                .map(i -> new CustomerDto(i, "customer" + i));
    }

}
