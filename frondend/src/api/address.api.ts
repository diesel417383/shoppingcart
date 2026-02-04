import { AddressResponse, CreateAddressRequest } from '@/api/types.ts';
import http from '@/api/http.ts';

export const getAddressesByUserIdUsingGet = async (userId: number): Promise<AddressResponse[]> => {
    try {
        const response = await http.get(`/addresses/${userId}`);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '取得地址失敗');
    }
};

export const createAddressUsingPost = async (values: CreateAddressRequest): Promise<AddressResponse> => {
    try {
        const response = await http.post('/addresses', values);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '新增地址失敗');
    }
};

export const updateAddressUsingPut = async (addressId: number, values: CreateAddressRequest): Promise<AddressResponse> => {
    try {
        const response = await http.put(`/addresses/${addressId}`, values);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '更新地址失敗');
    }
};

export const deleteAddressUsingDelete = async (addressId: number): Promise<void> => {
    try {
        await http.delete(`/addresses/${addressId}`);
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '刪除地址失敗');
    }
};
