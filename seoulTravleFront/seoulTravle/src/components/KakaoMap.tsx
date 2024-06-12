import React, { useEffect, useRef } from 'react';

interface KakaoMapProps {
    coords: { lat: number, lng: number } | null;
}

const KakaoMap: React.FC<KakaoMapProps> = ({ coords }) => {
    const mapContainer = useRef<HTMLDivElement | null>(null);
    const mapRef = useRef<any>(null);
    const markerRef = useRef<any>(null);

    useEffect(() => {
        const kakao = (window as any).kakao;
        if (!kakao || !kakao.maps) {
            console.error('Kakao maps SDK not loaded');
            return;
        }

        const mapOption = {
            center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 기본 위치
            level: 3,
        };
        mapRef.current = new kakao.maps.Map(mapContainer.current, mapOption);

        markerRef.current = new kakao.maps.Marker({
            position: mapOption.center,
        });
        markerRef.current.setMap(mapRef.current);
    }, []);

    useEffect(() => {
        if (coords && mapRef.current) {
            const kakao = (window as any).kakao;
            const newCoords = new kakao.maps.LatLng(coords.lat, coords.lng);
            mapRef.current.setCenter(newCoords);
            markerRef.current.setPosition(newCoords);
        }
    }, [coords]);

    return (
        <div ref={mapContainer} className="w-full h-full"></div>
    );
}

export default KakaoMap;
