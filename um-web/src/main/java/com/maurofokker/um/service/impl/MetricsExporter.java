package com.maurofokker.um.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.buffer.BufferMetricReader;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Simple metrics exporter that export data into an external system
 * The external system will be the logger in this case, the data will be exported into a JSON-enabled logger
 * There are a lot of systems that can pick up logs and can transport those into metric systems such as
 *  - Graphite
 *  - DLA
 *  - ELK Stack
 *  - ElasticSearch
 *  - Logstack
 *  - Kibana
 */
//@Component
final class MetricsExporter {

    // in this case the logger will be the external system
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BufferMetricReader metricReader; // replace MetricRepository to access the metrics

    @Autowired
    private CounterService counterService; // to reset the metrics

    @Scheduled(fixedRate = 1000 * 30) // runs every 30 seconds pretty close to real time
    public void exportMetrics() {
        metricReader.findAll().forEach(this::log); // iterate over metrics data and logging the data through the logger
    }

    private void log(final Metric<?> m) {
        logger.info("Reporting metric {}={}", m.getName(), m.getValue());
        counterService.reset(m.getName());
    }
}