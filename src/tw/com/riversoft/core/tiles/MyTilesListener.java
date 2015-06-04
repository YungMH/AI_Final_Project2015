package tw.com.riversoft.core.tiles;

import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;


public class MyTilesListener extends AbstractTilesListener {

  @Override
  protected TilesInitializer createTilesInitializer() {
    return new MyTilesInitializer();
  }

  
}
