package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.controllers.dto.MessageDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller.
 */
@Controller
@RequestMapping("/crops")
public class CropController {
  private final CropService cropService;
  
  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * getAllCrops.
   */
  @GetMapping
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<Crop> crops = cropService.getAllCrops();
    List<CropDto> cropsDtoList = crops.stream()
        .map(crop -> new CropDto(
          crop.getId(), 
          crop.getName(),
          crop.getPlantedArea(), 
          crop.getFarm().getId(),
          crop.getPlantedDate(),
          crop.getHarvestDate()
        ))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsDtoList);
  }

  /**
   * getCropById.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CropDto> getCropById(@PathVariable Long id) {
    Crop crop = cropService.getCropById(id);
    CropDto cropDto = new CropDto(
        crop.getId(), 
        crop.getName(),
        crop.getPlantedArea(), 
        crop.getFarm().getId(),
        crop.getPlantedDate(),
        crop.getHarvestDate()
    );

    return ResponseEntity.status(HttpStatus.OK).body(cropDto);
  }

  /**
   * getCropByHarvestDate.
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropDto>> getCropByHarvestDate(
      @RequestParam(name = "start") LocalDate start,
      @RequestParam(name = "end") LocalDate end
  ) {
    List<Crop> crops = cropService.getCropByHarvestDate(start, end);

    List<CropDto> cropsDtoList = crops.stream()
        .map(crop -> new CropDto(
          crop.getId(), 
          crop.getName(),
          crop.getPlantedArea(), 
          crop.getFarm().getId(),
          crop.getPlantedDate(),
          crop.getHarvestDate()
        ))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsDtoList);
  }

  /**
   * addFertilizer.
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<MessageDto> addFertilizer(
      @PathVariable Long cropId,
      @PathVariable Long fertilizerId
  ) {
    String message = cropService.addFertilizer(cropId, fertilizerId);

    MessageDto messageDto = new MessageDto(message);
    return ResponseEntity.status(HttpStatus.CREATED).body(messageDto);
  }

  /**
   * getFertilizerByCropId.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<List<FertilizerDto>> getFertilizerByCropId(@PathVariable Long cropId) {
    List<Fertilizer> fertilizers = cropService.getFertilizerByCropId(cropId);

    List<FertilizerDto> fertilizersDtoList = fertilizers.stream()
        .map(fertilizer -> new FertilizerDto(
          fertilizer.getId(), 
          fertilizer.getName(),
          fertilizer.getBrand(),
          fertilizer.getComposition()
        ))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(fertilizersDtoList);
  }
}
