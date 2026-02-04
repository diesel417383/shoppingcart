import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Layout } from 'antd';
import Header from './components/Header';
import Footer from './components/Footer';
import ProductPage from './pages/user/ProductPage';
import CartPage from './pages/user/CartPage';
import OrdersPage from './pages/user/OrdersPage';
import AddressesPage from './pages/user/AddressesPage';
import LoginPage from './pages/user/LoginPage';
import RegisterPage from './pages/user/RegisterPage';
import AboutView from './views/AboutView';
import UserManagePage from './pages/admin/UserManagePage';

import AccessRoute from '@/access';
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
              {/* public */}
              <Route path="/" element={<ProductPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/about" element={<AboutView />} />

              {/* login required */}
              <Route
                path="/cart-items"
                element={
                  <AccessRoute allowRoles={['user', 'admin']}>
                    <CartPage />
                  </AccessRoute>
                }
              />

              <Route
                path="/orders"
                element={
                  <AccessRoute allowRoles={['user', 'admin']}>
                    <OrdersPage />
                  </AccessRoute>
                }
              />

              <Route
                path="/addresses"
                element={
                  <AccessRoute allowRoles={['user', 'admin']}>
                    <AddressesPage />
                  </AccessRoute>
                }
              />

              {/* admin only */}
              <Route
                path="/admin"
                element={
                  <AccessRoute allowRoles={['admin']}>
                    <UserManagePage />
                  </AccessRoute>
                }
              />
            </Routes>
          </div>
        </Content>
        <Footer />
      </Layout>
    </Router>
  );
}

export default App;
