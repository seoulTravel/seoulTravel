package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.TravelPlanDto;
import com.dongyang.seoulTravel.dto.schedule.TravelPlanItemDto;
import com.dongyang.seoulTravel.entity.schedule.TravelPlanEntity;
import com.dongyang.seoulTravel.entity.schedule.TravelPlanItemEntity;
import com.dongyang.seoulTravel.repository.schedule.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Override
    public void createTravelPlan(TravelPlanDto travelPlanDto) {
        TravelPlanEntity travelPlanEntity = new TravelPlanEntity();
        travelPlanEntity.setPlanName(travelPlanDto.getPlanName());
        travelPlanEntity.setStartDate(LocalDate.parse(travelPlanDto.getStartDate()));
        travelPlanEntity.setEndDate(LocalDate.parse(travelPlanDto.getEndDate()));

        List<TravelPlanItemEntity> items = travelPlanDto.getItems().stream().map(itemDto -> {
            TravelPlanItemEntity itemEntity = new TravelPlanItemEntity();
            itemEntity.setDate(itemDto.getDate());
            itemEntity.setPlaceType(itemDto.getPlaceType());
            itemEntity.setPlaceId(itemDto.getPlaceId());
            itemEntity.setTravelPlan(travelPlanEntity);
            return itemEntity;
        }).collect(Collectors.toList());

        // 사용자가 선택한 숙소 ID를 받아옴
        String accommodationId = travelPlanDto.getAccommodationId();

        // 일정마다 끝에 숙소가 붙도록 알고리즘 설정
        // 주의: 일정의 끝에는 붙지 않도록 만들어줌
        LocalDate currentDate = travelPlanEntity.getStartDate();
        LocalDate endDate = travelPlanEntity.getEndDate();

        while (!currentDate.isAfter(endDate)) {
            String dateStr = currentDate.toString();
            boolean hasAccommodation = items.stream().anyMatch(item -> item.getDate().equals(dateStr) && item.getPlaceType().equals("ACCOMMODATION"));

            if (!hasAccommodation) {
                // 해당 날짜에 숙소가 없는 경우 사용자가 선택한 숙소 항목 추가
                TravelPlanItemEntity selectedAccommodation = new TravelPlanItemEntity();
                selectedAccommodation.setDate(dateStr);
                selectedAccommodation.setPlaceType("ACCOMMODATION");
                selectedAccommodation.setPlaceId(accommodationId); // 사용자가 선택한 숙소 ID로 설정
                selectedAccommodation.setTravelPlan(travelPlanEntity);
                items.add(selectedAccommodation);
            }

            currentDate = currentDate.plusDays(1);
        }

        travelPlanEntity.setItems(items);
        travelPlanRepository.save(travelPlanEntity);
    }

    @Override
    public List<TravelPlanDto> getAllTravelPlans() {
        List<TravelPlanEntity> travelPlans = travelPlanRepository.findAll();
        return travelPlans.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TravelPlanDto getTravelPlanById(Long id) {
        TravelPlanEntity travelPlanEntity = travelPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel plan not found with id " + id));
        return convertToDto(travelPlanEntity);
    }

    @Override
    public void updateTravelPlan(Long id, TravelPlanDto travelPlanDto) {
        TravelPlanEntity travelPlanEntity = travelPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel plan not found with id " + id));

        travelPlanEntity.setPlanName(travelPlanDto.getPlanName());
        travelPlanEntity.setStartDate(LocalDate.parse(travelPlanDto.getStartDate()));
        travelPlanEntity.setEndDate(LocalDate.parse(travelPlanDto.getEndDate()));

        // Clear existing items and set new ones
        travelPlanEntity.getItems().clear();
        List<TravelPlanItemEntity> items = travelPlanDto.getItems().stream().map(itemDto -> {
            TravelPlanItemEntity itemEntity = new TravelPlanItemEntity();
            itemEntity.setDate(itemDto.getDate());
            itemEntity.setPlaceType(itemDto.getPlaceType());
            itemEntity.setPlaceId(itemDto.getPlaceId());
            itemEntity.setTravelPlan(travelPlanEntity);
            return itemEntity;
        }).collect(Collectors.toList());

        // 사용자가 선택한 숙소 ID를 받아옴
        String accommodationId = travelPlanDto.getAccommodationId();

        // 일정의 끝에 자동으로 숙소가 붙게 만들어줌
        LocalDate currentDate = travelPlanEntity.getStartDate();
        LocalDate endDate = travelPlanEntity.getEndDate();

        while (!currentDate.isAfter(endDate)) {
            String dateStr = currentDate.toString();
            boolean hasAccommodation = items.stream().anyMatch(item -> item.getDate().equals(dateStr) && item.getPlaceType().equals("ACCOMMODATION"));

            if (!hasAccommodation) {
                // 해당 날짜에 숙소가 없는 경우 사용자가 선택한 숙소 항목 추가
                TravelPlanItemEntity selectedAccommodation = new TravelPlanItemEntity();
                selectedAccommodation.setDate(dateStr);
                selectedAccommodation.setPlaceType("ACCOMMODATION");
                selectedAccommodation.setPlaceId(accommodationId); // 사용자가 선택한 숙소 ID로 설정
                selectedAccommodation.setTravelPlan(travelPlanEntity);
                items.add(selectedAccommodation);
            }

            currentDate = currentDate.plusDays(1);
        }

        travelPlanEntity.setItems(items);
        travelPlanRepository.save(travelPlanEntity);
    }

    @Override
    public void deleteTravelPlan(Long id) {
        travelPlanRepository.deleteById(id);
    }

    private TravelPlanDto convertToDto(TravelPlanEntity travelPlanEntity) {
        TravelPlanDto travelPlanDto = new TravelPlanDto();
        travelPlanDto.setPlanName(travelPlanEntity.getPlanName());
        travelPlanDto.setStartDate(travelPlanEntity.getStartDate().toString());
        travelPlanDto.setEndDate(travelPlanEntity.getEndDate().toString());

        List<TravelPlanItemDto> items = travelPlanEntity.getItems().stream().map(itemEntity -> {
            TravelPlanItemDto itemDto = new TravelPlanItemDto();
            itemDto.setDate(itemEntity.getDate());
            itemDto.setPlaceType(itemEntity.getPlaceType());
            itemDto.setPlaceId(itemEntity.getPlaceId());
            return itemDto;
        }).collect(Collectors.toList());

        travelPlanDto.setItems(items);
        return travelPlanDto;
    }
}
