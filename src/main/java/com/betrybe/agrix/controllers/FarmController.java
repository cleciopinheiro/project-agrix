package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller.
 */
@Controller
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Create.
   */
  @PostMapping
  public ResponseEntity<FarmDto> create(@RequestBody FarmDto farmDto) {
    Farm farm = farmService.create(farmDto.toFarm());
    FarmDto newFarmDto = new FarmDto(farm.getId(), farm.getName(), farm.getSize());
    return new ResponseEntity<>(newFarmDto, HttpStatus.CREATED);
  }

  /**
   * getAll.
   */
  @GetMapping
  public ResponseEntity<List<FarmDto>> getAll() {
    List<Farm> farms = farmService.getAllFarms();
    List<FarmDto> farmDtoList = farms.stream()
        .map(farm -> new FarmDto(farm.getId(), farm.getName(), farm.getSize()))
        .toList();
    return ResponseEntity.ok(farmDtoList);
  }

  /**
   * getFarmById.
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<FarmDto> getFarmById(@PathVariable Long id) {
    Farm farm = farmService.getFarmById(id);
    FarmDto farmDto = new FarmDto(farm.getId(), farm.getName(), farm.getSize());
    return ResponseEntity.ok(farmDto);
  }

  /**
   * createCrop.
   */
  @PostMapping(value = "/{farmId}/crops")
  public ResponseEntity<CropDto> createCrop(@PathVariable Long farmId, 
      @RequestBody CropDto cropDto) {
    Crop crop = farmService.createCrop(farmId, cropDto.toEntity());
    CropDto newCropDto = new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(), 
        farmId, crop.getPlantedDate(), crop.getHarvestDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(newCropDto);
  }

  /**
   * getCropsByFarmId.
   */
  @GetMapping(value = "/{farmId}/crops")
  public ResponseEntity<List<CropDto>> getCropsByFarmId(@PathVariable Long farmId) {
    List<Crop> crops = farmService.getCropsById(farmId);
    List<CropDto> cropDtoList = crops.stream()
        .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(), 
            farmId, crop.getPlantedDate(), crop.getHarvestDate()))
        .toList();
    return ResponseEntity.ok(cropDtoList);
  }
}
