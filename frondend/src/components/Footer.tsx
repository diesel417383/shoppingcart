import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Col, Typography, Divider, Space } from 'antd';
import { GithubOutlined, MailOutlined, LinkedinOutlined, FacebookOutlined } from '@ant-design/icons';

const { Title, Text, Paragraph } = Typography;

const Footer: React.FC = () => {
  return (
    <footer style={{ backgroundColor: '#f0f2f5', padding: '30px 0', marginTop: '20px' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '0 20px' }}>
        <Row gutter={[24, 24]} justify="center">
          <Col xs={24} lg={12} style={{ textAlign: 'center' }}>
            <Title level={3} style={{ color: '#1890ff', marginBottom: '16px', textAlign: 'center' }}>
              張哲珉的 Side Project
            </Title>
            <Paragraph style={{ color: '#666', lineHeight: '1.6', textAlign: 'center' }}>
              這是一個購物網站的 side project，展示全端開發技能，並做為學習技術導向的練習網站。
            </Paragraph>
            <Space size="large" style={{ marginTop: '20px', justifyContent: 'center' }}>
              <a 
                href="https://github.com/diesel417383" 
                target="_blank" 
                rel="noopener noreferrer"
                style={{ fontSize: '24px', color: '#1890ff' }}
              >
                <GithubOutlined />
              </a>
              <a 
                href="mailto:a27046955@gmail.com"
                style={{ fontSize: '24px', color: '#1890ff' }}
              >
                <MailOutlined />
              </a>
            </Space>
          </Col>
         </Row>
        
        <Divider style={{ margin: '30px 0', borderColor: '#d9d9d9' }} />
        
        <Row justify="space-between" align="middle">
          <Col>
            <Text style={{ color: '#999' }}>
              © {new Date().getFullYear()} 張哲珉的 Side Project. 保留所有權利。
            </Text>
          </Col>
          <Col>
            <Text style={{ color: '#999' }}>
              Built with React, TypeScript & Spring boot API
            </Text>
          </Col>
        </Row>
      </div>
    </footer>
  );
};

export default Footer;