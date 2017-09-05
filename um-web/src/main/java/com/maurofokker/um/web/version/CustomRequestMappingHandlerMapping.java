package com.maurofokker.um.web.version;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo.BuilderConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        final Version typeAnnotation = AnnotationUtils.findAnnotation(handlerType, Version.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        final Version methodAnnotation = AnnotationUtils.findAnnotation(method, Version.class);
        return createCondition(methodAnnotation);
    }

    @Override
    protected RequestMappingInfo createRequestMappingInfo(RequestMapping requestMapping, RequestCondition<?> customCondition) {
        String[] resultPath = requestMapping.path();
        if ((customCondition != null) && (customCondition instanceof VersionRequestCondition)) {
            final ArrayList<String> newPath = new ArrayList<String>();
            final Set<Double> versions = ((VersionRequestCondition) customCondition).getVersions();
            for (final String old : resultPath) {
                newPath.add(old);
                for (final double version : versions) {
                    newPath.add("/v" + version + old);
                }
            }
            resultPath = newPath.toArray(resultPath);
        }
        return RequestMappingInfo.paths(resolveEmbeddedValuesInPatterns(resultPath)).methods(requestMapping.method()).params(requestMapping.params()).headers(requestMapping.headers()).consumes(requestMapping.consumes()).produces(requestMapping.produces())
                .mappingName(requestMapping.name()).customCondition(customCondition).options(this.config).build();
    }

    private RequestCondition<?> createCondition(Version apiVersion) {
        if (apiVersion != null) {
            return createVersionRequestCondition(apiVersion.value());
        }
        return null;
    }

    private RequestCondition<?> createVersionRequestCondition(double[] value) {
        return new VersionRequestCondition(Arrays.stream(value).boxed().collect(Collectors.toSet()));

    }
}