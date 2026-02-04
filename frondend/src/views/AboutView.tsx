import { Card, Typography } from 'antd';

const { Title, Paragraph } = Typography;

const AboutView = () => {
  return (
    <div style={{ maxWidth: 800, margin: '50px auto', padding: '0 20px' }}>
      <Card>
        <Title level={2}>關於這個 Side Project</Title>

        <Paragraph>
          這個購物車網站是我為了提升全端開發能力而製作的 side project。
          透過實作完整的電商功能，從後端 API 設計到前端介面開發，讓我深入理解 Spring Boot、React
          和資料庫設計的整合應用，同時也練習了 RESTful API 設計和使用者體驗優化。
        </Paragraph>
      </Card>
    </div>
  );
};

export default AboutView;
