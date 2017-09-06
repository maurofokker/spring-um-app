package com.maurofokker.common.metric.web;

import com.maurofokker.common.metric.service.IActuatorMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class MetricController {

    @Autowired
    private IActuatorMetricService actuatorMetricService;

    //

    @RequestMapping(value = "/metric-graph-data", method = RequestMethod.GET)
    @ResponseBody
    public Object[][] getActuatorMetricData() {
        return actuatorMetricService.getGraphData();
    }

}
