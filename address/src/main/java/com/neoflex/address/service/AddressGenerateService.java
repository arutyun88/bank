package com.neoflex.address.service;

import com.neoflex.address.model.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class AddressGenerateService {
    private static String url;

    public AddressGenerateService(@Value(value = "${address.generate.url}") String url) {
        AddressGenerateService.url = url;
    }

    public static Address find() {
        Address address = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            address = restTemplate.getForObject(new URI(url), Address.class);
        } catch (ResourceAccessException exception) {
            System.out.println("Server is not found");
            exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Other exception");
        }
        return address;
    }
}
