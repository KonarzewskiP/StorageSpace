package com.storage.service;

import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeDetails;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.repositories.WarehouseRepository;
import com.storage.utils.mapper.WarehouseMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    private static final String NAME = "Orange";
    private static final String CITY = "London";
    private static final String POSTCODE = "AB1 0AA";
    private static final String STREET = "57 Walcot Square";
    private static final float LAT = 51.4943f;
    private static final float LNG = -0.1089f;
    private static final BigDecimal TOTAL_RENTAL_AREA_IN_M_2 = new BigDecimal("1000.00");

    @InjectMocks
    private WarehouseService underTest;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Spy
    private WarehouseMapper warehouseMapper = new WarehouseMapper(new ModelMapper());
    @Mock
    private PostcodeService postcodeService;
    @Captor
    private ArgumentCaptor<Warehouse> warehouseArgumentCaptor;

    @Nested
    class CreateTest {

        @Test
        void itShouldCreateWithoutPostcodeDetails() {
            //Given
            CreateWarehouseRequest req = new CreateWarehouseRequest(NAME, CITY, POSTCODE, STREET, TOTAL_RENTAL_AREA_IN_M_2);
            //... returned mocked warehouse after save to successfully complete test
            given(warehouseRepository.save(any())).willReturn(new Warehouse());
            //When
            underTest.create(req);
            //Then
            then(warehouseRepository).should(times(1)).save(warehouseArgumentCaptor.capture());
            Warehouse savedWarehouse = warehouseArgumentCaptor.getValue();

            assertThat(savedWarehouse).isNotNull();
            assertAll(
                    () -> assertThat(savedWarehouse.getUuid()).isNotBlank(),
                    () -> assertThat(savedWarehouse.getName()).isEqualTo(NAME),
                    () -> assertThat(savedWarehouse.getCity()).isEqualTo(CITY),
                    () -> assertThat(savedWarehouse.getStreet()).isEqualTo(STREET),
                    () -> assertThat(savedWarehouse.getTotalRentalAreaInM2()).isEqualTo(TOTAL_RENTAL_AREA_IN_M_2),
                    () -> assertThat(savedWarehouse.getPostcode()).isEqualTo(POSTCODE),
                    () -> assertThat(savedWarehouse.getLat()).isEqualTo(0f),
                    () -> assertThat(savedWarehouse.getLng()).isEqualTo(0f)
            );
        }

        @Test
        void itShouldCreateNewWarehouse() {
            //Given
            CreateWarehouseRequest req = new CreateWarehouseRequest(NAME, CITY, POSTCODE, STREET, TOTAL_RENTAL_AREA_IN_M_2);
            // ... return details for the postcode
            PostcodeDetails postcodeDetails = PostcodeDetails.builder()
                    .postcode(POSTCODE)
                    .lat(LAT)
                    .lng(LNG)
                    .build();
            PostcodeDTO postcodeDTO = PostcodeDTO.builder()
                    .result(postcodeDetails)
                    .build();
            given(postcodeService.getDetails(req.postcode())).willReturn(postcodeDTO);
            //... returned mocked warehouse after save to successfully complete test
            given(warehouseRepository.save(any())).willReturn(new Warehouse());
            //When
            underTest.create(req);
            //Then
            then(warehouseRepository).should(times(1)).save(warehouseArgumentCaptor.capture());
            Warehouse savedWarehouse = warehouseArgumentCaptor.getValue();

            assertThat(savedWarehouse).isNotNull();
            assertAll(
                    () -> assertThat(savedWarehouse.getUuid()).isNotBlank(),
                    () -> assertThat(savedWarehouse.getName()).isEqualTo(NAME),
                    () -> assertThat(savedWarehouse.getCity()).isEqualTo(CITY),
                    () -> assertThat(savedWarehouse.getStreet()).isEqualTo(STREET),
                    () -> assertThat(savedWarehouse.getTotalRentalAreaInM2()).isEqualTo(TOTAL_RENTAL_AREA_IN_M_2),
                    () -> assertThat(savedWarehouse.getPostcode()).isEqualTo(POSTCODE),
                    () -> assertThat(savedWarehouse.getLat()).isEqualTo(LAT),
                    () -> assertThat(savedWarehouse.getLng()).isEqualTo(LNG)
            );
        }
    }

    @Nested
    class GetSortedByDistanceFromPostcodeTest {
        @Test
        void itShouldReturnListOfUnsortedWarehousesWhenCantGetPostcodeDetails() {
            //Given
            String postcode = "WC2R3DX";
            // ... return list of unsorted warehouses from DB
            Warehouse warehouseA = Warehouse.builder()
                    .uuid("AAA")
                    .lat(51.4943f)
                    .lng(-0.1089f)
                    .postcode("SE11 4UB")
                    .build();
            Warehouse warehouseB = Warehouse.builder()
                    .uuid("BBB")
                    .lat(51.4627f)
                    .lng(-0.169f)
                    .postcode("SW11 1PL")
                    .build();
            Warehouse warehouseC = Warehouse.builder()
                    .uuid("CCC")
                    .lat(51.5058f)
                    .lng(-0.1535f)
                    .postcode("W1K 1AB")
                    .build();
            List<Warehouse> unsortedWarehouses = List.of(warehouseA, warehouseB, warehouseC);
            given(warehouseRepository.findAll()).willReturn(unsortedWarehouses);
            //When
            List<WarehouseDto> result = underTest.getSortedByDistanceFromPostcode(postcode);
            //Then
            assertThat(result).isNotEmpty();
            assertAll(
                    () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(warehouseA),
                    () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(warehouseB),
                    () -> assertThat(result.get(2)).usingRecursiveComparison().isEqualTo(warehouseC)
            );
        }

        @Test
        void itShouldReturnListOfSortedWarehousesByDistanceToPostcode() {
            //Given
            String postcode = "WC2R3DX";
            // ... return list of unsorted warehouses from DB
            Warehouse warehouseA = Warehouse.builder()
                    .uuid("AAA")
                    .lat(51.4943f)
                    .lng(-0.1089f)
                    .postcode("SE11 4UB")
                    .build();
            Warehouse warehouseB = Warehouse.builder()
                    .uuid("BBB")
                    .lat(51.4627f)
                    .lng(-0.169f)
                    .postcode("SW11 1PL")
                    .build();
            Warehouse warehouseC = Warehouse.builder()
                    .uuid("CCC")
                    .lat(51.5058f)
                    .lng(-0.1535f)
                    .postcode("W1K 1AB")
                    .build();
            List<Warehouse> unsortedWarehouses = List.of(warehouseA, warehouseB, warehouseC);
            given(warehouseRepository.findAll()).willReturn(unsortedWarehouses);
            // ... return postcode details with latitude and longitude
            PostcodeDetails postcodeDetails = PostcodeDetails.builder()
                    .postcode(postcode)
                    .lat(51.5115f)
                    .lng(-0.1138f)
                    .build();
            PostcodeDTO postcodeDTO = PostcodeDTO.builder()
                    .result(postcodeDetails)
                    .build();
            given(postcodeService.getDetails(postcode)).willReturn(postcodeDTO);
            //When
            List<WarehouseDto> result = underTest.getSortedByDistanceFromPostcode(postcode);
            //Then
            assertThat(result).isNotEmpty();
            assertAll(
                    () -> assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(warehouseA),
                    () -> assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(warehouseC),
                    () -> assertThat(result.get(2)).usingRecursiveComparison().isEqualTo(warehouseB)
            );
        }
    }
}















