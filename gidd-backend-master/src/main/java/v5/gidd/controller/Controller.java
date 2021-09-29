package v5.gidd.controller;


import org.springframework.web.bind.annotation.RestController;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.message.Message;

import org.springframework.http.ResponseEntity;
import v5.gidd.entities.message.Message;
import v5.gidd.service.Service;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Controller superclass providing generic tools to interact with the service layer.
 * Controllers implementing this structure should to their best effort interact through
 * these methods to isolate their responsibility, which is:
 *
 * 1. Relay client queries to service.
 * 2. Wrap service responses in EntityResponses and set the appropriate status-code depending on payload information.
 * 3. Return properly formatted http-response to client.
 *
 * @param <T> Type of service to interact with.
 */

@RestController
abstract class Controller<T extends Service<?>> {

    /**
     * Service object composed in sub-class. May prove useful for future revisions when this class API
     * depend on the service itself.
     * @return Service belonging to sub-class.
     */

    protected abstract T getService();

    /**
     * Generic entityResponse wrapper. Accepts arguments, and relays it to provided method. Whatever is returned
     * will be wrapped in EntityResponse.
     * @param request Any argument.
     * @param action Any functional interface.
     * @param <A> Type of argument.
     * @param <B> Type of response.
     * @return Response wrapped in EntityResponse.
     */

    static <A,B extends Message<?>> ResponseEntity<B> mapRequest(A request, Function<A,B> action){
        B response = action.apply(request);

        return ResponseEntity
                .status(response.getStatus().statusCode)
                .body(response);
    }

    /**
     * Generic entityResponse wrapper. Accepts arguments, and relays it to provided method. Whatever is returned
     * will be wrapped in EntityResponse.
     * @param arg0 First argument.
     * @param arg1 Second argument.
     * @param action Target functional interface.
     * @param <A> Type of first argument.
     * @param <B> Type of second argument.
     * @param <C> Type of response.
     * @return Response wrapped in EntityResponse.
     */

    static <A,B,C extends Message<?>> ResponseEntity<C> mapRequest(A arg0, B arg1, BiFunction<A,B,C> action){
        C response = action.apply(arg0, arg1);

        return ResponseEntity
                .status(response.getStatus().statusCode)
                .body(response);
    }

    /**
     * Generic entityResponse wrapper. Whatever is returned will be wrapped in EntityResponse.
     * @param action Functional interface to invoke.
     * @param <B> Type of return value.
     * @return Response wrapped in EntityResponse.
     */

    static <B extends Message<?>> ResponseEntity<B> mapRequest(Supplier<B> action){
        B response = action.get();

        return ResponseEntity
                .status(response.getStatus().statusCode)
                .body(response);
    }

    /**
     * Handles queries from client, but yields no response.
     * @param request Argument provided by client.
     * @param action Functional interface to invoke.
     * @param <A> Type of argument.
     */

    static <A> void consumeRequest(A request, Consumer<A> action){
        action.accept(request);
    }
}
