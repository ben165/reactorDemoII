package ben.demo.handler;

import ben.demo.dao.CustomerDao;
import ben.demo.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> getCustomersStreams(ServerRequest request) {
        Flux<CustomerDto> customersStreams = dao.getCustomersStreams();
        return ServerResponse
                .ok()
                //.contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStreams, CustomerDto.class);
    }


    public Mono<ServerResponse> getCustomersStreamsNow(ServerRequest request) {
        Flux<CustomerDto> customersStreams = dao.getCustomersStreamsNow();
        return ServerResponse
                .ok()
                //.contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStreams, CustomerDto.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        int customerId = Integer.parseInt(request.pathVariable("id"));

        Mono<CustomerDto> monoCustomer = dao
                .getCustomersStreamsNow()
                .filter(c -> c.getId() == customerId)
                .next();

        return ServerResponse
                .ok()
                .body(monoCustomer, CustomerDto.class);
    }

    public Mono<ServerResponse> saveNewCustomer(ServerRequest request) {
        Mono<CustomerDto> monoCustomer = request.bodyToMono(CustomerDto.class);

        return ServerResponse
                .ok()
                .body(monoCustomer, CustomerDto.class);

    }

    public Mono<ServerResponse> getCustomerByIdAndMap(ServerRequest request) {
        int customerId = Integer.parseInt(request.pathVariable("id"));

        Mono<String> monoString = dao
                .getCustomersStreamsNow()
                .filter(x -> x.getId() == customerId)
                .next()
                .map(x -> "ID: " + x.getId() + " with Name: " + x.getName());
        //Mono<String> monoStr = monoCustomer.map(x -> "ID: " + x.getId() + " and " + x.getName());

        return ServerResponse
                .ok()
                .body(monoString, String.class);
    }

}
