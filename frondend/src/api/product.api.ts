import http from './http';
import { ProductResponse, CreateProductRequest } from '@/api/types.ts';

export const getProductsUsingGet = async (): Promise<ProductResponse[]> => {
    try {
        const response = await http.get<ProductResponse[]>('/products');
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || '取得商品列表失敗');
    }
};

export const createProductUsingPost = async (values: CreateProductRequest): Promise<ProductResponse> => {
    try {
        const response = await http.post('/products', values);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.error || '新增商品失敗');
    }
};
