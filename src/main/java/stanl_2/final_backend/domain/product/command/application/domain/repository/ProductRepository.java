package stanl_2.final_backend.domain.product.command.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
