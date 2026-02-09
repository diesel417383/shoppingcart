import { useState, useEffect } from 'react';
import { Card, List, Button, InputNumber, message, Modal, Select } from 'antd';
import { getUserId } from '@/utils/auth.ts';

import { 
    getCartItemsByUserIdUsingGet, 
    getAddressesByUserIdUsingGet, 
    updateCartItemQuantityUsingPut, 
    deleteCartItemUsingDelete, 
    createOrderUsingPost,

} from '@/api/cart.api.ts';
import { AddressResponse, CartResponse } from '@/api/types';

const CartPage = () => {
    const [cart, setCart] = useState<CartResponse>({
        items: [],
        totalPrice: 0,
    });

    const userId = getUserId();

    const [addresses, setAddresses] = useState<AddressResponse[]>([]);
    const [selectedAddressId, setSelectedAddressId] = useState<number>();
    const [loading, setLoading] = useState(false);
    const [checkoutModalVisible, setCheckoutModalVisible] = useState(false);

    const fetchCartItems = async () => {
        try {
            const response = await getCartItemsByUserIdUsingGet(userId);
            setCart(response);
        } catch (error: any) {
            message.error(error.message || '取得購物車失敗');
        }
    };

    const fetchAddresses = async () => {
        try {
            const response = await getAddressesByUserIdUsingGet(userId);
            setAddresses(response);

            // 設定預設地址
            const defaultAddress = response.find(addr => addr.isDefault);
            if (defaultAddress) {
                setSelectedAddressId(defaultAddress.id);
            } else if (response.length > 0) {
                setSelectedAddressId(response[0].id);
            }
        } catch (error: any) {
            message.error(error.message || '取得地址失敗');
        }
    };

    useEffect(() => {
        fetchCartItems();
        fetchAddresses();
    }, []);

    const updateQuantity = async (cartItemId: number, quantity: number) => {
        try {
            await updateCartItemQuantityUsingPut(cartItemId, quantity);
            fetchCartItems();
            message.success('更新數量成功');
        } catch (error: any) {
            message.error(error.message || '更新數量失敗');
        }
    };

    const removeItem = async (cartItemId: number) => {
        try {
            await deleteCartItemUsingDelete(cartItemId);
            fetchCartItems();
            message.success('刪除商品成功');
        } catch (error: any) {
            message.error(error.message || '刪除商品失敗');
        }
    };

    const checkout = async () => {
        if (!selectedAddressId) {
            message.error('請選擇收貨地址');
            return;
        }

        setLoading(true);
        try {
            await createOrderUsingPost(userId, selectedAddressId);
            message.success('下單成功');
            setCheckoutModalVisible(false);
            fetchCartItems();
        } catch (error: any) {
            message.error(error.message || '下單失敗');
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
                                </Button>,
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
                {cart.items.map((item) => (
                    <p key={item.id}>
                        {item.productName} × {item.quantity} = ¥{item.totalPrice}
                    </p>
                ))}

                <p>
                    <strong>總價：¥{cart.totalPrice}</strong>
                </p>

                <p>選擇收貨地址：</p>
                <Select
                    style={{ width: '100%' }}
                    value={selectedAddressId}
                    onChange={setSelectedAddressId}
                    placeholder="請選擇收貨地址"
                >
                    {addresses.map((addr) => (
                        <Select.Option key={addr.id} value={addr.id}>
                            {addr.recipientName}（{addr.phone}）{addr.city}
                            {addr.district}
                            {addr.detailAddress}
                            {addr.isDefault ? '（預設）' : ''}
                        </Select.Option>
                    ))}
                </Select>
            </Modal>
        </div>
    );
};

export default CartPage;
