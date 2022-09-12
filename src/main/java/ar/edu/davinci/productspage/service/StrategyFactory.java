package ar.edu.davinci.productspage.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ar.edu.davinci.productspage.domain.ProductState;

@Component
public class StrategyFactory {
	
	private Map<ProductState, ProductStateStrategy> strategies = new EnumMap<>(ProductState.class);
	
	public StrategyFactory() {
		initStrategies();
	}
	
	private void initStrategies() {
		strategies.put(ProductState.NEW, new ProductNew());
	}
	
	public ProductStateStrategy getStrategy(ProductState state) {
		if(state == null || !strategies.containsKey(state)) {
			throw new IllegalArgumentException("Invalid " + state);
		}
		return strategies.get(state);
	}
}
