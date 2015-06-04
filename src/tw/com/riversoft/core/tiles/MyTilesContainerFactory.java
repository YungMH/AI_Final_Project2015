package tw.com.riversoft.core.tiles;

import org.apache.tiles.definition.pattern.DefinitionPatternMatcherFactory;
import org.apache.tiles.definition.pattern.PatternDefinitionResolver;
import org.apache.tiles.definition.pattern.PrefixedPatternDefinitionResolver;
import org.apache.tiles.definition.pattern.regexp.RegexpDefinitionPatternMatcherFactory;
import org.apache.tiles.definition.pattern.wildcard.WildcardDefinitionPatternMatcherFactory;
import org.apache.tiles.factory.BasicTilesContainerFactory;


public class MyTilesContainerFactory extends BasicTilesContainerFactory {

  @Override
  protected <T> PatternDefinitionResolver<T> createPatternDefinitionResolver(Class<T> customizationKeyClass) {
    
    DefinitionPatternMatcherFactory wildcardFactory = new WildcardDefinitionPatternMatcherFactory();
    DefinitionPatternMatcherFactory regexpFactory = new RegexpDefinitionPatternMatcherFactory();
    PrefixedPatternDefinitionResolver<T> resolver = new PrefixedPatternDefinitionResolver<T>();
    resolver.registerDefinitionPatternMatcherFactory("WILDCARD", wildcardFactory);
    resolver.registerDefinitionPatternMatcherFactory("REGEXP", regexpFactory);
    return resolver;
  }
}
