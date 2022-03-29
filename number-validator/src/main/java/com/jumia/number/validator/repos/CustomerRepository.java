package com.jumia.number.validator.repos;

import com.jumia.number.validator.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, CustomCustomerRepository {
}
