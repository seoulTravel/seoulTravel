import React, { useState, useEffect } from 'react';
import { Modal, DatePicker, Button, Input, List, Pagination } from 'antd';
import moment, { Moment } from 'moment';
import Sidebar from '../../components/Sidebar';
import Header from '../../components/Header';
import KakaoMap from '../../components/KakaoMap';
import { motion } from 'framer-motion';
import { getSpots, getAccommodations, getRestaurants } from '../../api/apiFunctions'; // API 호출 함수 임포트

interface TravelPlanItem {
  date: string;
  placeType: string;
  placeId: string;
}

interface Accommodation {
  accommodationId: string;
  startDate: string;
  endDate: string;
}

interface TravelPlan {
  planName: string;
  startDate: string;
  endDate: string;
  items: TravelPlanItem[];
  accommodations: Accommodation[];
}

const Map: React.FC = () => {
  const [places, setPlaces] = useState<any[]>([]);
  const [accommodations, setAccommodations] = useState<any[]>([]);
  const [restaurants, setRestaurants] = useState<any[]>([]);
  const [selectedCoords, setSelectedCoords] = useState<{ lat: number, lng: number } | null>(null);
  const [planName, setPlanName] = useState('');
  const [startDate, setStartDate] = useState<string | null>(null);
  const [endDate, setEndDate] = useState<string | null>(null);
  const [items, setItems] = useState<TravelPlanItem[]>([]);
  const [selectedAccommodations, setSelectedAccommodations] = useState<Accommodation[]>([]);
  const [modalVisible, setModalVisible] = useState(true);

  // 페이지네이션 상태
  const [accommodationPage, setAccommodationPage] = useState(1);
  const [restaurantPage, setRestaurantPage] = useState(1);
  const itemsPerPage = 5; // 페이지당 항목 수

  useEffect(() => {
    const fetchData = async () => {
      try {
        const spotData = await getSpots();
        if (Array.isArray(spotData)) {
          setPlaces(spotData);
        } else {
          console.error('API 응답이 배열이 아닙니다:', spotData);
        }
      } catch (error) {
        console.error('데이터 가져오기 중 오류 발생:', error);
      }
    };

    fetchData();
  }, []);

  const fetchAccommodationsAndRestaurants = async () => {
    if (startDate && endDate) {
      try {
        const accommodationData = await getAccommodations();
        const restaurantData = await getRestaurants();
        if (Array.isArray(accommodationData)) {
          setAccommodations(accommodationData);
        }
        if (Array.isArray(restaurantData)) {
          setRestaurants(restaurantData);
        }
      } catch (error) {
        console.error('데이터 가져오기 중 오류 발생:', error);
      }
    }
  };

  useEffect(() => {
    if (startDate && endDate) {
      fetchAccommodationsAndRestaurants();
    }
  }, [startDate, endDate]);

  const handleStartDateChange = (date: Moment | null) => {
    setStartDate(date ? date.format('YYYY-MM-DD') : null);
  };

  const handleEndDateChange = (date: Moment | null) => {
    setEndDate(date ? date.format('YYYY-MM-DD') : null);
  };

  const handleModalOk = () => {
    setModalVisible(false);
  };

  const handleModalCancel = () => {
    setModalVisible(false);
  };

  const handlePlanNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPlanName(e.target.value);
  };

  const handleAddAccommodation = (accommodationId: string) => {
    if (startDate && endDate) {
      const newAccommodation: Accommodation = {
        accommodationId,
        startDate,
        endDate,
      };
      setSelectedAccommodations([...selectedAccommodations, newAccommodation]);
    }
  };

  const handleAddItem = (date: string, placeType: string, placeId: string) => {
    const newItem: TravelPlanItem = {
      date,
      placeType,
      placeId,
    };
    setItems([...items, newItem]);
  };

  const handleSubmit = async () => {
    const travelPlan: TravelPlan = {
      planName,
      startDate: startDate || '',
      endDate: endDate || '',
      items,
      accommodations: selectedAccommodations,
    };

    try {
      const response = await fetch('/api/travelPlans', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(travelPlan),
      });
      if (response.ok) {
        console.log('Travel plan submitted successfully');
      } else {
        console.error('Failed to submit travel plan');
      }
    } catch (error) {
      console.error('Error submitting travel plan:', error);
    }
  };

  const handleMouseEnter = (address: string) => {
    const cleanedAddress = cleanAddress(address);
    const kakao = (window as any).kakao;
    const geocoder = new kakao.maps.services.Geocoder();

    geocoder.addressSearch(cleanedAddress, (result: any, status: any) => {
      if (status === kakao.maps.services.Status.OK) {
        const coords = {
          lat: parseFloat(result[0].y),
          lng: parseFloat(result[0].x),
        };
        setSelectedCoords(coords);
      } else {
        console.error('Geocode was not successful for the following reason: ' + status);
        setSelectedCoords(null);
      }
    });
  };

  const cleanAddress = (address: string): string => {
    let cleaned = address.replace(/^\d{5}\s/, '');
    cleaned = cleaned.replace(/\(.*?\)/g, '');
    cleaned = cleaned.replace(/\d+층/g, '');
    return cleaned.trim();
  };

  const handleAccommodationPageChange = (page: number) => {
    setAccommodationPage(page);
  };

  const handleRestaurantPageChange = (page: number) => {
    setRestaurantPage(page);
  };

  const paginatedAccommodations = accommodations.slice(
    (accommodationPage - 1) * itemsPerPage,
    accommodationPage * itemsPerPage
  );

  const paginatedRestaurants = restaurants.slice(
    (restaurantPage - 1) * itemsPerPage,
    restaurantPage * itemsPerPage
  );

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      transition={{ duration: .5 }}
      className="w-screen h-screen flex flex-col"
    >
      <Header />
      <Modal
        title="여행 날짜 선택"
        visible={modalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        footer={[
          <Button key="cancel" onClick={handleModalCancel}>
            Cancel
          </Button>,
          <Button key="ok" type="primary" onClick={handleModalOk}>
            OK
          </Button>,
        ]}
      >
        <DatePicker
          placeholder="시작 날짜"
          onChange={handleStartDateChange}
          style={{ display: 'block', marginBottom: '10px' }}
        />
        <DatePicker
          placeholder="종료 날짜"
          onChange={handleEndDateChange}
          style={{ display: 'block', marginBottom: '10px' }}
        />
      </Modal>
      <div className="flex flex-grow overflow-hidden">
        <Sidebar places={places} onHover={handleMouseEnter} />
        <div className="flex flex-row flex-grow relative">
          <div className="map flex-grow relative">
            <KakaoMap coords={selectedCoords} />
          </div>
          <div className="flex flex-col w-1/3 p-4 overflow-y-auto">
            <h3>숙박 정보</h3>
            <List
              bordered
              dataSource={paginatedAccommodations}
              renderItem={(item: any) => (
                <List.Item onClick={() => handleAddAccommodation(item.id)}>
                  {item.name}
                </List.Item>
              )}
              style={{ marginBottom: '20px' }}
            />
            <Pagination
              current={accommodationPage}
              pageSize={itemsPerPage}
              total={accommodations.length}
              onChange={handleAccommodationPageChange}
              style={{ textAlign: 'center', marginBottom: '20px' }}
            />
            <h3>식당 정보</h3>
            <List
              bordered
              dataSource={paginatedRestaurants}
              renderItem={(item: any) => (
                <List.Item onClick={() => handleAddItem(startDate || '', 'restaurant', item.id)}>
                  {item.name}
                </List.Item>
              )}
              style={{ marginBottom: '20px' }}
            />
            <Pagination
              current={restaurantPage}
              pageSize={itemsPerPage}
              total={restaurants.length}
              onChange={handleRestaurantPageChange}
              style={{ textAlign: 'center', marginBottom: '20px' }}
            />
          </div>
        </div>
      </div>
      <div className="p-4">
        <Input
          placeholder="Enter plan name"
          value={planName}
          onChange={handlePlanNameChange}
          style={{ marginBottom: '20px' }}
        />
        <Button type="primary" onClick={handleSubmit}>
          Submit Travel Plan
        </Button>
      </div>
    </motion.div>
  );
};

export default Map;
