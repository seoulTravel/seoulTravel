import React, { useEffect, useState } from 'react';
import { List, Typography, Divider } from 'antd';
import { getTravelPlans } from '../api/apiFunctions';

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

const MyPage: React.FC = () => {
  const [travelPlans, setTravelPlans] = useState<TravelPlan[]>([]);

  useEffect(() => {
    const fetchTravelPlans = async () => {
      try {
        const plans = await getTravelPlans();
        setTravelPlans(plans);
      } catch (error) {
        console.error('Failed to fetch travel plans:', error);
      }
    };

    fetchTravelPlans();
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <Typography.Title level={2}>My Travel Plans</Typography.Title>
      {travelPlans.map((plan, index) => (
        <div key={index} style={{ marginBottom: '20px' }}>
          <Typography.Title level={4}>{plan.planName}</Typography.Title>
          <Typography.Text>
            {plan.startDate} - {plan.endDate}
          </Typography.Text>
          <Divider />
          <Typography.Title level={5}>Items</Typography.Title>
          <List
            bordered
            dataSource={plan.items}
            renderItem={(item: TravelPlanItem) => (
              <List.Item>
                {item.date} - {item.placeType}: {item.placeId}
              </List.Item>
            )}
          />
          <Typography.Title level={5} style={{ marginTop: '20px' }}>
            Accommodations
          </Typography.Title>
          <List
            bordered
            dataSource={plan.accommodations}
            renderItem={(item: Accommodation) => (
              <List.Item>
                {item.accommodationId}: {item.startDate} - {item.endDate}
              </List.Item>
            )}
          />
        </div>
      ))}
    </div>
  );
};

export default MyPage;
