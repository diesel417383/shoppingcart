import http from './http';
import { AddProductToCartRequest, CartItemResponse, CartResponse, AddressResponse } from './types';

export const getCartItemsByUserIdUsingGet = async (userId: any): Promise<CartResponse> => {
    try {
        const response = await http.get(`/cart-items/${userId}`);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '取得購物車失敗');
    }
};

export const getAddressesByUserIdUsingGet = async (userId: any): Promise<AddressResponse[]> => {
    try {
        const response = await http.get(`/addresses/${userId}`);
        return response.data || [];
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '取得地址失敗');
    }
};

export const updateCartItemQuantityUsingPut = async (cartItemId: number, quantity: number): Promise<void> => {
    try {
        await http.put(`/cart-items/${cartItemId}`, { quantity });
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '更新數量失敗');
    }
};

export const deleteCartItemUsingDelete = async (cartItemId: number): Promise<void> => {
    try {
        await http.delete(`/cart-items/${cartItemId}`);
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '刪除商品失敗');
    }
};

export const createOrderUsingPost = async (userId: any, addressId: number): Promise<void> => {
    try {
        await http.post('/orders/create', {
            userId,
            addressId,
        });
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '下單失敗');
    }
};

export const addCartItemToCartUsingPost = async (values: AddProductToCartRequest): Promise<CartItemResponse> => {
    try {
        const response = await http.post('/cart-items', values);
        return response.data;
    } catch (error: any) {
        console.error('新增購物車失敗', error);
        throw new Error(error.response?.data?.error || '新增購物車失敗');
    }
};
