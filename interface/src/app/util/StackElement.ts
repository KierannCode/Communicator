export class StackNode<T> {
    value: T;
    bottom: StackNode<T> | null;

    constructor(value: T, bottom: StackNode<T> | null) {
        this.value = value;
        this.bottom = bottom;
    }
}