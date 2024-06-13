import React, { useState, useEffect } from 'react';
import { Calendar, Modal, DatePicker, Button, Input } from 'antd';
import { Moment } from 'moment';
import 'antd/dist/antd.css';

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

const TravelPlanPage: React.FC = () => {
  const [planName, setPlanName] = useState('');
  const [startDate, setStartDate] = useState<string | null>(null);
  const [endDate, setEndDate] = useState<string | null>(null);
  const [items] = useState<TravelPlanItem[]>([]);
  const [accommodations] = useState<Accommodation[]>([]);
  const [modalVisible, setModalVisible] = useState(true); // 처음에 모달이 보이도록 설정

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

  return (
    <div>
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

      <Input
        placeholder="Enter plan name"
        value={planName}
        onChange={handlePlanNameChange}
        style={{ marginBottom: 20 }}
      />
      <Calendar />
      <Button type="primary" onClick={handleSubmit} style={{ marginTop: 20 }}>
        Submit Travel Plan
      </Button>
    </div>
  );
};

export default TravelPlanPage;
