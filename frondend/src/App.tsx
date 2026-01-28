import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import CartPage from './pages/CartPage';
import OrdersPage from './pages/OrdersPage';
import AddressesPage from './pages/AddressesPage';
import LoginPage from './pages/LoginPage';
import RegisterAccountPage from './pages/RegisterAccountPage';
import './App.css';

const { Content } = Layout;

function App() {
  return (
    <Router>
      <Layout className="layout" style={{ minHeight: '100vh' }}>
        <Header />
        <Content style={{ padding: '0 20px', marginTop: 64, flex: 1 }}>
          <div style={{ padding: 16 }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/cart-items" element={<CartPage />} />
              <Route path="/orders" element={<OrdersPage />} />
              <Route path="/addresses" element={<AddressesPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterAccountPage />} />
            </Routes>
          </div>
        </Content>
        <Footer/>
      </Layout>
    </Router>
  );
}

export default App;
