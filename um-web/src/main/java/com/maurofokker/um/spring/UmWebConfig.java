package com.maurofokker.um.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan({ "com.maurofokker.um.web", "com.maurofokker.common.web" })
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

        Optional<HttpMessageConverter<?>> jsonConverterFound = converters.stream()
                .filter(c -> c instanceof AbstractJackson2HttpMessageConverter).findFirst();
        // for json
        if (jsonConverterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) jsonConverterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); // pretty print output
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // not accept unknown props
        }
        // for xml
        Optional<HttpMessageConverter<?>> xmlConverterFound = converters.stream()
                .filter(c -> c instanceof MappingJackson2XmlHttpMessageConverter).findFirst();

        if (xmlConverterFound.isPresent()) {
            final MappingJackson2XmlHttpMessageConverter converter = (MappingJackson2XmlHttpMessageConverter) xmlConverterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); // pretty print output
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // not accept unknown props
        }
    }
}
