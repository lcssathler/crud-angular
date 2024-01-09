import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'category',
    standalone: true
})
export class CategoryPipe implements PipeTransform {

  transform(category: string): string {
    switch (category) {
      case "front-end": return "code";
      case "back-end": return "computer";
    }

    return "code"
  }
}
