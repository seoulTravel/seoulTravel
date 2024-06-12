import React, { useState, useEffect } from 'react';
import { Calendar, Modal, DatePicker, Button, Input } from 'antd';
import moment, { Moment } from 'moment';
import Sidebar from '../../components/Sidebar';
import Header from '../../components/Header';
import KakaoMap from '../../components/KakaoMap';
import { motion } from 'framer-motion';
import { getSpots } from '../../api/apiFunctions'; // API 호출 함수 임포트

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
  const [places, setPlaces] = useState<any[]>([]); // 초기 상태를 빈 배열로 설정
  const [selectedCoords, setSelectedCoords] = useState<{ lat: number, lng: number } | null>(null);
  const [planName, setPlanName] = useState('');
  const [startDate, setStartDate] = useState<string | null>(null);
  const [endDate, setEndDate] = useState<string | null>(null);
  const [items, setItems] = useState<TravelPlanItem[]>([]);
  const [accommodations, setAccommodations] = useState<Accommodation[]>([]);
  const [modalVisible, setModalVisible] = useState(true); // 처음에 모달이 보이도록 설정

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getSpots();
        if (Array.isArray(data)) {
          setPlaces(data);
        } else {
          console.error('API 응답이 배열이 아닙니다:', data);
        }
      } catch (error) {
        console.error('데이터 가져오기 중 오류 발생:', error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    setModalVisible(true);
  }, []);

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

  const handleSubmit = async () => {
    const travelPlan: TravelPlan = {
      planName,
      startDate: startDate || '',
      endDate: endDate || '',
      items,
      accommodations,
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
        <div className="map flex-grow relative">
          <KakaoMap coords={selectedCoords} />
        </div>
      </div>
      <Input
        placeholder="Enter plan name"
        value={planName}
        onChange={handlePlanNameChange}
        style={{ margin: '20px' }}
      />
      <Button type="primary" onClick={handleSubmit} style={{ margin: '20px' }}>
        Submit Travel Plan
      </Button>
    </motion.div>
  );
};

export default Map;
