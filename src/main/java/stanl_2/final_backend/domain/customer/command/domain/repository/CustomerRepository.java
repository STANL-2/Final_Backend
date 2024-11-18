package stanl_2.final_backend.domain.customer.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.customer.command.domain.aggregate.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
