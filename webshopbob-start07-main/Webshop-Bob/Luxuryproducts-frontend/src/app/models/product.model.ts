import { Category } from "./category.model";

export class Product {
  public id: number;
  public name: string;
  public imagePath: string;
  public description: string;
  public price: number;
  public category: Category;
}
