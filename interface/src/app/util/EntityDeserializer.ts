export class EntityDeserializer {
  static entityMap = new Map<number, any>();

  static register(entity: any): void {
    this.entityMap.set(entity.id, entity);
  }

  static get(id: number): any {
    return this.entityMap.get(id);
  }

  static deserialize<T>(src: any, type: { new(src: any): T }): T {
    let entity = new type(src);
    this.entityMap.clear();
    return entity;
  }

  static deserializeNullable<T>(src: any, type: { new(src: any): T }): T | null {
    if (src == null) {
      return null;
    }
    return this.deserialize<T>(src, type);
  }
}