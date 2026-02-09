import { Navigate, useLocation } from 'react-router-dom';
import { getUserRole } from '@/utils/auth';
import { message } from 'antd';

interface AccessRouteProps {
    allowRoles: Array<'user' | 'admin'>;
    children: JSX.Element;
}

const AccessRoute = ({ allowRoles, children }: AccessRouteProps) => {
    const location = useLocation();
    const role = getUserRole();

    // 沒登入
    if (!role) {
        message.warning('請先登入');
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    // 有登入但角色不對 稍後需製作 Userpage 並導航
    if (!allowRoles.includes(role)) {
        return <Navigate to="/" replace />;
    }

    return children;
};

export default AccessRoute;
