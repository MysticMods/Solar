package mart.solar.blocks.enums;

import net.minecraft.util.IStringSerializable;

public enum MenhirPart  implements IStringSerializable {
  TOP(2, "top"),
  MIDDLE(1, "middle"),
  BASE(0, "base");

  private static final MenhirPart[] META_LOOKUP = new MenhirPart[values().length];
  private final int meta;
  private final String name;

  MenhirPart(int meta, String name){
    this.name = name;
    this.meta = meta;
  }

  public String getName()
  {
    return this.name;
  }

  public int getMetadata()
  {
    return this.meta;
  }

  public static MenhirPart byMetadata(int meta)
  {
    if (meta < 0 || meta >= META_LOOKUP.length)
    {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }

  static
  {
    for (MenhirPart menhirType : values())
    {
      META_LOOKUP[menhirType.getMetadata()] = menhirType;
    }
  }

}