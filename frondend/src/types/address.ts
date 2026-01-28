interface CreateAddressRequest {
    id: number;
    userId: number,
    recipientName: string;
    phone: string;
    city: string;
    district: string;
    detailAddress: string;
    isDefault: boolean;
}

interface AddressResponse {
    id: number;
    recipientName: string;
    phone: string;
    city: string;
    district: string;
    detailAddress: string;
    isDefault: boolean;
}
