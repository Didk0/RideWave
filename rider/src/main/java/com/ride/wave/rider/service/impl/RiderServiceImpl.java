package com.ride.wave.rider.service.impl;

import com.ride.wave.rider.dto.request.CreateRiderRequest;
import com.ride.wave.rider.dto.response.RiderDto;
import com.ride.wave.rider.entity.Rider;
import com.ride.wave.rider.repository.RiderRepository;
import com.ride.wave.rider.service.RiderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

  private final RiderRepository riderRepository;
  private final ModelMapper modelMapper;

  @Override
  public RiderDto createRider(final CreateRiderRequest request) {

    final Rider rider =
        Rider.builder().name(request.name()).email(request.email()).phone(request.phone()).build();

    riderRepository.save(rider);

    return modelMapper.map(rider, RiderDto.class);
  }

  @Override
  public RiderDto getRider(final Long id) {

    final Rider rider =
        riderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Rider with id " + id + " not found."));

    return modelMapper.map(rider, RiderDto.class);
  }

  @Override
  public List<RiderDto> getAllRiders() {

    return riderRepository.findAll().stream()
        .map(rider -> modelMapper.map(rider, RiderDto.class))
        .toList();
  }

  @Override
  public RiderDto updateRider(final Long id, final CreateRiderRequest request) {

    final Rider rider =
        riderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Rider with id " + id + " not found."));

    rider.setName(request.name());
    rider.setEmail(request.email());
    rider.setPhone(request.phone());

    riderRepository.save(rider);

    return modelMapper.map(rider, RiderDto.class);
  }

  @Override
  public void deleteRider(final Long id) {

    riderRepository.deleteById(id);
  }
}
