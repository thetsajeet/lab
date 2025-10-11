package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    CustomerDTO getCustomerById(UUID id);
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);
    void updateCustomer(UUID customerId, CustomerDTO customerDTO);
    void deleteById(UUID customerId);
}
