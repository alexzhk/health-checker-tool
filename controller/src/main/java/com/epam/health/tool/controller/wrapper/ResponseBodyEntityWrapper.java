package com.epam.health.tool.controller.wrapper;

import com.epam.facade.model.projection.impl.ClusterEntityProjectionImpl;

//Use for POST/PUT queries
public class ResponseBodyEntityWrapper {
    private ClusterEntityProjectionImpl cluster;

    public ClusterEntityProjectionImpl getCluster() {
        return cluster;
    }

    public void setCluster(ClusterEntityProjectionImpl cluster) {
        this.cluster = cluster;
    }
}
