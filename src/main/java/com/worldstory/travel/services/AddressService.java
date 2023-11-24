package com.worldstory.travel.services;

import com.worldstory.travel.models.Address;
import com.worldstory.travel.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    public List<Address> saveAll(List<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }
}
