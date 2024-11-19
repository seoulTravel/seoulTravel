import React from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';

const Home: React.FC = () => {
    const navigate = useNavigate();

    const handleNavigation = (path: string) => {
        navigate(path);
    };

    const navItems = [
        {
            label: "다른 사람들은 어떤 여행을 했을까?",
            actionLabel: "커뮤니티",
            actionPath: null,
        },
        {
            label: "AI가 계획해준 나의 여행",
            actionLabel: "서울계획짜기",
            actionPath: '/plan',
        },
        {
            label: "나의 발자취 그리고 동행자와 함께",
            actionLabel: "마이페이지",
            actionPath: null,
        },
    ];

    return (
        <motion.div
            className="wrap w-screen h-screen bg-[url('/public/images/main.png')] overflow-hidden"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 1.5 }}
        >
            <div className="flex w-[110%] h-full justify-end items-center relative left-32">
                <motion.nav
                    className="text-white/50 font-display text-3xl ease-in-out pr-10 border-r border-white/50"
                    initial={{ x: 200 }}
                    animate={{ x: 0 }}
                    transition={{ type: "tween", duration: 1.5 }}
                >
                    {navItems.map((item, index) => (
                        <ul key={index} className="py-10 flex justify-between items-center gap-x-10 hover:text-white">
                            <li className="ease-in-out transition duration-300">{item.label}</li>
                            <li className="line w-96 h-[0.5px] bg-white"></li>
                            {item.actionPath ? (
                                <li className="cursor-pointer ease-in-out transition duration-300" onClick={() => handleNavigation(item.actionPath)}>
                                    {item.actionLabel}
                                </li>
                            ) : (
                                <li className="cursor-pointer ease-in-out transition duration-300">{item.actionLabel}</li>
                            )}
                        </ul>
                    ))}
                </motion.nav>
                <motion.div
                    className="circle flex items-center justify-center ml-20 bg-white/50 rounded-full w-[842px] h-[842px]"
                    initial={{ scale: 0.8, opacity: 0 }}
                    animate={{ scale: 1, opacity: 1 }}
                    transition={{ duration: 1.5, type: "tween" }}
                >
                    <img src="/public/images/logo.svg" alt="Logo" />
                </motion.div>
            </div>
        </motion.div>
    );
};

export default Home;
