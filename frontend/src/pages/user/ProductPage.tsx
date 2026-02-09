import { useState, useEffect } from 'react';
import { Card, Row, Col, Button, Select, message, Input, Modal, Form } from 'antd';
import { ShoppingCartOutlined, PlusOutlined } from '@ant-design/icons';
import { ProductResponse } from '@/api/types';
import { getProductsUsingGet, createProductUsingPost } from '@/api/product.api.ts';
import { addCartItemToCartUsingPost } from '@/api/cart.api.ts';
import { getUserId, getUserRole } from '@/utils/auth.ts';

import { useNavigate } from 'react-router-dom';

const { Meta } = Card;
const { Option } = Select;
const { Search } = Input;

const HomePage = () => {
    const [products, setProducts] = useState<ProductResponse[]>([]);
    const [filteredProducts, setFilteredProducts] = useState<ProductResponse[]>([]);
    const [categories, setCategories] = useState<string[]>([]);
    const [selectedCategory, setSelectedCategory] = useState<string>('');
    const [loading, setLoading] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);
    const [form] = Form.useForm();

    const navigate = useNavigate();

    const userId = getUserId();
    const userRole = getUserRole();

    useEffect(() => {
        fetchProducts();
    }, []);

    useEffect(() => {
        if (selectedCategory) {
            const filtered = products.filter((product) => product.category === selectedCategory);
            setFilteredProducts(filtered);
        } else {
            setFilteredProducts(products);
        }
    }, [selectedCategory, products]);

    const fetchProducts = async () => {
        setLoading(true);
        try {
            const response = await getProductsUsingGet();
            setProducts(response);
            setFilteredProducts(response);

            // 提取所有分類
            const uniqueCategories = [...new Set(response.map((product: ProductResponse) => product.category))];
            setCategories(uniqueCategories);
        } catch (error: any) {
            console.error('Error fetching products:', error);
            message.error(`取得商品列表失敗: ${error.response?.data?.message || error.message}`);
        } finally {
            setLoading(false);
        }
    };

    const addToCart = async (productId: number) => {
        try {
            if (!userId) {
                message.warning('請先登入');
                return;
            }
            await addCartItemToCartUsingPost({
                userId,
                productId,
                quantity: 1,
            });
            message.success('新增購物車成功');
        } catch (error: any) {
            message.error(error.response?.data?.error || '新增購物車失敗');
        }
    };

    const handleAddProduct = () => {
        setModalVisible(true);
    };

    const handleModalCancel = () => {
        setModalVisible(false);
        form.resetFields();
    };

    const handleModalSubmit = async (values: any) => {
        try {
            await createProductUsingPost(values);
            message.success('新增商品成功');
            setModalVisible(false);
            form.resetFields();
            fetchProducts(); // 重新載入商品列表
        } catch (error: any) {
            message.error(error.message || '新增商品失敗');
        }
    };

    const onSearch = (value: string) => {
        if (value.trim()) {
            const filtered = products.filter(
                (product) =>
                    product.name.toLowerCase().includes(value.toLowerCase()) ||
                    product.description.toLowerCase().includes(value.toLowerCase())
            );
            setFilteredProducts(filtered);
        } else {
            setFilteredProducts(
                selectedCategory ? products.filter((product) => product.category === selectedCategory) : products
            );
        }
    };

    return (
        <div>
            <h1>商品列表</h1>

            {/* 搜尋和篩選 */}
            <div style={{ marginBottom: 24, display: 'flex', gap: 16 }}>
                <Search placeholder="搜尋商品名稱或描述" onSearch={onSearch} style={{ width: 300 }} allowClear />
                <Select
                    placeholder="選擇分類"
                    style={{ width: 150 }}
                    value={selectedCategory}
                    onChange={setSelectedCategory}
                    allowClear
                >
                    {categories.map((category) => (
                        <Option key={category} value={category}>
                            {category}
                        </Option>
                    ))}
                </Select>
                {userRole === 'admin' ? (
                    <Button type="primary" icon={<PlusOutlined />} onClick={handleAddProduct}>
                        新增商品
                    </Button>
                ) : (
                    <Button onClick={() => navigate('/cart-items')}>
                        <ShoppingCartOutlined />
                        查看購物車
                    </Button>
                )}
            </div>

            {/* 商品列表 */}
            {loading ? (
                <div style={{ textAlign: 'center', marginTop: 48 }}>
                    <p>載入中...</p>
                </div>
            ) : (
                <Row gutter={[16, 16]}>
                    {filteredProducts.map((product) => (
                        <Col xs={24} sm={12} md={8} lg={6} xl={4} key={product.id}>
                            <Card
                                hoverable
                                style={{ height: '100%' }}
                                cover={
                                    <div
                                        style={{
                                            height: 180,
                                            background: '#f5f5f5',
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                            fontSize: '48px',
                                        }}
                                    >
                                        🛍️
                                    </div>
                                }
                                actions={[
                                    userRole === 'admin' ? (
                                        <Button type="primary" disabled block>
                                            管理員模式
                                        </Button>
                                    ) : (
                                        <Button
                                            type="primary"
                                            icon={<ShoppingCartOutlined />}
                                            onClick={() => addToCart(product.id)}
                                            disabled={product.stock <= 0}
                                            block
                                        >
                                            {product.stock > 0 ? '新增至購物車' : '缺貨'}
                                        </Button>
                                    ),
                                ]}
                            >
                                <Meta
                                    title={product.name}
                                    description={
                                        <div>
                                            <p
                                                style={{
                                                    color: '#1890ff',
                                                    fontSize: '16px',
                                                    fontWeight: 'bold',
                                                    margin: '8px 0',
                                                }}
                                            >
                                                ¥{product.price}
                                            </p>
                                            <p style={{ color: '#666', fontSize: '12px', margin: '4px 0' }}>
                                                庫存: {product.stock}
                                            </p>
                                            <p style={{ color: '#666', fontSize: '12px', margin: '4px 0' }}>
                                                分類: {product.category}
                                            </p>
                                            <p
                                                style={{
                                                    color: '#999',
                                                    fontSize: '12px',
                                                    margin: '8px 0 0 0',
                                                    overflow: 'hidden',
                                                    textOverflow: 'ellipsis',
                                                    display: '-webkit-box',
                                                    WebkitLineClamp: 2,
                                                    WebkitBoxOrient: 'vertical',
                                                }}
                                            >
                                                {product.description}
                                            </p>
                                        </div>
                                    }
                                />
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            {filteredProducts.length === 0 && !loading && (
                <div style={{ textAlign: 'center', marginTop: 48 }}>
                    <p>暫無商品</p>
                </div>
            )}

            {/* 新增商品 Modal */}
            <Modal title="新增商品" open={modalVisible} onCancel={handleModalCancel} footer={null} width={600}>
                <Form
                    form={form}
                    layout="vertical"
                    onFinish={handleModalSubmit}
                    initialValues={{
                        name: '',
                        description: '',
                        price: 0,
                        stock: '0',
                        images: '',
                        category: '',
                    }}
                >
                    <Form.Item name="name" label="商品名稱" rules={[{ required: true, message: '請輸入商品名稱' }]}>
                        <Input placeholder="請輸入商品名稱" />
                    </Form.Item>

                    <Form.Item
                        name="description"
                        label="商品描述"
                        rules={[{ required: true, message: '請輸入商品描述' }]}
                    >
                        <Input.TextArea rows={3} placeholder="請輸入商品描述" />
                    </Form.Item>

                    <Form.Item name="price" label="商品價格" rules={[{ required: true, message: '請輸入商品價格' }]}>
                        <Input type="number" min="0" placeholder="請輸入商品價格" />
                    </Form.Item>

                    <Form.Item name="stock" label="庫存數量" rules={[{ required: true, message: '請輸入庫存數量' }]}>
                        <Input type="number" min="0" placeholder="請輸入庫存數量" />
                    </Form.Item>

                    <Form.Item name="images" label="商品圖片" rules={[{ message: '請輸入商品圖片 URL' }]}>
                        <Input placeholder="請輸入商品圖片 URL" />
                    </Form.Item>

                    <Form.Item name="category" label="商品分類" rules={[{ required: true, message: '請輸入商品分類' }]}>
                        <Input placeholder="請輸入商品分類" />
                    </Form.Item>

                    <Form.Item style={{ textAlign: 'right' }}>
                        <Button onClick={handleModalCancel} style={{ marginRight: 8 }}>
                            取消
                        </Button>
                        <Button type="primary" htmlType="submit">
                            新增商品
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
};

export default HomePage;
