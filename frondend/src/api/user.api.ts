import http from './http';
import { UserRegisterRequest, UserLoginRequest, UserRegisterResponse, UserLoginResponse } from './types';

// 註冊新帳戶
export const registerUserUsingPost = async (values: UserRegisterRequest): Promise<UserRegisterResponse> => {
    try {
        const response = await http.post('/users/register', values);
        return response.data;
    } catch (error: any) {
        throw new Error('註冊失敗');
    }
};

// 帳戶登入
export const loginUserUsingPost = async (values: UserLoginRequest): Promise<UserLoginResponse> => {
    try {
        const response = await http.post('/users/login', values);
        return response.data;
    } catch (error: any) {
        throw new Error('登入失敗');
    }
};
