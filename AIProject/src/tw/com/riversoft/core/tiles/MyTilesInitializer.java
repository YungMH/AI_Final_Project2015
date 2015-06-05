package tw.com.riversoft.core.tiles;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;


public class MyTilesInitializer extends AbstractTilesInitializer {

  @Override
  protected AbstractTilesContainerFactory createContainerFactory(TilesApplicationContext context) {
    return new MyTilesContainerFactory();
  }

}
