package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FertilizerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;
  private final FertilizerRepository fertilizerRepository;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerRepository fertilizerRepository) {
    this.cropRepository = cropRepository;
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * getAllCrops.
   */
  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  /**
   * getCropById.
   */
  public Crop getCropById(Long id) {
    Optional<Crop> crop = cropRepository.findById(id);

    if (crop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    
    return crop.get();
  }

  /**
   * getCropByHarvestDate.
   */
  public List<Crop> getCropByHarvestDate(LocalDate start, LocalDate end) {
    return cropRepository.findAllByHarvestDateBetween(start, end);
  }

  /**
   * addFertilizer.
   */
  public String addFertilizer(Long id, Long fertilizerId) {
    Optional<Crop> optionalCrop = cropRepository.findById(id);

    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }

    Optional<Fertilizer> optionalFertilizer = fertilizerRepository.findById(fertilizerId);

    if (optionalFertilizer.isEmpty()) {
      throw new NotFoundException("Fertilizante não encontrado!");
    }

    Crop crop = optionalCrop.get();
    Fertilizer fertilizer = optionalFertilizer.get();

    crop.getFertilizers().add(fertilizer);
    cropRepository.save(crop);

    return "Fertilizante e plantação associados com sucesso!";
  }

  /**
   * getFertilizerByCropId.
   */
  public List<Fertilizer> getFertilizerByCropId(Long id) {
    Optional<Crop> optionalCrop = cropRepository.findById(id);

    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }

    Crop crop = optionalCrop.get();

    return crop.getFertilizers();
  }
}
