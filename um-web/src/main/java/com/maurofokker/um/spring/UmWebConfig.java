package com.maurofokker.um.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan({ "com.maurofokker.um.web" })
@EnableWebMvc
public class UmWebConfig extends WebMvcConfigurerAdapter {
    /***
     * Notes:
     * - Extends WebMvcConfigurerAdapter basically allows us to hook into the web configuration of Spring and start making changes
     *
     */
    public UmWebConfig() {
        super();
    }


    // start configuring and tuning the HTTP message converters
    // Overwrite extendMessageConverters method designed to allow us to extend and tune these converters
    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {

        // find jackson converter for marshalling and unmarshalling
        /*
        Optional<HttpMessageConverter<?>> converterFound = converters.stream()
                .filter(c -> c instanceof AbstractJackson2HttpMessageConverter).findFirst();

        if (converterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) converterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); // pretty print output
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // not accept unknown props
        }
        */
    }
}
