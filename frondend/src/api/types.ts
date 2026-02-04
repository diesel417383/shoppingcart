// ------------------User------------------
export interface UserRegisterRequest {
    account: string;
    password: string;
    email: string;
}

export interface UserRegisterResponse {
    account: string;
    email: string;
}

export interface UserLoginRequest {
    account: string;
    password: string;
}

export interface UserLoginResponse {
    id: number;
    account: string;
    role: string;
    token: string;
}

// ------------------Address------------------
export interface CreateAddressRequest {
    recipientName: string;
    phone: string;
    city: string;
    district: string;
    detailAddress: string;
    isDefault: boolean;
}

export interface AddressResponse {
    id: number;
    recipientName: string;
    phone: string;
    city: string;
    district: string;
    detailAddress: string;
    isDefault: boolean;
}

// ------------------Product------------------
export interface ProductResponse {
    id: number;
    name: string;
    description: string;
    price: number;
    stock: number;
    images: string;
    category: string;
}

export interface CreateProductRequest {
    name: string;
    description: string;
    price: number;
    stock: string;
    images: string;
    category: string;
}

// ------------------Cart / CartItem------------------
export interface CartItemResponse {
    id: number;
    productId: number;
    productName: string;
    productDescription: string;
    productPrice: number;
    productImages?: string;
    quantity: number;
    totalPrice: number;
}

export interface CartResponse {
    items: CartItemResponse[];
    totalPrice: number;
}

export interface AddProductToCartRequest {
    productId: number;
    quantity: number;
}

// ------------------Order------------------
export interface OrderItemRequest {
    productId: number;
    quantity: number;
}

export interface OrderItemResponse {
    productId: number;
    productName: string;
    price: number;
    quantity: number;
}

export interface CreateOrderRequest {
    addressId: number;
}

export interface OrderResponse {
    id: number;
    orderNo: string;
    address: AddressResponse;
    status: 'PENDING' | 'PAID' | 'SHIPPED' | 'FINISHED' | 'CANCELLED';
    totalAmount: number;
    createdAt: string;
    orderItems: OrderItemResponse[];
}
// ------------------JWT------------------
export interface TokenPayload {
    userId: number;
    account: string;
    role: 'user' | 'admin';
}

export interface Props {
    allowRoles: Array<'user' | 'admin'>;
    children: JSX.Element;
}
