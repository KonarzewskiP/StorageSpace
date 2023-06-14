package com.storage.controllers;

import com.storage.models.Price;
import com.storage.models.dto.PriceDTO;
import com.storage.models.requests.CreatePriceRequest;
import com.storage.service.PriceService;
import com.storage.utils.mapper.PriceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/price")
public class PriceController {

    private final PriceService priceService;
    private final PriceMapper priceMapper;

    @PostMapping
    public ResponseEntity<PriceDTO> create(@RequestBody @Valid CreatePriceRequest priceRequest) {
        log.info("Create price with req: [{}] ", priceRequest);
        PriceDTO priceDTO = priceService.create(priceRequest);
        return new ResponseEntity<>(priceDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PriceDTO> getByUuid(@PathVariable String uuid) {
        log.info("Get Price by [UUID:{}] ", uuid);
        Price price = priceService.findByUuid(uuid);
        PriceDTO priceDTO = priceMapper.map(price);
        return new ResponseEntity<>(priceDTO, HttpStatus.OK);
    }

    @GetMapping("/warehouse/{uuid}")
    public ResponseEntity<Page<PriceDTO>> getAll(@PathVariable String uuid, Pageable pageable) {
        log.info("Get All Price by Warehouse UUID: {}", uuid);
        Page<PriceDTO> allWarehouses = priceService.getAll(uuid, pageable);
        return new ResponseEntity<>(allWarehouses, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Integer> deleteByUuid(@PathVariable String uuid) {
        log.info("Delete Price by UUID: {}", uuid);
        int numberOfDeletedRooms = priceService.deleteByUuid(uuid);
        return new ResponseEntity<>(numberOfDeletedRooms, HttpStatus.OK);
    }
}
