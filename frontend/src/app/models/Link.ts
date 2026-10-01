export interface Link {
  id: number;
  shortCode: string;
  originalUrl: string;
  createdAt: string;
  clickCount: number;
}

export interface CreateLinkResponse {
  shortCode: string;
  shortUrl: string;
}