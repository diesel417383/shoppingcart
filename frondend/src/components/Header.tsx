import { Link } from 'react-router-dom';
import { Layout, Menu } from 'antd';

const { Header: AntHeader } = Layout;

const Header = () => {
  return (
    <AntHeader>
      <Link to="/" style={{ color: 'white', fontSize: '20px', fontWeight: 'bold', textDecoration: 'none' }}>
        購物車系統
      </Link>
      <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['home']}>
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
        <Menu.Item key="login">
          <Link to="/login">登入</Link>
        </Menu.Item>
      </Menu>
    </AntHeader>
  );
};

export default Header;
