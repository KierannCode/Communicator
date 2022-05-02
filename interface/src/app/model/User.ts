import { EntityDeserializer } from "../util/EntityDeserializer";

export class User {
    role: 'User' | 'Administrator';

    id: number;
    username: string;

    description: string | null;
    gender: 'MALE' | 'FEMALE' | null;

    registrationTimestamp: Date;

    constructor(src: any) {
        this.id = src.id;
        EntityDeserializer.register(this);
        this.username = src.username;
        this.role = src.role;
        this.description = src.description;
        this.gender = src.gender;
        this.registrationTimestamp = new Date(`${src.registrationTimestamp}Z`);
    }
}