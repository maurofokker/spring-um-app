package com.maurofokker.um.web;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.maurofokker.um.persistence.model.Principal;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.persistence.model.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import java.io.IOException;

/**
 * An {@code HttpMessageConverter} that can read and write Kryo messages.
 */
public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final MediaType KRYO = new MediaType("application", "x-kryo");

    /**
     * We're using a single instance of Kryo here, which means that multiple threads will naturally be using this single instance.
     * However, what's critical to understand is that the Kryo is not thread safe.
     * And so we'll use a thread local here to have a Kryo instance per thread
     *
     * we can access the instance out of this thread local -> kryoThreadLocal.get()
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };

    public KryoHttpMessageConverter() {
        super(KRYO);
    }

    @Override
    protected boolean supports(final Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(final Class<? extends Object> clazz, final HttpInputMessage inputMessage) throws IOException {
        final Input input = new Input(inputMessage.getBody());
        return kryoThreadLocal.get().readClassAndObject(input);
    }

    @Override
    protected void writeInternal(final Object object, final HttpOutputMessage outputMessage) throws IOException {
        final Output output = new Output(outputMessage.getBody()); // wrap in Output object
        kryoThreadLocal.get().writeClassAndObject(output, object);
        output.flush(); // flush response
    }

    @Override
    protected MediaType getDefaultContentType(final Object object) {
        return KRYO;
    }
    
    //
    
    private static final Kryo createKryo(){
        final Kryo kryo = new Kryo();
        kryo.register(User.class, 1);
        kryo.register(Role.class, 2);
        kryo.register(Privilege.class, 3);
        kryo.register(Principal.class, 4);

        return kryo;
    }
    
}
