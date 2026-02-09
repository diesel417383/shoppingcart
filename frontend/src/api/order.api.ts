import http from './http';
import { OrderItemResponse, OrderResponse } from '@/api/types.ts';

export const getUserOrdersByUserIdUsingGet = async (userId: number | null): Promise<OrderResponse[]> => {
    try {
        if (userId === null) {
            throw new Error('請先登入');
        }
        const response = await http.get(`/users/${userId}/orders`);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || '取得使用者訂單失敗');
    }
};

export const deleteOrderByOrderIdUsingDelete = async (orderId: number): Promise<void> => {
    try {
        await http.delete(`/orders/${orderId}`);
    } catch (error: any) {
        throw new Error(error.response?.data?.message || '刪除單一使用者訂單失敗');
    }
};

export const getOrderDetailByOrderIdUsingGet = async (orderId: number): Promise<OrderItemResponse[]> => {
    try {
        const response = await http.get(`/orders/${orderId}/items`);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || '取得單一使用者訂單詳情失敗');
    }
};
