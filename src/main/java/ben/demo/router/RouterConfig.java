package ben.demo.router;

import ben.demo.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler handler;

    // http://127.0.0.1:8080/router/
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers", handler::getCustomersStreams)
                .GET("/router/customersNow", handler::getCustomersStreamsNow)
                .POST("/router/customer/save", handler::saveNewCustomer)
                .GET("/router/customer/read/{id}", handler::getCustomerById)
                .GET("/router/customer/readAndMap/{id}", handler::getCustomerByIdAndMap)
                .build();
    }
}
