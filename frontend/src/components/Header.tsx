import { Link, useLocation } from 'react-router-dom';
import { Layout, Menu, Button, message } from 'antd';
import { getUserId, getUserRole } from '@/utils/auth';

const { Header: AntHeader } = Layout;

const Header = () => {
    const location = useLocation();
    const userId = getUserId();
    const userRole = getUserRole();

    // 算出當前URL
    const selectedKey = location.pathname === '/' ? 'home' : location.pathname.replace('/', '');

    const handleLogout = () => {
        // 顯示確認訊息
        if (window.confirm('是否確定登出?')) {
            // 清除 token
            localStorage.removeItem('token');
            message.success('已登出');
            // 重新整理頁面以更新狀態
            window.location.reload();
        }
    };

    // 如果是 admin，只顯示首頁、登出、關於
    if (userRole === 'admin') {
        return (
            <AntHeader>
                <Link to="/" style={{ color: 'white', fontSize: '20px', fontWeight: 'bold', textDecoration: 'none' }}>
                    購物車系統
                </Link>
                <Menu theme="dark" mode="horizontal" selectedKeys={[selectedKey]}>
                    <Menu.Item key="home">
                        <Link to="/">首頁</Link>
                    </Menu.Item>
                    <Menu.Item key="login" style={{ float: 'right' }}>
                        <Button type="text" style={{ color: 'white' }} onClick={handleLogout}>
                            登出
                        </Button>
                    </Menu.Item>
                    <Menu.Item key="about">
                        <Link to="/about">關於</Link>
                    </Menu.Item>
                </Menu>
            </AntHeader>
        );
    }

    return (
        <AntHeader>
            <Link to="/" style={{ color: 'white', fontSize: '20px', fontWeight: 'bold', textDecoration: 'none' }}>
                購物車系統
            </Link>
            <Menu theme="dark" mode="horizontal" selectedKeys={[selectedKey]}>
                <Menu.Item key="home">
                    <Link to="/">首頁</Link>
                </Menu.Item>
                <Menu.Item key="cart-items">
                    <Link to="/cart-items">購物車</Link>
                </Menu.Item>
                <Menu.Item key="orders">
                    <Link to="/orders">訂單</Link>
                </Menu.Item>
                <Menu.Item key="addresses">
                    <Link to="/addresses">地址管理</Link>
                </Menu.Item>
                <Menu.Item key="login" style={{ float: 'right' }}>
                    {userId ? (
                        <Button type="text" style={{ color: 'white' }} onClick={handleLogout}>
                            登出
                        </Button>
                    ) : (
                        <Link to="/login">登入</Link>
                    )}
                </Menu.Item>
                <Menu.Item key="about">
                    <Link to="/about">關於</Link>
                </Menu.Item>
            </Menu>
        </AntHeader>
    );
};

export default Header;
