package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;

  private final CropRepository cropRepository;

  /**
   * Constructor.
   */
  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  /**
   * Create.
   */
  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  /**
   * getAll.
   */
  public List<Farm> getAllFarms() {
    return farmRepository.findAll();
  }

  /**
   * getFarmById.
   */
  public Farm getFarmById(Long id) {
    Optional<Farm> farm = farmRepository.findById(id);
    
    if (farm.isEmpty()) {
      throw new NotFoundException("Fazenda não encontrada!");
    }

    return farm.get();
  }

  /**
   * createCrop.
   */
  public Crop createCrop(Long id, Crop crop) {
    Farm farm = this.getFarmById(id);
    crop.setFarm(farm);

    Crop newCrop = cropRepository.save(crop);
    farm.getCrops().add(newCrop);

    this.create(farm);

    return newCrop;
  }

  /**
   * getCropsById.
   */
  public List<Crop> getCropsById(Long farmId) {
    Farm farm = this.getFarmById(farmId);
    return farm.getCrops();
  }
}
