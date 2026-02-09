import { useState, useEffect } from 'react';
import { Card, Button, Modal, Form, Input, Checkbox, message } from 'antd';

import { AddressResponse, CreateAddressRequest } from '@/api/types';
import { getUserId } from '@/utils/auth';
import {
    getAddressesByUserIdUsingGet,
    createAddressUsingPost,
    updateAddressUsingPut,
    deleteAddressUsingDelete,
} from '@/api/address.api.ts';

const AddressesPage = () => {
    const [addressList, setAddressList] = useState<AddressResponse[]>([]);
    const [modalVisible, setModalVisible] = useState(false);
    const [editingAddress, setEditingAddress] = useState<AddressResponse | null>(null);
    const [form] = Form.useForm();

    const userId = getUserId();

    /* ===== 取得地址列表 ===== */
    const fetchAddress = async () => {
        try {
            const res = await getAddressesByUserIdUsingGet(userId);
            setAddressList(res);
        } catch (error: any) {
            message.error(error.message || '取得地址失敗');
        }
    };

    useEffect(() => {
        fetchAddress();
    }, []);

    /* ===== Modal 表單初始化 ===== */
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

    /* ===== 新增 ===== */
    const handleAdd = () => {
        setEditingAddress(null);
        form.resetFields();
        setModalVisible(true);
    };

    /* ===== 編輯 ===== */
    const handleEdit = (address: AddressResponse) => {
        setEditingAddress(address);
        setModalVisible(true);
    };

    /* ===== 刪除 ===== */
    const handleDelete = async (addressId: number) => {
        try {
            await deleteAddressUsingDelete(addressId);
            message.success('刪除地址成功');
            setAddressList((prev) => prev.filter((addr) => addr.id !== addressId));
        } catch (error: any) {
            message.error(error.message || '刪除地址失敗');
        }
    };

    /* ===== 新增 / 更新送出 ===== */
    const handleSubmit = async (values: any) => {
        const requestBody: CreateAddressRequest = {
            ...values,
            userId,
        };

        try {
            if (editingAddress) {
                await updateAddressUsingPut(editingAddress.id, requestBody);
                message.success('更新地址成功');
            } else {
                await createAddressUsingPost(requestBody);
                message.success('新增地址成功');
            }

            setModalVisible(false);
            form.resetFields();
            fetchAddress();
        } catch (error: any) {
            message.error(error.message || '操作失敗');
        }
    };

    return (
        <div>
            <h1 style={{ marginBottom: 24 }}>地址管理</h1>

            <Button type="primary" onClick={handleAdd}>
                新增地址
            </Button>

            {/* ===== 地址列表 ===== */}
            {addressList.map((address) => (
                <Card key={address.id} style={{ width: '100%', marginTop: 16, marginBottom: 16 }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                        <div>
                            <h3>
                                {address.recipientName}
                                {address.isDefault && <span style={{ color: '#1890ff', marginLeft: 8 }}>(預設)</span>}
                            </h3>
                            <p>電話: {address.phone}</p>
                            <p>
                                地址:
                                {address.city}
                                {address.district}
                                {address.detailAddress}
                            </p>
                        </div>
                        <div>
                            <Button onClick={() => handleEdit(address)} style={{ marginRight: 8 }}>
                                編輯
                            </Button>
                            <Button danger onClick={() => handleDelete(address.id)}>
                                刪除
                            </Button>
                        </div>
                    </div>
                </Card>
            ))}

            {/* ===== Modal ===== */}
            <Modal
                title={editingAddress ? '編輯地址' : '新增地址'}
                open={modalVisible}
                onCancel={() => setModalVisible(false)}
                footer={null}
            >
                <Form form={form} layout="vertical" onFinish={handleSubmit} initialValues={{ isDefault: false }}>
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

                    <Form.Item name="city" label="城市" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>

                    <Form.Item name="district" label="區縣" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>

                    <Form.Item name="detailAddress" label="詳細地址" rules={[{ required: true }]}>
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
