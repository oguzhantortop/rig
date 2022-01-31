package com.oguzhan.rig.service;

import com.oguzhan.rig.dao.Customer;
import com.oguzhan.rig.dto.CustomerDto;
import com.oguzhan.rig.dto.ErrorResponse;
import com.oguzhan.rig.exception.BusinessException;
import com.oguzhan.rig.exception.ResourceNotFound;
import com.oguzhan.rig.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto getCustomer(Long customerId) throws Exception {
        return mapToCustomerDTO(getCustomerEntity(customerId));
    }

    public Customer getCustomerEntity(Long customerId) throws Exception {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent())
            return customer.get();
        else {
            ErrorResponse errorResponse = new ErrorResponse("CUST_ERR_002","Customer with given id does not exists!");
            throw new ResourceNotFound(errorResponse);
        }
    }

    public Customer getCustomerByEmail(String email) {
        List<Customer> clist = customerRepository.findByEmail(email);
        if(!clist.isEmpty()) {
            return clist.get(0);
        }
        return null;
    }

    public void deleteCustomer(Long customerId) throws Exception {
        validateCustomerExistence(customerId);
        customerRepository.deleteById(customerId);
    }

    public CustomerDto createCustomer(CustomerDto customer) throws Exception {
        validateCreateCustomer(customer);
        Customer customerEntity = customerRepository.save(mapToCustomerEntity(customer));
        return mapToCustomerDTO(customerEntity);
    }

    public CustomerDto updateCustomer(CustomerDto customer, Long customerId) throws Exception {
        validateCustomerExistence(customerId);
        Customer customerEntity = mapToCustomerEntity(customer);
        customerEntity.setId(customerId);
        customerEntity = customerRepository.save(customerEntity);
        return mapToCustomerDTO(customerEntity);
    }

    private void validateCustomerExistence(Long customerId) throws Exception {
        if(!isUserExists(customerId)) {
            ErrorResponse errorResponse = new ErrorResponse("CUST_ERR_002","Customer with given id does not exists!");
            throw new ResourceNotFound(errorResponse);
        }
    }

    private boolean isUserExists(Long customerId) {
        Optional<Customer> user = customerRepository.findById(customerId);
        if(!user.isPresent()) {
            return false;
        }
        return true;
    }

    private void validateCreateCustomer(CustomerDto customer) throws Exception {
        if(isEmailExists(customer)) {
            ErrorResponse errorResponse = new ErrorResponse("CUST_ERR_001","Customer with given email already exists!");
            throw new BusinessException(errorResponse);
        }
    }

    private boolean isEmailExists(CustomerDto customer) {
        List<Customer> clist = customerRepository.findByEmail(customer.getEmail());
        if(!clist.isEmpty()) {
            return true;
        }
        return false;
    }


    private Customer mapToCustomerEntity(CustomerDto customer) throws Exception {
        Customer c = new Customer();
        if(customer.getName()!=null)
            c.setName(customer.getName());
        if(customer.getSurname()!=null)
            c.setSurname(customer.getSurname());
        if(customer.getEmail()!=null)
            c.setEmail(customer.getEmail());
        if(customer.getPassword()!=null)
            c.setPassword(customer.getPassword());
        return c;
    }

    private CustomerDto mapToCustomerDTO(Customer customer) throws Exception {
        CustomerDto c = new CustomerDto();
        if(customer.getId()!=null)
            c.setId(customer.getId());
        if(customer.getName()!=null)
            c.setName(customer.getName());
        if(customer.getSurname()!=null)
            c.setSurname(customer.getSurname());
        if(customer.getEmail()!=null)
            c.setEmail(customer.getEmail());
        if(customer.getPassword()!=null)
            c.setPassword(customer.getPassword());
        return c;
    }

}
