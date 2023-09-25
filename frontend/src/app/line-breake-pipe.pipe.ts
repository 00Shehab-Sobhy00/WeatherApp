import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lineBreakePipe'
})
export class LineBreakePipePipe implements PipeTransform {

  transform(value: string, maxLength: number): string {
    if (value.length > maxLength) {
      const substr = value.substring(0, maxLength);
      const remaining = value.substring(maxLength);
      return substr + '\n' + this.transform(remaining, maxLength);
    }
    return value;
  }

}
