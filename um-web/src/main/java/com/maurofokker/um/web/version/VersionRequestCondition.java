package com.maurofokker.um.web.version;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class VersionRequestCondition implements RequestCondition<VersionRequestCondition> {

    private final Set<Double> versions;

    public VersionRequestCondition(Set<Double> versions) {
        this.versions = versions;
    }

    @Override
    public VersionRequestCondition combine(VersionRequestCondition other) {
        final Set<Double> result = new HashSet<Double>(this.versions);
        result.addAll(other.versions);
        return new VersionRequestCondition(result);
    }

    @Override
    public VersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        try {
            // param
            if ((request.getParameter("v") != null)) {
                if (versions.contains(Double.parseDouble(request.getParameter("v")))) {
                    return this;
                }
            }

            // header
            final String header = request.getHeader("Accept");
            if ((header != null) && header.contains("vnd")) {
                final int start = header.indexOf('v', header.indexOf("vnd") + 1);
                if (start != -1) {
                    final int end = header.indexOf('+', start + 1);
                    final double version = Double.parseDouble(header.substring(start + 1, end));
                    if (versions.contains(version)) {
                        return this;
                    }
                }
            }

            // uri
            final String uri = request.getRequestURI();
            if (uri.contains("/v")) {
                final int start = uri.indexOf("/v");
                final int end = uri.indexOf('/', start + 2);
                final double version = Double.parseDouble(uri.substring(start + 2, end));
                if (versions.contains(version)) {
                    return this;
                }
            }

        } catch (final Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public int compareTo(VersionRequestCondition other, HttpServletRequest request) {
        return other.versions.size() - this.versions.size();
    }

    public Set<Double> getVersions() {
        return versions;
    }

}
