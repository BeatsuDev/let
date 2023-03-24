export class InputHandler {
  id: number = 0;
  delay: number;

  constructor(delay: number) {
    this.delay = delay;
  }

  timer(func: Function) {
    console.log(this.id);
    this.id = setTimeout(() => {
      console.log("run function");
      func();
    }, this.delay);
  }

  searchWithDelay(func: Function) {
    clearTimeout(this.id);
    this.timer(func);
  }
}
