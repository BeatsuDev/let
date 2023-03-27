export class InputHandler {
  id: number = 0;
  delay: number;

  constructor(delay: number) {
    this.delay = delay;
  }

  timer(func: Function) {
    this.id = setTimeout(() => {
      func();
    }, this.delay);
  }

  searchWithDelay(func: Function) {
    clearTimeout(this.id);
    this.timer(func);
  }
}
