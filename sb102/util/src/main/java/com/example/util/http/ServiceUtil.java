package com.example.util.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ComponentScan
public class ServiceUtil {
    private final String port;

    private String serviceAddress = null;

    @Autowired
    public ServiceUtil(@Value("${server.port}") String port){
        this.port = port;
    }

    public String getServiceAddress(){
        if(serviceAddress == null){
            serviceAddress = findMyHostname() + "/" + findMyIdAddress() + ":" + port;
        }
        return  serviceAddress;
    }

    private String findMyHostname(){
        try {
            return InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            return "unknown host name";
        }
    }

    private String findMyIdAddress(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
            return "unknown IP address";
        }
    }
}
