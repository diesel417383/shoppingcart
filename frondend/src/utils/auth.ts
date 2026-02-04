import { jwtDecode } from 'jwt-decode';
import { TokenPayload } from '@/api/types';

export const getToken = () => {
    return localStorage.getItem('token');
};

export const getUserFromToken = (): TokenPayload | null => {
    const token = getToken();
    if (!token) return null;

    try {
        return jwtDecode<TokenPayload>(token);
    } catch {
        return null;
    }
};

export const getUserRole = () => {
    return getUserFromToken()?.role ?? null;
};

export const getUserId = () => {
    return getUserFromToken()?.userId ?? null;
};
