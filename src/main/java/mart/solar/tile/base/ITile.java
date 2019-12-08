package mart.solar.tile.base;

import java.util.function.Supplier;

public interface ITile<T> {

    Supplier<T> getTile();

}
