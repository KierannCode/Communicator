export interface SignUpData {
    username?: string;
    rawPassword?: string;
    passwordConfirmation?: string;
    email?: string;
    description?: string;
    gender?: 'MALE' | 'FEMALE';
}