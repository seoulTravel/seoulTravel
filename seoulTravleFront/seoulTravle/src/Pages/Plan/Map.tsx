import React, { useState, useEffect } from 'react';
import { Modal, DatePicker, Button, Input, List, Pagination } from 'antd';
import moment, { Moment } from 'moment';
import Sidebar from '../../components/Sidebar';
import Header from '../../components/Header';
import KakaoMap from '../../components/KakaoMap';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { getSpots, getAccommodations, getRestaurants, postTravelPlan } from '../../api/apiFunctions';

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
  const [accommodationModalVisible, setAccommodationModalVisible] = useState(false);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const [selectedAccommodationName, setSelectedAccommodationName] = useState<string | null>(null);
  const [accommodationStartDate, setAccommodationStartDate] = useState<string | null>(null);
  const [accommodationEndDate, setAccommodationEndDate] = useState<string | null>(null);
  const navigate = useNavigate();

  // 페이지네이션 상태
  const [accommodationPage, setAccommodationPage] = useState(1);
  const [restaurantPage, setRestaurantPage] = useState(1);
  const itemsPerPage = 5; // 페이지당 항목 수

  useEffect(() => {
    const fetchData = async () => {
      try {
        const cachedSpots = localStorage.getItem('spots');
        if (cachedSpots) {
          setPlaces(JSON.parse(cachedSpots));
        } else {
          const spotData = await getSpots();
          if (Array.isArray(spotData)) {
            setPlaces(spotData);
            localStorage.setItem('spots', JSON.stringify(spotData));
          } else {
            console.error('API 응답이 배열이 아닙니다:', spotData);
          }
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
        const cachedAccommodations = localStorage.getItem('accommodations');
        const cachedRestaurants = localStorage.getItem('restaurants');

        if (cachedAccommodations && cachedRestaurants) {
          setAccommodations(JSON.parse(cachedAccommodations));
          setRestaurants(JSON.parse(cachedRestaurants));
        } else {
          const accommodationData = await getAccommodations();
          const restaurantData = await getRestaurants();
          if (Array.isArray(accommodationData)) {
            setAccommodations(accommodationData);
            localStorage.setItem('accommodations', JSON.stringify(accommodationData));
          }
          if (Array.isArray(restaurantData)) {
            setRestaurants(restaurantData);
            localStorage.setItem('restaurants', JSON.stringify(restaurantData));
          }
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
    const formattedDate = date ? date.format('YYYY-MM-DD') : null;
    setStartDate(formattedDate);
  };

  const handleEndDateChange = (date: Moment | null) => {
    const formattedDate = date ? date.format('YYYY-MM-DD') : null;
    setEndDate(formattedDate);
  };

  const handleModalOk = () => {
    setModalVisible(false);
  };

  const handleModalCancel = () => {
    setModalVisible(false);
  };

  const handleAccommodationModalOk = () => {
    if (accommodationStartDate && accommodationEndDate && selectedAccommodationName) {
      handleAddAccommodation(selectedAccommodationName, accommodationStartDate, accommodationEndDate);
      setAccommodationModalVisible(false);
    } else {
      alert('모든 날짜를 선택해주세요.');
    }
  };

  const handleAccommodationModalCancel = () => {
    setAccommodationModalVisible(false);
  };

  const handlePlanNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPlanName(e.target.value);
  };

  const handleAddAccommodation = (name: string, startDate: string, endDate: string) => {
    const newAccommodation: Accommodation = {
      accommodationId: name,
      startDate,
      endDate,
    };
    const updatedAccommodations = [...selectedAccommodations, newAccommodation];
    setSelectedAccommodations(updatedAccommodations);
  };

  const handleAddItem = (date: string, placeType: string, placeId: string) => {
    const newItem: TravelPlanItem = {
      date,
      placeType,
      placeId,
    };
    const updatedItems = [...items, newItem];
    setItems(updatedItems);
  };

  const handleItemAddToDate = (placeType: string, name: string) => {
    if (selectedDate) {
      handleAddItem(selectedDate, placeType, name);
    } else {
      alert('날짜를 먼저 선택하세요.');
    }
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
      await postTravelPlan(travelPlan);
      alert('Travel plan submitted successfully');
      navigate('/mypage');
    } catch (error) {
      console.error('Failed to submit travel plan');
      alert('Failed to submit travel plan');
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

  const generateDateRange = (start: string, end: string): string[] => {
    const startDate = moment(start);
    const endDate = moment(end);
    const dateArray: string[] = [];

    let currentDate = startDate;
    while (currentDate <= endDate) {
      dateArray.push(currentDate.format('YYYY-MM-DD'));
      currentDate = currentDate.add(1, 'days');
    }
    return dateArray;
  };

  const handleDateClick = (date: string) => {
    setSelectedDate(date);
  };

  const dateRange = startDate && endDate ? generateDateRange(startDate, endDate) : [];

  useEffect(() => {
    console.log({
      planName,
      startDate,
      endDate,
      items,
      accommodations: selectedAccommodations,
    });
  }, [planName, startDate, endDate, items, selectedAccommodations]);

  const openAccommodationModal = (name: string) => {
    setSelectedAccommodationName(name);
    setAccommodationModalVisible(true);
  };

  const handleAccommodationStartDateChange = (date: Moment | null) => {
    const formattedDate = date ? date.format('YYYY-MM-DD') : null;
    setAccommodationStartDate(formattedDate);
  };

  const handleAccommodationEndDateChange = (date: Moment | null) => {
    const formattedDate = date ? date.format('YYYY-MM-DD') : null;
    setAccommodationEndDate(formattedDate);
  };

  const disabledDate = (current: Moment) => {
    return current && current < moment().startOf('day');
  };

  const disabledAccommodationDate = (current: Moment) => {
    return current && (current < moment(startDate) || current > moment(endDate));
  };

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
          disabledDate={disabledDate}
          style={{ display: 'block', marginBottom: '10px' }}
        />
        <DatePicker
          placeholder="종료 날짜"
          onChange={handleEndDateChange}
          disabledDate={disabledDate}
          style={{ display: 'block', marginBottom: '10px' }}
        />
      </Modal>
      <Modal
        title="숙박 날짜 선택"
        visible={accommodationModalVisible}
        onOk={handleAccommodationModalOk}
        onCancel={handleAccommodationModalCancel}
        footer={[
          <Button key="cancel" onClick={handleAccommodationModalCancel}>
            Cancel
          </Button>,
          <Button key="ok" type="primary" onClick={handleAccommodationModalOk}>
            OK
          </Button>,
        ]}
      >
        <DatePicker
          placeholder="숙박 시작 날짜"
          onChange={handleAccommodationStartDateChange}
          disabledDate={disabledAccommodationDate}
          style={{ display: 'block', marginBottom: '10px' }}
        />
        <DatePicker
          placeholder="숙박 종료 날짜"
          onChange={handleAccommodationEndDateChange}
          disabledDate={disabledAccommodationDate}
          style={{ display: 'block', marginBottom: '10px' }}
        />
      </Modal>
      <div className="flex flex-grow overflow-hidden">
        <Sidebar places={places} onHover={handleMouseEnter} onClick={(name: string) => handleItemAddToDate('spot', name)} />
        <div className="flex flex-row flex-grow relative">
          <div className="map flex-grow relative">
            <KakaoMap coords={selectedCoords} />
          </div>
          <div className="flex flex-col w-1/3 p-4 overflow-y-auto pt-20">
            <h3>숙박 정보</h3>
            <List
              bordered
              dataSource={paginatedAccommodations}
              renderItem={(item: any) => (
                <List.Item onClick={() => openAccommodationModal(item.name)}>
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
                <List.Item onClick={() => handleItemAddToDate('restaurant', item.name)}>
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
      <div className="flex overflow-x-auto p-4 bg-gray-100">
        {dateRange.map(date => (
          <div
            key={date}
            onClick={() => handleDateClick(date)}
            className={`p-4 leading-[.2] m-2 border rounded cursor-pointer ${selectedDate === date ? 'bg-blue-300' : 'bg-white'}`}
          >
            {date}
          </div>
        ))}
      </div>
      <div className="p-4">
        {selectedDate && (
          <div>
            <h3>{selectedDate}에 선택된 장소</h3>
            <List
              bordered
              dataSource={items.filter(item => item.date === selectedDate)}
              renderItem={(item: TravelPlanItem) => (
                <List.Item>
                  {item.placeType}: {item.placeId}
                </List.Item>
              )}
            />
          </div>
        )}
        <h3>선택된 숙박 정보</h3>
        <List
          bordered
          dataSource={selectedAccommodations}
          renderItem={(item: Accommodation) => (
            <List.Item>
              {item.accommodationId}: {item.startDate} - {item.endDate}
            </List.Item>
          )}
        />
      </div>
    </motion.div>
  );
};

export default Map;
