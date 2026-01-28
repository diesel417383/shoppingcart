interface OrderItem {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

interface OrderResponse {
  id: number;
  orderNo: string;
  address: address;
  status: 'PENDING' | 'PAID' | 'SHIPPED' | 'FINISHED' | 'CANCELLED';
  totalAmount: number;
  createdAt: string;
  orderItems: OrderItem[];
}