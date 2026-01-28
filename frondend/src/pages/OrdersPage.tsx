import { useState, useEffect } from 'react';
import { Card, List, Button, Select, message, Modal, Descriptions, Spin } from 'antd';
import axios from 'axios';

import { AddressResponse } from '../types/address';
import { OrderResponse, OrderItem } from '../types/order';



const OrdersPage = () => {
  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [statusFilter, setStatusFilter] = useState<string>('');
  const [detailModalVisible, setDetailModalVisible] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const userId = 1; // 臨時用戶ID

  useEffect(() => {
    fetchOrders();
  }, [statusFilter]);

  const fetchOrders = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`/api/users/${userId}/orders`);
      const filtered = statusFilter
        ? response.data.filter((o: Order) => o.status === statusFilter)
        : response.data;
      setOrders(filtered);
    } catch (error) {
      message.error('取得訂單列表失敗');
    } finally {
      setLoading(false);
    }
  };

  const cancelOrder = async (orderId: number) => {
    try {
      await axios.delete(`/api/orders/${orderId}`);
      message.success('取消訂單成功');
      fetchOrders();
    } catch (error: any) {
      message.error(error.response?.data?.error || '取消訂單失敗');
    }
  };

  const showOrderDetail = async (order: Order) => {
    try {
      const itemsResponse = await axios.get(`/api/orders/${order.id}/items`);
      setSelectedOrder({ ...order, orderItems: itemsResponse.data });
      setDetailModalVisible(true);
    } catch (error) {
      message.error('取得訂單明細失敗');
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'PENDING': return '待支付';
      case 'PAID': return '已支付';
      case 'SHIPPED': return '已發貨';
      case 'FINISHED': return '已完成';
      case 'CANCELLED': return '已取消';
      default: return status;
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING': return 'orange';
      case 'PAID': return 'blue';
      case 'SHIPPED': return 'purple';
      case 'FINISHED': return 'green';
      case 'CANCELLED': return 'red';
      default: return 'default';
    }
  };

  return (
    <div>
      <h1 style={{ marginBottom: 24 }}>我的訂單</h1>

      {/* 篩選狀態 */}
      <Card style={{ marginBottom: 16 }}>
        <Select
          placeholder="選擇訂單狀態"
          style={{ width: 200 }}
          allowClear
          value={statusFilter}
          onChange={setStatusFilter}
        >
          <Select.Option value="PENDING">待支付</Select.Option>
          <Select.Option value="PAID">已支付</Select.Option>
          <Select.Option value="SHIPPED">已發貨</Select.Option>
          <Select.Option value="FINISHED">已完成</Select.Option>
          <Select.Option value="CANCELLED">已取消</Select.Option>
        </Select>
      </Card>

      {/* 訂單列表 */}
      {loading ? (
        <Spin/>
      ) : (
        <List
          dataSource={orders}
          renderItem={(order) => (
            <Card style={{ marginBottom: 16 }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <h3>訂單號: {order.orderNo}</h3>
                  <p>下單時間: {new Date(order.createdAt).toLocaleString()}</p>
                  {order.address && (
                    <>
                      <p>收貨地址: {order.address.city}{order.address.district}{order.address.detailAddress}</p>
                      <p>收貨人: {order.address.recipientName} ({order.address.phone})</p>
                    </>
                  )}
                </div>
                <div style={{ textAlign: 'right' }}>
                  <p style={{ color: getStatusColor(order.status), fontWeight: 'bold' }}>
                    {getStatusText(order.status)}
                  </p>
                  <p style={{ fontSize: '18px', fontWeight: 'bold', color: '#1890ff' }}>
                    ¥{order.totalAmount}
                  </p>
                  <div>
                    <Button onClick={() => showOrderDetail(order)} style={{ marginRight: 8 }}>
                      查看詳情
                    </Button>
                    {order.status === 'PENDING' && (
                      <Button danger onClick={() => cancelOrder(order.id)}>
                        取消訂單
                      </Button>
                    )}
                  </div>
                </div>
              </div>
            </Card>
          )}
        />
      )}

      {/* 訂單詳情 Modal */}
      <Modal
        title="訂單詳情"
        open={detailModalVisible}
        onCancel={() => setDetailModalVisible(false)}
        footer={null}
        width={800}
      >
        {selectedOrder && (
          <div>
            <Descriptions bordered column={2}>
              <Descriptions.Item label="訂單號">{selectedOrder.orderNo}</Descriptions.Item>
              <Descriptions.Item label="訂單狀態">{getStatusText(selectedOrder.status)}</Descriptions.Item>
              <Descriptions.Item label="總價">¥{selectedOrder.totalAmount}</Descriptions.Item>
              <Descriptions.Item label="下單時間">
                {new Date(selectedOrder.createdAt).toLocaleString()}
              </Descriptions.Item>
            </Descriptions>

            {selectedOrder.address && (
              <>
                <h3 style={{ marginTop: 24 }}>收貨地址</h3>
                <Card size="small">
                  <p>收貨人: {selectedOrder.address.recipientName}</p>
                  <p>聯繫電話: {selectedOrder.address.phone}</p>
                  <p>收貨地址: {selectedOrder.address.province}{selectedOrder.address.city}{selectedOrder.address.district}{selectedOrder.address.detailAddress}</p>
                </Card>
              </>
            )}

            <h3 style={{ marginTop: 24 }}>商品清單</h3>
            <List
              dataSource={selectedOrder.orderItems || []}
              renderItem={(item) => {
                const subtotal = item.price * item.quantity;
                return (
                  <List.Item>
                    <List.Item.Meta
                      title={item.productName}
                      description={`單價: ¥${item.price} | 數量: ${item.quantity} | 小計: ¥${subtotal}`}
                    />
                  </List.Item>
                );
              }}
            />
          </div>
        )}
      </Modal>
    </div>
  );
};

export default OrdersPage;
