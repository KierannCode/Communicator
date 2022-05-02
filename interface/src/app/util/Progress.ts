export class Progress {
    value = 1;

    isOver(): boolean {
        return this.value == 1;
    }
}