package v5.gidd.service;

import org.springframework.data.jpa.repository.JpaRepository;
import v5.gidd.entities.Inspectable;

import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import java.util.function.*;


/**
 * Service superclass providing generic tools to interact with various interactors.
 * Services implementing this structure should to their best effort interact through
 * these methods to isolate their responsibility, which is:
 *
 *      1. Accept any object which may be inspected for invalid inputs.
 *      2. Forward payload through necessary validation checks. Every object which implements inspectable
 *         must ensure proper checks of each field. If such field is also an object, then each field composed in that
 *         object must also be inspected. Best way to accomplish this is by requiring fields to also extend Inspectable.
 *         Inspection of object should throw exception on invalid data, or return void (do nothing)
 *         if everything checks out. Exceptions MUST NOT be caught locally by the object. It should propagate and
 *         be handled in this method.
 *      3. Relay valid data to interactor. Interactor may also throw exceptions if necessary.
 *      4. Return success response, or a failure report specifying whatever went wrong.
 *
 * @param <T> Type of repo to interact with.
 */


public abstract class Service<T extends JpaRepository<?,?>> {

    /**
     * Repository object composed in sub-class. Is used extensively in the following generic API
     * to pass repo as implicit argument. This is merely to implemented so sub-classes can
     * call functional interfaces by their method reference instead of functional literals (lambda).
     * Strictly speaking, only sugar to make code more readable, but not necessary.
     * @return Service belonging to sub-class.
     */

    protected abstract T getRepo();


    /**
     * Generic message wrapper. Accepts arguments, and relays it to provided method. Whatever is returned
     * will be wrapped in a {@link Message} with ok status. If data validation detects malformed data, or any
     * interactor fails fulfilling client request, they may throw a GiddException to indicate fail request.
     * This exception will also be transmitted back to client agent, indicating exactly what failed.
     * @param request Any argument which is instance
     * @param action Functional interface to invoke.
     * @param <A> Type of argument to pass.
     * @param <B> Type of response returned.
     * @return The returned Message.
     */

    /*
    Best suitable for methods which comply which this signature: foo(repo: T, request: A): B
    Practical use case:

    query(user, UserInteractor::registerUser)
     */

    <A extends Inspectable,B> Message<B> query(A request, BiFunction<T,A,B> action){
        B response;
        try {
            request.inspect();
            response = action.apply(getRepo(), request);
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", response);
    }

    /**
     * Generic message wrapper. Whatever is returned
     * will be wrapped in a {@link Message} with ok status. If data validation detects malformed data, or any
     * interactor fails fulfilling client request, they may throw a GiddException to indicate fail request.
     * This exception will also be transmitted back to client agent, indicating exactly what failed.
     * @param action Functional interface to invoke.
     * @param <B> Type of response returned.
     * @return The returned Message.
     */

    /*
    Overloaded query method suitable for this signature: foo(repo: T): B
    i.e., whenever no request argument is required. Use case:

    query(user, UserInteractor::getAllUsers)
     */

    <B> Message<B> query(Function<T,B> action){
        B response;
        try {
            response = action.apply(getRepo());
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", response);
    }

    /**
     * Generic message wrapper. Accepts arguments, and relays it to provided method. Whatever is returned
     * will be wrapped in a {@link Message} with ok status. If data validation detects malformed data, or any
     * interactor fails fulfilling client request, they may throw a GiddException to indicate fail request.
     * This exception will also be transmitted back to client agent, indicating exactly what failed.
     * @param request Any argument which is instance
     * @param action Functional interface to invoke.
     * @param <A> Type of argument to pass.
     * @param <B> Type of response returned.
     * @return The returned Message.
     */

    /*
    Equivalent, to non-static methods, except for the repo argument which is not automatically provided.
    Suits methods of this signature: foo(request: A): B
     */

    static <A extends Inspectable,B> Message<B> query(A request, Function<A,B> action){
        B response;
        try {
            request.inspect();
            response = action.apply(request);
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", response);
    }

    /**
     * Generic message wrapper. Accepts arguments, and relays it to provided method. Whenever no exception is
     * thrown from invoked interface, a {@link Message} of Void with ok status will be returned.
     * If data validation detects malformed data, or any
     * interactor fails fulfilling client request, they may throw a GiddException to indicate fail request.
     * This exception will also be transmitted back to client agent, indicating exactly what failed.
     * @param request Any argument which is instance
     * @param action Functional interface to invoke.
     * @param <A> Type of argument to pass.
     * @param <B> Type of response returned.
     * @return The returned Message.
     */

    /*
    Same use case as execute() which demands consumers as argument. Only different is the BiConsumer argument
    which provides instances of this class's repo. The reason for this is merely to allow use of method references
    when invoking interactor methods.
    Can write this:
        "Interactor::registerEntity"
    Instead of this:
        "entity -> Interactor.registerEntity(this.getRepo(), entity)
     */

    <A extends Inspectable,B> Message<B> execute(A request, BiConsumer<T,A> action){
        try {
            request.inspect();
            action.accept(getRepo(), request);
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", null);
    }

    /**
     * Generic message wrapper. Whenever no exception is
     * thrown from invoked interface, a {@link Message} of Void with ok status will be returned.
     * If data validation detects malformed data, or any
     * interactor fails fulfilling client request, they may throw a GiddException to indicate fail request.
     * This exception will also be transmitted back to client agent, indicating exactly what failed.
     * @param request Any argument which is instance
     * @param action Functional interface to invoke.
     * @param <A> Type of argument to pass.
     * @return The returned Message.
     */

     /*
    Overloaded method of Service#resolve(request: Message<A>, action: Function<A,B>): Message<B>
    Unlike it's counterpart, this method expects no return value from the invoked domain, but will still
    validate data integrity. Exception are caught and relayed back. The same for successes too.
     */

    static <A extends Inspectable> Message<Void> execute(A request, Consumer<A> action){
        try {
            request.inspect();
            action.accept(request);
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", null);
    }


    static <A extends Inspectable, B extends Inspectable> Message<Void> execute(A arg0, B arg1, BiConsumer<A,B> action){
        try {
            arg0.inspect();
            arg1.inspect();
            action.accept(arg0, arg1);
        }
        catch (GiddException exception) {
            return exception.toMessage();
        }
        return new Message<>(Status.Ok, "Ok", null);
    }
}
