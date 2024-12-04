package stanl_2.final_backend.domain.product.command.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {
}
