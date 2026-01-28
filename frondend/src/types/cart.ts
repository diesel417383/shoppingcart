interface CartItem {
  id: number;
  productId: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  productImages?: string;
  quantity: number;
  totalPrice: number;
}

interface CartResponse {
  items: CartItem[];
  totalPrice: number;
}