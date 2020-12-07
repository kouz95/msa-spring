package com.kouz.util.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceUtil {
    private final String port;
    private final Optional<String> serviceAddress = Optional.empty();

    public ServiceUtil(@Value("${server.port}") String port) {
        this.port = port;
    }

    public String getServiceAddress() {
        return serviceAddress
                .orElseGet(() -> findMyHostName() + "/" + findMyIpAddress() + ":" + port);
    }

    private String findMyHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown host name";
        }
    }

    private String findMyIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown IP address";
        }
    }
}
