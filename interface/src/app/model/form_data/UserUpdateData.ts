export interface UserUpdateData {
    updatedFields?: Array<string>
    username?: string;
    rawPassword?: string;
    passwordConfirmation?: string;
    email?: string;
    description?: string;
    gender?: 'MALE' | 'FEMALE';
}