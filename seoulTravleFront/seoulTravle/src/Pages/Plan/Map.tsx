import React, { useState, useEffect } from 'react';
import Sidebar from '../../components/Sidebar';
import Header from '../../components/Header';
import KakaoMap from '../../components/KakaoMap';
import { motion } from 'framer-motion';
import { getSpots } from '../../api/apiFunctions'; // API 호출 함수 임포트

const Map: React.FC = () => {
    const [places, setPlaces] = useState<any[]>([]); // 초기 상태를 빈 배열로 설정
    const [selectedCoords, setSelectedCoords] = useState<{ lat: number, lng: number } | null>(null);

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

    return (
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: .5 }}
            className="w-screen h-screen flex flex-col"
        >
            <Header />
            <div className="flex flex-grow overflow-hidden">
                <Sidebar places={places} onHover={setSelectedCoords} />
                <div className="map flex-grow relative">
                    <KakaoMap coords={selectedCoords} />
                </div>
            </div>
        </motion.div>
    );
}

export default Map;
