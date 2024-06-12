import React from 'react';
import { useLocation } from 'react-router-dom';

const Header: React.FC = () => {
    const location = useLocation();
    const isHome = location.pathname === '/';

    if (isHome) {
        return null;
    }

    const links = [
        { path: '/community', label: '커뮤니티' },
        { path: '/plan', label: '서울계획만들기' },
        { path: '/mypage', label: '마이페이지' },
    ];

    return (
        <header className="header fixed w-full bg-white text-black/50 p-4 z-50 shadow-md">
            <nav className="flex justify-between items-center">
                <div></div>
                <div className="links flex gap-16 text-xl">
                    {links.map(link => (
                        <a
                            key={link.path}
                            href={link.path}
                            className={location.pathname === link.path ? 'text-black' : 'text-black/30'}
                        >
                            {link.label}
                        </a>
                    ))}
                </div>
                <div className="logo">로고</div>
            </nav>
        </header>
    );
}

export default Header;
