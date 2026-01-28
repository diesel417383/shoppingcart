import { Form, Input, Button, Card, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { UserRegisterResquest } from '../types/user';
import { useState } from 'react';


const RegisterAccountPage = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onFinish = async (values: UserRegisterResquest) => {
      setLoading(true);
      try {
        const res = await fetch('/api/users/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values),
        });

        if (!res.ok) {
          const error = await res.json();
          message.error(error.message || '註冊失敗');
          return;
        }

        message.success('註冊成功！請登入');
        navigate('/login');
      } catch (err) {
        console.error(err);
        message.error('註冊失敗，請稍後重試');
      } finally {
        setLoading(false);
      }
    };

  return (
      <Card title="創建帳戶" style={{ maxWidth: 400, margin: '50px auto' }}>
        <Form<UserRegisterResquest>
          name="register"
          onFinish={onFinish}
          layout="vertical"
        >
          <Form.Item
            label="帳號"
            name="username"
            rules={[{ required: true, message: '請輸入帳號！' }]}
          >
            <Input placeholder="請輸入帳號" />
          </Form.Item>

          <Form.Item
            label="Email"
            name="email"
            rules={[
              { required: true, message: '請輸入Email！' },
              { type: 'email', message: '請輸入有效的Email！' },
            ]}
          >
            <Input placeholder="請輸入Email" />
          </Form.Item>

          <Form.Item
            label="密碼"
            name="password"
            rules={[
              { required: true, message: '請輸入密碼！' },
              { min: 8, message: '密碼長度至少8個字符！' }, // 對應後端規則
            ]}
          >
            <Input.Password placeholder="請輸入密碼" />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" block loading={loading}>
              創建帳戶
            </Button>
          </Form.Item>

          <Form.Item>
            <Button type="link" onClick={() => navigate('/login')} block>
              返回登入
            </Button>
          </Form.Item>
        </Form>
      </Card>
    );
  };

export default RegisterAccountPage;
