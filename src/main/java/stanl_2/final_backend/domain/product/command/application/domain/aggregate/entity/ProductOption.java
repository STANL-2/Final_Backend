package stanl_2.final_backend.domain.product.command.application.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import stanl_2.final_backend.global.config.PrefixGeneratorConfig;

@Entity
@Table(name="TB_PRODUCT_OPTION")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductOption {

    @Id  // @Id를 명시적으로 선언합니다.
    @GeneratedValue(generator = "PrefixGeneratorConfig")
    @GenericGenerator(name = "PrefixGeneratorConfig",
            type = PrefixGeneratorConfig.class,
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "PRO")
    )
    @Column(name ="PROD_ID")
    private String productId;  // 옵션을 위한 식별자 필드

    @Column(name ="OPT_CNTY", nullable = false)
    private Character country;

    @Column(name ="OPT_MNFR", nullable = false)
    private Character manufacturer;

    @Column(name ="OPT_VHC_TYPE", nullable = false)
    private Character vehicleType;

    @Column(name ="OPT_CHSS", nullable = false)
    private Character chassis;

    @Column(name ="OPT_DTIL_TYPE", nullable = false)
    private Character detailType;

    @Column(name ="OPT_BODY_TYPE", nullable = false)
    private Character bodyType;

    @Column(name ="OPT_SFTY_DVCE", nullable = false)
    private Character safetyDevice;

    @Column(name ="OPT_ENGN_CPCT", nullable = false)
    private Character engineCapacity;

    @Column(name ="OPT_SCRT_CODE", nullable = false)
    private Character secretCode;

    @Column(name ="OPT_PRDC_YEAR", nullable = false)
    private Character productYear;

    @Column(name ="OPT_PRDC_PLNT", nullable = false)
    private Character productPlant;

    @Column(name ="OPT_ENGN", nullable = false)
    private Character engine;

    @Column(name ="OPT_MSSN", nullable = false)
    private Character mission;

    @Column(name ="OPT_TRIM", nullable = false)
    private Character trim;

    @Column(name ="OPT_XTNL_COLR", nullable = false)
    private Character externalColor;

    @Column(name ="OPT_ITNL_COLR", nullable = false)
    private Character internalColor;

    @Column(name ="OPT_HUD", nullable = false)
    private Character headUpDisplay;

    @Column(name ="OPT_NAVI", nullable = false)
    private Character navigation;

    @Column(name ="OPT_DRVE_WISE", nullable = false)
    private Character driveWise;

    @Column(name ="OPT_SMRT_CNCT", nullable = false)
    private Character smartConnect;

    @Column(name ="OPT_STYL", nullable = false)
    private Character style;

    @Column(name ="OPT_MY_CFRT_PCKG", nullable = false)
    private Character myComfortPackage;

    @Column(name ="OPT_OTDR_PCKG", nullable = false)
    private Character outdoorPackage;

    @Column(name ="OPT_SUN_ROOF", nullable = false)
    private Character sunRoof;

    @Column(name ="OPT_SOND", nullable = false)
    private Character sound;

    @Column(name ="ACTIVE")
    private Boolean active = true;

    public ProductOption(Character country,
                         Character manufacturer,
                         Character vehicleType,
                         Character chassis,
                         Character detailType,
                         Character bodyType,
                         Character safetyDevice,
                         Character engineCapacity,
                         Character secretCode,
                         Character productYear,
                         Character productPlant,
                         Character engine,
                         Character mission,
                         Character trim,
                         Character externalColor,
                         Character internalColor,
                         Character headUpDisplay,
                         Character navigation,
                         Character driveWise,
                         Character smartConnect,
                         Character style,
                         Character myComfortPackage,
                         Character outdoorPackage,
                         Character sunRoof,
                         Character sound) {

        this.country = country;
        this.manufacturer = manufacturer;
        this.vehicleType = vehicleType;
        this.chassis = chassis;
        this.detailType = detailType;
        this.bodyType = bodyType;
        this.safetyDevice = safetyDevice;
        this.engineCapacity = engineCapacity;
        this.secretCode = secretCode;
        this.productYear = productYear;
        this.productPlant = productPlant;
        this.engine = engine;
        this.mission = mission;
        this.trim = trim;
        this.externalColor = externalColor;
        this.internalColor = internalColor;
        this.headUpDisplay = headUpDisplay;
        this.navigation = navigation;
        this.driveWise = driveWise;
        this.smartConnect = smartConnect;
        this.style = style;
        this.myComfortPackage = myComfortPackage;
        this.outdoorPackage = outdoorPackage;
        this.sunRoof = sunRoof;
        this.sound = sound;
    }
}
