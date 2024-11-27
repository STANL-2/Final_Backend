package stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

@Entity
@Table(name="TB_PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "PRO")
    )
    @Column(name ="PROD_ID")
    private String productId;

    @Column(name ="PROD_SER_NO", nullable = false)
    private String serialNumber;

    @Column(name ="PROD_COST", nullable = false)
    private Integer cost;

    @Column(name ="PROD_NAME", nullable = false)
    private String name;

    @Column(name ="PROD_STCK", nullable = false)
    private Integer stock;

    @Column(name ="CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name ="UPDATED_AT", nullable = false)
    private String updatedAt;

    @Column(name ="DELETED_AT")
    private String deletedAt;

    @Column(name ="ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name ="IMAGE_URL", nullable = false)
    private String imageUrl;
}
