import { Form, Input, Button, Card, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { UserLoginRequest } from '@/api/types';
import { loginUserUsingPost } from '@/api/user.api';

const LoginPage = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const onFinish = async (values: UserLoginRequest) => {
        setLoading(true);
        try {
            const data = await loginUserUsingPost(values);
            const token = data.token;

            if (token) localStorage.setItem('token', token);

            console.log('登入成功:', values);
            message.success('登入成功！');
            navigate('/');
        } catch (error) {
            message.error('登入失敗，請檢查帳號密碼');
        } finally {
            setLoading(false);
        }
    };

    const onCreateAccount = () => {
        navigate('/register');
    };

    return (
        <Card title="登入" style={{ maxWidth: 400, margin: '50px auto' }}>
            <Form name="login" onFinish={onFinish} layout="vertical">
                <Form.Item label="帳號" name="account" rules={[{ required: true, message: '請輸入帳號！' }]}>
                    <Input placeholder="請輸入帳號" />
                </Form.Item>

                <Form.Item label="密碼" name="password" rules={[{ required: true, message: '請輸入密碼！' }]}>
                    <Input.Password placeholder="請輸入密碼" />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" block loading={loading}>
                        登入
                    </Button>
                </Form.Item>

                <Form.Item>
                    <Button type="default" onClick={onCreateAccount} block>
                        創建帳戶
                    </Button>
                </Form.Item>
            </Form>
        </Card>
    );
};

export default LoginPage;
