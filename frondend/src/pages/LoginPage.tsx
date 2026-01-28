import { Form, Input, Button, Card, message } from 'antd';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const navigate = useNavigate();

  const onFinish = (values: any) => {
    console.log('登入成功:', values);
    message.success('登入成功！');
    // 這裡可以添加登入邏輯
    navigate('/');
  };

  const onCreateAccount = () => {
    navigate('/register');
  };

  return (
    <Card title="登入" style={{ maxWidth: 400, margin: '50px auto' }}>
      <Form
        name="login"
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
          label="密碼"
          name="password"
          rules={[{ required: true, message: '請輸入密碼！' }]}
        >
          <Input.Password placeholder="請輸入密碼" />
        </Form.Item>

        <Form.Item
          label="Email"
          name="email"
          rules={[
            { required: true, message: '請輸入Email！' },
            { type: 'email', message: '請輸入有效的Email！' }
          ]}
        >
          <Input placeholder="請輸入Email" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
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
