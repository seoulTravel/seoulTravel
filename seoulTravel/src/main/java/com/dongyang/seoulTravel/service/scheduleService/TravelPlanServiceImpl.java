package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.TravelPlanDto;
import com.dongyang.seoulTravel.dto.schedule.TravelPlanItemDto;
import com.dongyang.seoulTravel.dto.schedule.AccommodationPeriodDto;
import com.dongyang.seoulTravel.entity.schedule.TravelPlanEntity;
import com.dongyang.seoulTravel.entity.schedule.TravelPlanItemEntity;
import com.dongyang.seoulTravel.entity.schedule.AccommodationPeriodEntity;
import com.dongyang.seoulTravel.repository.schedule.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    // 여행 계획 생성
    @Override
    public void createTravelPlan(TravelPlanDto travelPlanDto) {
        TravelPlanEntity travelPlanEntity = new TravelPlanEntity();
        travelPlanEntity.setPlanName(travelPlanDto.getPlanName());
        travelPlanEntity.setStartDate(LocalDate.parse(travelPlanDto.getStartDate()));
        travelPlanEntity.setEndDate(LocalDate.parse(travelPlanDto.getEndDate()));

        // 여행 일정 항목 생성
        List<TravelPlanItemEntity> items = travelPlanDto.getItems().stream().map(itemDto -> {
            TravelPlanItemEntity itemEntity = new TravelPlanItemEntity();
            itemEntity.setDate(itemDto.getDate());
            itemEntity.setPlaceType(itemDto.getPlaceType());
            itemEntity.setPlaceId(itemDto.getPlaceId());
            itemEntity.setTravelPlan(travelPlanEntity);
            return itemEntity;
        }).collect(Collectors.toList());

        // 숙소 기간 항목 생성
        List<AccommodationPeriodEntity> accommodations = travelPlanDto.getAccommodations().stream().map(accDto -> {
            AccommodationPeriodEntity accommodationEntity = new AccommodationPeriodEntity();
            accommodationEntity.setAccommodationId(accDto.getAccommodationId());
            accommodationEntity.setStartDate(LocalDate.parse(accDto.getStartDate()));
            accommodationEntity.setEndDate(LocalDate.parse(accDto.getEndDate()));
            accommodationEntity.setTravelPlan(travelPlanEntity);
            return accommodationEntity;
        }).collect(Collectors.toList());

        travelPlanEntity.setItems(items);
        travelPlanEntity.setAccommodations(accommodations);
        travelPlanRepository.save(travelPlanEntity); // DB 저장
    }

    // 모든 여행 계획 조회
    @Override
    public List<TravelPlanDto> getAllTravelPlans() {
        List<TravelPlanEntity> travelPlans = travelPlanRepository.findAll();
        return travelPlans.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 특정 여행 계획 조회
    @Override
    public TravelPlanDto getTravelPlanById(Long id) {
        TravelPlanEntity travelPlanEntity = travelPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel plan id를 찾을 수 없음 " + id));
        return convertToDto(travelPlanEntity);
    }

    // 여행 계획 수정
    @Override
    public void updateTravelPlan(Long id, TravelPlanDto travelPlanDto) {
        TravelPlanEntity travelPlanEntity = travelPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel plan id를 찾을 수 없음 " + id));

        travelPlanEntity.setPlanName(travelPlanDto.getPlanName());
        travelPlanEntity.setStartDate(LocalDate.parse(travelPlanDto.getStartDate()));
        travelPlanEntity.setEndDate(LocalDate.parse(travelPlanDto.getEndDate()));

        // 기존 항목을 모두 가져와서 Map으로 변환 (Key: 일정 날짜 + 장소 ID)
        Map<String, TravelPlanItemEntity> existingItemsMap = travelPlanEntity.getItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getDate() + item.getPlaceId(),
                        item -> item
                ));

        // 새로운 항목을 처리
        for (TravelPlanItemDto itemDto : travelPlanDto.getItems()) {
            String key = itemDto.getDate() + itemDto.getPlaceId();
            TravelPlanItemEntity itemEntity = existingItemsMap.get(key);

            if (itemEntity == null) {
                // 새 항목 추가
                itemEntity = new TravelPlanItemEntity();
                itemEntity.setTravelPlan(travelPlanEntity);
                travelPlanEntity.getItems().add(itemEntity);
            }

            // 항목 업데이트
            itemEntity.setDate(itemDto.getDate());
            itemEntity.setPlaceType(itemDto.getPlaceType());
            itemEntity.setPlaceId(itemDto.getPlaceId());
        }

        // 기존 항목 중 더 이상 사용되지 않는 항목 삭제
        travelPlanEntity.getItems().removeIf(item -> !existingItemsMap.containsKey(item.getDate() + item.getPlaceId()));

        // 숙소 기간 처리------------------------- 동일한 방식
        Map<String, AccommodationPeriodEntity> existingAccommodationsMap = travelPlanEntity.getAccommodations().stream()
                .collect(Collectors.toMap(
                        acc -> acc.getStartDate().toString() + acc.getAccommodationId(),
                        acc -> acc
                ));

        for (AccommodationPeriodDto accDto : travelPlanDto.getAccommodations()) {
            String key = accDto.getStartDate() + accDto.getAccommodationId();
            AccommodationPeriodEntity accommodationEntity = existingAccommodationsMap.get(key);

            if (accommodationEntity == null) {
                // 새 항목 추가
                accommodationEntity = new AccommodationPeriodEntity();
                accommodationEntity.setTravelPlan(travelPlanEntity);
                travelPlanEntity.getAccommodations().add(accommodationEntity);
            }


            // 항목 업데이트
            accommodationEntity.setAccommodationId(accDto.getAccommodationId());
            accommodationEntity.setStartDate(LocalDate.parse(accDto.getStartDate()));
            accommodationEntity.setEndDate(LocalDate.parse(accDto.getEndDate()));
        }

        // 기존 항목 중 더 이상 사용되지 않는 항목 삭제
        travelPlanEntity.getAccommodations().removeIf(acc -> !existingAccommodationsMap.containsKey(acc.getStartDate().toString() + acc.getAccommodationId()));


        travelPlanRepository.save(travelPlanEntity);
    }


    // 여행 계획 삭제
    @Override
    public void deleteTravelPlan(Long id) {
        travelPlanRepository.deleteById(id);
    }

    // TravelPlanEntity를 TravelPlanDto로 변환하는 메서드
    private TravelPlanDto convertToDto(TravelPlanEntity travelPlanEntity) {
        TravelPlanDto travelPlanDto = new TravelPlanDto();
        travelPlanDto.setPlanName(travelPlanEntity.getPlanName());
        travelPlanDto.setStartDate(travelPlanEntity.getStartDate().toString());
        travelPlanDto.setEndDate(travelPlanEntity.getEndDate().toString());

        // 일정 항목 변환
        List<TravelPlanItemDto> items = travelPlanEntity.getItems().stream().map(itemEntity -> {
            TravelPlanItemDto itemDto = new TravelPlanItemDto();
            itemDto.setDate(itemEntity.getDate());
            itemDto.setPlaceType(itemEntity.getPlaceType());
            itemDto.setPlaceId(itemEntity.getPlaceId());
            return itemDto;
        }).collect(Collectors.toList());

        // 숙소 기간 항목 변환
        List<AccommodationPeriodDto> accommodations = travelPlanEntity.getAccommodations().stream().map(accEntity -> {
            AccommodationPeriodDto accommodationDto = new AccommodationPeriodDto();
            accommodationDto.setAccommodationId(accEntity.getAccommodationId());
            accommodationDto.setStartDate(accEntity.getStartDate().toString());
            accommodationDto.setEndDate(accEntity.getEndDate().toString());
            return accommodationDto;
        }).collect(Collectors.toList());

        travelPlanDto.setItems(items);
        travelPlanDto.setAccommodations(accommodations);
        return travelPlanDto;
    }
}
