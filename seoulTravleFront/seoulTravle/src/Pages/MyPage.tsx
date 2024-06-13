import React, { useEffect, useState } from 'react';
import { List, Typography, Divider, Card } from 'antd';
import { getTravelPlans } from '../api/apiFunctions';
import Header from '../components/Header';

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

const groupItemsByDate = (items: TravelPlanItem[], accommodations: Accommodation[]) => {
  const grouped: Record<string, (TravelPlanItem | Accommodation)[]> = {};

  items.forEach(item => {
    if (!grouped[item.date]) {
      grouped[item.date] = [];
    }
    grouped[item.date].push(item);
  });

  accommodations.forEach(accommodation => {
    const date = accommodation.startDate;
    if (!grouped[date]) {
      grouped[date] = [];
    }
    grouped[date].push(accommodation);
  });

  return grouped;
};

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
    <div>
      <Header />
      <Typography.Title level={2} style={{ textAlign: 'center', paddingTop: '80px' }}>
        나의 여행계획
      </Typography.Title>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px', justifyContent: 'center' }}>
        {travelPlans.map((plan, index) => {
          const groupedItems = groupItemsByDate(plan.items, plan.accommodations);

          return (
            <Card key={index} title={plan.planName} bordered={true} style={{ width: 300 }}>
              <Typography.Text>
                {plan.startDate} - {plan.endDate}
              </Typography.Text>
              <Divider />
              <List
                size="small"
                dataSource={Object.entries(groupedItems)}
                renderItem={([date, items]: [string, (TravelPlanItem | Accommodation)[]]) => (
                  <div style={{ marginBottom: '20px' }}>
                    <Typography.Title level={5}>{date}</Typography.Title>
                    {items.map((item: TravelPlanItem | Accommodation, index) => (
                      'placeType' in item ? (
                        <Typography.Paragraph key={index}>{item.placeType}: {item.placeId}</Typography.Paragraph>
                      ) : (
                        <Typography.Paragraph key={index}>숙박: {item.accommodationId}</Typography.Paragraph>
                      )
                    ))}
                  </div>
                )}
              />
            </Card>
          );
        })}
      </div>
    </div>
  );
};

export default MyPage;
