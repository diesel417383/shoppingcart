import { useState, useEffect } from 'react';
import { Card, Row, Col, Button, Select, message, Input } from 'antd';
import { ShoppingCartOutlined } from '@ant-design/icons';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ProductResponse } from '../types/product';

const { Meta } = Card;
const { Option } = Select;
const { Search } = Input;


const HomePage = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<ProductResponse[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const userId = 1; // 臨時用戶ID

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    if (selectedCategory) {
      const filtered = products.filter(product => product.category === selectedCategory);
      setFilteredProducts(filtered);
    } else {
      setFilteredProducts(products);
    }
  }, [selectedCategory, products]);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      console.log('Fetching products from /api/products...');
      const response = await axios.get('/api/products');
      console.log('Products response:', response.data);
      setProducts(response.data);
      setFilteredProducts(response.data);

      // 提取所有分類
      const uniqueCategories = [...new Set(response.data.map((product: Product) => product.category))];
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
      await axios.post('/api/cart-items', {
           userId,
           productId,
           quantity: 1,
      });
      message.success('新增購物車成功');
    } catch (error: any) {
      message.error(error.response?.data?.error || '新增購物車失敗');
    }
  };

  const onSearch = (value: string) => {
    if (value.trim()) {
      const filtered = products.filter(product =>
        product.name.toLowerCase().includes(value.toLowerCase()) ||
        product.description.toLowerCase().includes(value.toLowerCase())
      );
      setFilteredProducts(filtered);
    } else {
      setFilteredProducts(selectedCategory ?
        products.filter(product => product.category === selectedCategory) :
        products
      );
    }
  };

  return (
    <div>
      <h1>商品列表</h1>

      {/* 搜尋和篩選 */}
      <div style={{ marginBottom: 24, display: 'flex', gap: 16 }}>
        <Search
          placeholder="搜尋商品名稱或描述"
          onSearch={onSearch}
          style={{ width: 300 }}
          allowClear
        />
        <Select
          placeholder="選擇分類"
          style={{ width: 150 }}
          value={selectedCategory}
          onChange={setSelectedCategory}
          allowClear
        >
          {categories.map(category => (
            <Option key={category} value={category}>{category}</Option>
          ))}
        </Select>
        <Button onClick={() => navigate('/cart-items')}>
          <ShoppingCartOutlined />
          查看購物車
        </Button>
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
                cover={(
                  <div style={{
                    height: 180,
                    background: '#f5f5f5',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    fontSize: '48px'
                  }}>
                    🛍️
                  </div>
                )}
                actions={[
                  <Button
                    type="primary"
                    icon={<ShoppingCartOutlined />}
                    onClick={() => addToCart(product.id)}
                    disabled={product.stock <= 0}
                    block
                  >
                    {product.stock > 0 ? '新增至購物車' : '缺貨'}
                  </Button>
                ]}
              >
                <Meta
                  title={product.name}
                  description={
                    <div>
                      <p style={{ color: '#1890ff', fontSize: '16px', fontWeight: 'bold', margin: '8px 0' }}>
                        ¥{product.price}
                      </p>
                      <p style={{ color: '#666', fontSize: '12px', margin: '4px 0' }}>
                        庫存: {product.stock}
                      </p>
                      <p style={{ color: '#666', fontSize: '12px', margin: '4px 0' }}>
                        分類: {product.category}
                      </p>
                      <p style={{
                        color: '#999',
                        fontSize: '12px',
                        margin: '8px 0 0 0',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical'
                      }}>
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
    </div>
  );
};

export default HomePage;
