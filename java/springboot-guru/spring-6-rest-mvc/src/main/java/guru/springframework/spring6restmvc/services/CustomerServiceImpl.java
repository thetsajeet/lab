package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;
    private CustomerServiceImpl() {
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("CustomerDTO 1")
                .version(1)
                .createdDate(null)
                .updateDate(null)
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("CustomerDTO 2")
                .version(2)
                .createdDate(null)
                .updateDate(null)
                .build();
        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("CustomerDTO 3")
                .version(3)
                .createdDate(null)
                .updateDate(null)
                .build();

        customerMap = Map.of(
                customerDTO1.getId(), customerDTO1,
                customerDTO2.getId(), customerDTO2,
                customerDTO3.getId(), customerDTO3
        );
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerMap.values().stream().toList();
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        CustomerDTO c = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customerDTO.getName())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        customerMap.put(c.getId(), c);
        return c;
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setName(customerDTO.getName());
        existing.setUpdateDate(LocalDateTime.now());
        customerMap.put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }
}
