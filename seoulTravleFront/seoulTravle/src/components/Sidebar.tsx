import React, { useState, useEffect } from 'react';

interface Place {
  name: string;
  POST_SN: string;
  NEW_ADDRESS: string;
  POST_URL: string;
  CMMN_TELNO: string;
}

interface SidebarProps {
  places: Place[];
  onHover: (address: string) => void;
}

const cleanAddress = (address: string): string => {
  // 앞의 숫자 5자리 제거
  let cleaned = address.replace(/^\d{5}\s/, '');
  
  // 괄호 안의 내용 제거
  cleaned = cleaned.replace(/\(.*?\)/g, '');
  
  // "~층" 정보 제거
  cleaned = cleaned.replace(/\d+층/g, '');
  
  return cleaned.trim();
}

const Sidebar: React.FC<SidebarProps> = ({ places, onHover }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredPlaces, setFilteredPlaces] = useState<Place[]>(places);

  useEffect(() => {
    setFilteredPlaces(places);
  }, [places]);

  const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    const searchTerm = event.target.value.toLowerCase();
    setSearchTerm(searchTerm);
    const filtered = places.filter(place =>
      place.name.toLowerCase().includes(searchTerm) ||
      place.NEW_ADDRESS.toLowerCase().includes(searchTerm)
    );
    setFilteredPlaces(filtered);
  };

  const handleMouseEnter = (address: string) => {
    onHover(address);
  }

  return (
    <aside className="sidebar pt-20 w-64 bg-white p-4 overflow-y-auto h-screen">
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearch}
        placeholder="검색"
        className="w-full p-2 mb-4 border rounded"
      />
      {Array.isArray(filteredPlaces) && filteredPlaces.map((place, index) => (
        <div
          key={index}
          className="place-card p-4 mb-4 border rounded-lg cursor-pointer hover:bg-gray-100"
          onMouseEnter={() => handleMouseEnter(place.NEW_ADDRESS)}
        >
          <h3 className="text-lg font-bold">{place.name}</h3>
          <p className="text-sm text-gray-600">{place.NEW_ADDRESS}</p>
          <a href={place.POST_URL} target="_blank" rel="noopener noreferrer" className="text-blue-500">자세히 보기</a>
        </div>
      ))}
    </aside>
  );
}

export default Sidebar;
