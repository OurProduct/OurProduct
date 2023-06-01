package com.example.authservice.rsocketclient;

import com.example.authservice.model.ServiceType;
import io.rsocket.loadbalance.LoadbalanceTarget;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class RSocketClientRequester {
    protected ServiceType serviceType = ServiceType.NONE;
    protected List<LoadbalanceTarget> loadBalanceTarget = new ArrayList<>();

    public abstract void refreshRequester();

    public List<LoadbalanceTarget> getLoadBalanceTarget() {
        return loadBalanceTarget;
    }

    public void setLoadBalanceTarget(List<LoadbalanceTarget> loadBalanceTarget) {
        this.loadBalanceTarget = loadBalanceTarget;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
