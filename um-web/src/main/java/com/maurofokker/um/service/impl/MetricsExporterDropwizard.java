package com.maurofokker.um.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.SortedMap;
*/
//@Component
final class MetricsExporterDropwizard {

    /*
    private Logger logger = LoggerFactory.getLogger(getClass());

    // commented in order to use ActuatorMetricService or we have error
    // No qualifying bean of type 'org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics' available: expected single matching bean but found 2: dropwizardPublicMetrics,metricReaderPublicMetrics
    @Autowired
    private MetricRegistry metricRegistry;

    @Scheduled(fixedRate = 1000 * 30) // every 30 second
    public void exportMetrics() {
        final SortedMap<String, Counter> counters = metricRegistry.getCounters();
        final SortedMap<String, Gauge> gauges = metricRegistry.getGauges();
        counters.forEach(this::log);

    }

    private void log(String counterName, final Counter m) {
        logger.info("Reporting metric {}={}", counterName, m.getCount());
    }
    */
}