import { StackNode } from "./StackElement";

export class Stack<T> {
    top: StackNode<T> | null = null;

    size = 0;

    push(value: T): void {
        this.top = new StackNode(value, this.top);
        ++this.size;
    }

    topValue(): T | null {
        if (this.top == null) {
            return null;
        }
        return this.top!.value;
    }

    pop(): void {
        --this.size;
        if (this.top != null) {
            this.top = this.top!.bottom;
        }
    }
}