import { useState, useEffect } from 'react';
import { Card, Button, Modal, Form, Input, Checkbox, message } from 'antd';
import { AddressResponse } from '../types/address';
import axios from 'axios';

const AddressesPage = () => {
  const [address, setAddress] = useState<AddressResponse | null>(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [editingAddress, setEditingAddress] = useState<AddressResponse | null>(null);
  const [form] = Form.useForm();

  // 臨時 user
  const userId = 1;

  const fetchAddress = async () => {
    try {
      const res = await axios.get<AddressResponse>(`/api/addresses/${userId}`);
      setAddress(res.data);
    } catch {
      setAddress(null);
    }
  };

    useEffect(() => {
      fetchAddress();
    }, []);

    useEffect(() => {
      if (!modalVisible) return;

      if (editingAddress) {
        form.setFieldsValue({
          recipientName: editingAddress.recipientName,
          phone: editingAddress.phone,
          city: editingAddress.city,
          district: editingAddress.district,
          detailAddress: editingAddress.detailAddress,
          isDefault: editingAddress.isDefault ?? false,
        });
      } else {
        form.resetFields();
        form.setFieldsValue({ isDefault: false });
      }
    }, [modalVisible, editingAddress, form]);

  const handleAdd = () => {
    setEditingAddress(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (addr: AddressResponse) => {
    setEditingAddress(addr);
    form.setFieldsValue({
      recipientName: addr.recipientName,
      phone: addr.phone,
      province: addr.province,
      city: addr.city,
      district: addr.district,
      detailAddress: addr.detailAddress,
      isDefault: addr.isDefault ?? false
    });
    setModalVisible(true);
  };

  const handleDelete = async (addressId: number) => {
    try {
      await axios.delete(`/api/addresses/${addressId}`);
      message.success('刪除地址成功');
      setAddress(null);
    } catch {
      message.error('刪除地址失敗');
    }
  };

  const handleSubmit = async (values: any) => {
    if (!values || typeof values !== 'object') {
      message.error('表單資料異常');
      return;
    }

    const requestBody = {
      ...values,
      userId,
    };

    try {
      if (editingAddress) {
        await axios.put(
          `/api/addresses/${editingAddress.id}`,
          requestBody
        );
        message.success('更新地址成功');
      } else {
        await axios.post('/api/addresses', requestBody);
        message.success('新增地址成功');
      }

      setModalVisible(false);
      form.resetFields();
      fetchAddress();
    } catch (error: any) {
      message.error(error.response?.data?.error || '操作失敗');
    }
  };

  return (
    <div>
      <h1 style={{ marginBottom: 24 }}>地址管理</h1>

      {address ? (
        <Card style={{ width: '100%' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <div>
              <h3>
                {address.recipientName}
                {address.isDefault && (
                  <span style={{ color: '#1890ff', marginLeft: 8 }}>
                    (預設)
                  </span>
                )}
              </h3>
              <p>電話: {address.phone}</p>
              <p>
                地址:
                {address.province}
                {address.city}
                {address.district}
                {address.detailAddress}
              </p>
            </div>
            <div>
              <Button
                onClick={() => handleEdit(address)}
                style={{ marginRight: 8 }}
              >
                編輯
              </Button>
              <Button danger onClick={() => handleDelete(address.id)}>
                刪除
              </Button>
            </div>
          </div>
        </Card>
      ) : (
        <Button type="primary" onClick={handleAdd}>
          新增地址
        </Button>
      )}

      {/* ===== Modal ===== */}
      <Modal
        title={editingAddress ? '編輯地址' : '新增地址'}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        footer={null}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
          initialValues={{ isDefault: false }}
        >
          <Form.Item
            name="recipientName"
            label="收貨人姓名"
            rules={[{ required: true, message: '請輸入收貨人姓名' }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="phone"
            label="聯繫電話"
            rules={[
              { required: true, message: '請輸入聯繫電話' },
              { pattern: /^09\d{8}$/, message: '請輸入正確的手機號碼' },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="city"
            label="城市"
            rules={[{ required: true }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="district"
            label="區縣"
            rules={[{ required: true }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="detailAddress"
            label="詳細地址"
            rules={[{ required: true }]}
          >
            <Input.TextArea rows={3} />
          </Form.Item>

          <Form.Item name="isDefault" valuePropName="checked">
            <Checkbox>設為預設地址</Checkbox>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" style={{ marginRight: 8 }}>
              {editingAddress ? '更新' : '新增'}
            </Button>
            <Button onClick={() => setModalVisible(false)}>取消</Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default AddressesPage;
