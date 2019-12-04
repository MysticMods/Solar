package mart.solar.tile;

import java.util.function.Supplier;

public interface ITile<T> {

    Supplier<T> getTile();

}
