import type { ListingMinimal } from "@/services/index";

interface Message {
    id: number;
    sent: string;  // The datetime the message was sent
    sender: string;
    content: string;
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

/**
 * A chat between a buyer and a seller as part of a chat list.
 */
interface ChatOverview {
    id: number;
    buyer: Buyer;
    seller: Seller;
    listing: ListingMinimal;
    lastMessage: Message;
}

/**
 * A chat between a buyer and a seller with all messages.
 */
interface Chat {
    id: number;
    buyer: Buyer;
    seller: Seller;
    listing: ListingMinimal;
    messages: Message[];
}

export type { Chat, ChatOverview, Message, Buyer, Seller };