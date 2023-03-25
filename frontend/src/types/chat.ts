interface Message {
    id: number;
    sent: string;  // The datetime the message was sent
    sender: string;
    text: string;
    chat: number;
}

interface ChatUser {
    admin: boolean;
    email: string;
    firstName: string;
    id: number;
    lastName: string;
}

interface Buyer extends ChatUser {
}

interface Seller extends ChatUser {
}

interface Chat {
    id: number;
    listingId: number;
    buyerId: number;
    messages: Message[];
}

export type { Chat, Message };