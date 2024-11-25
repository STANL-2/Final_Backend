package stanl_2.final_backend.domain.product.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductSelectIdDTO {
    private String productId;
    private String name;
    private String serialNumber;
    private String cost;
    private String stock;
    private String imageUrl;

    /* 설명. product option 받아올 필드 */
    private Character country;
    private Character manufacturer;
    private Character vehicleType;
    private Character chassis;
    private Character detailType;
    private Character bodyType;
    private Character safetyDevice;
    private Character engineCapacity;
    private Character secretCode;
    private Character productYear;
    private Character productPlant;
    private Character engine;
    private Character mission;
    private Character trim;
    private Character externalColor;
    private Character internalColor;
    private Character headUpDisplay;
    private Character navigation;
    private Character driveWise;
    private Character smartConnect;
    private Character style;
    private Character myComfortPackage;
    private Character outdoorPackage;
    private Character sunRoof;
    private Character sound;
}
