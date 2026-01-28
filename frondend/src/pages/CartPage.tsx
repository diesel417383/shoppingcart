import { useState, useEffect } from 'react';
import { Card, List, Button, InputNumber, message, Modal, Select } from 'antd';
import axios from 'axios';

import { AddressResponse } from '../types/address';
import { CartResponse, CartItem } from '../types/cart';


const CartPage = () => {
  const userId = 1;

  const [cart, setCart] = useState<CartResponse>({
    items: [],
    totalPrice: 0
  });


  const [addresses, setAddresses] = useState<AddressResponse[]>([]);
  const [selectedAddressId, setSelectedAddressId] = useState<number>();
  const [loading, setLoading] = useState(false);
  const [checkoutModalVisible, setCheckoutModalVisible] = useState(false);

  const fetchCartItems = async () => {
    try {
      const response = await axios.get<CartResponse>(`/api/cart-items/${userId}`);
      setCart(response.data);
    } catch {
      message.error('取得購物車失敗');
    }
  };

  const fetchAddresses = async () => {
    try {
      const response = await axios.get<AddressResponse>(`/api/addresses/${userId}`);

      // ✅ normalize：單筆 → 陣列
      setAddresses(response.data ? [response.data] : []);

      if (response.data?.isDefault) {
        setSelectedAddressId(response.data.id);
      }

    } catch {
      message.error('取得地址失敗');
    }
  };

  useEffect(() => {
    fetchCartItems();
    fetchAddresses();
  }, []);

  const updateQuantity = async (cartItemId: number, quantity: number) => {
    try {
      await axios.put(`/api/cart-items/${cartItemId}`, { quantity });
      fetchCartItems();
      message.success('更新數量成功');
    } catch (error: any) {
      message.error(error.response?.data?.error || '更新數量失敗');
    }
  };

  const removeItem = async (cartItemId: number) => {
    try {
      await axios.delete(`/api/cart-items/${cartItemId}`);
      fetchCartItems();
      message.success('刪除商品成功');
    } catch {
      message.error('刪除商品失敗');
    }
  };

  const checkout = async () => {
    if (!selectedAddressId) {
      message.error('請選擇收貨地址');
      return;
    }

    setLoading(true);
    try {
      await axios.post('/api/orders/create', {
        userId,
        addressId: selectedAddressId
      });
      message.success('下單成功');
      setCheckoutModalVisible(false);
      fetchCartItems();
    } catch (error: any) {
      message.error(error.response?.data?.error || '下單失敗');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 style={{ marginBottom: 24 }}>購物車</h1>

      <Card style={{ marginBottom: 16 }}>
        <List
          dataSource={cart.items}
          renderItem={(item) => (
            <List.Item
              actions={[
                <InputNumber
                  min={1}
                  value={item.quantity}
                  onChange={(value) => updateQuantity(item.id, value || 1)}
                  style={{ width: 80 }}
                />,
                <Button danger onClick={() => removeItem(item.id)}>
                  刪除
                </Button>
              ]}
            >
              <List.Item.Meta
                title={item.productName}
                description={`價格: ¥${item.productPrice} ｜ 小計: ¥${item.totalPrice}`}
              />
            </List.Item>
          )}
        />

        <div style={{ marginTop: 16, textAlign: 'right' }}>
          <h3>總價：¥{cart.totalPrice}</h3>
          <Button
            type="primary"
            size="large"
            onClick={() => setCheckoutModalVisible(true)}
            disabled={cart.items.length === 0}
          >
            結算
          </Button>
        </div>
      </Card>

      <Modal
        title="確認訂單"
        open={checkoutModalVisible}
        onOk={checkout}
        onCancel={() => setCheckoutModalVisible(false)}
        confirmLoading={loading}
      >
        <p>訂單商品：</p>
        {cart.items.map(item => (
          <p key={item.id}>
            {item.productName} × {item.quantity} = ¥{item.totalPrice}
          </p>
        ))}

        <p><strong>總價：¥{cart.totalPrice}</strong></p>

        <p>選擇收貨地址：</p>
        <Select
          style={{ width: '100%' }}
          value={selectedAddressId}
          onChange={setSelectedAddressId}
          placeholder="請選擇收貨地址"
        >
          {addresses.map(addr => (
            <Select.Option key={addr.id} value={addr.id}>
              {addr.recipientName}（{addr.phone}）
              {addr.province}{addr.city}{addr.district}{addr.detailAddress}
              {addr.isDefault ? '（預設）' : ''}
            </Select.Option>
          ))}
        </Select>
      </Modal>
    </div>
  );
};

export default CartPage;
